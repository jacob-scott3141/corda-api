@file:JvmName("Encoding")

package net.corda.v5.crypto.signing

import net.corda.v5.crypto.exceptions.CryptoServiceException
import net.corda.v5.crypto.sha256Bytes
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.time.Instant

const val ENHANCED_SIGNATURE_ENCODING_V1_MAGIC_NUMBER: Short = 18237
const val ENHANCED_SIGNING_ENCODING_V1_MAGIC_NUMBER: Short = 18238
const val ENHANCED_SIGNED_ENCODING_V1_MAGIC_NUMBER: Short = 18239

fun EnhancedSignature.encode(): ByteArray =
    ByteArrayOutputStream().use { stream ->
        DataOutputStream(stream).use { writer ->
            writer.writeShort(ENHANCED_SIGNATURE_ENCODING_V1_MAGIC_NUMBER.toInt())
            writer.writeLong(timestamp.toEpochMilli())
            writer.writeString(signatureCodeName)
            writer.writeByteArray(signature)
        }
        stream.toByteArray()
    }

fun ByteArray.decodeDigitalSignature(): EnhancedSignature =
    ByteArrayInputStream(this).use { stream ->
        DataInputStream(stream).use { reader ->
            when (reader.readShort()) {
                ENHANCED_SIGNATURE_ENCODING_V1_MAGIC_NUMBER -> {
                    val timestamp = reader.readLong()
                    val signatureCodeName = reader.readUTF()
                    require(!signatureCodeName.isNullOrBlank()) {
                        "The signature spec id must not be blank."
                    }
                    val signature = reader.readByteArray()
                    EnhancedSignature(
                        timestamp = Instant.ofEpochMilli(timestamp),
                        signatureCodeName = signatureCodeName,
                        signature = signature
                    )
                }
                else -> throw CryptoServiceException("Unknown signature format.")
            }
        }
    }


fun EnhancedSigningData.encode(): ByteArray =
    ByteArrayOutputStream().use { stream ->
        DataOutputStream(stream).use { writer ->
            writer.writeShort(ENHANCED_SIGNING_ENCODING_V1_MAGIC_NUMBER.toInt())
            writeSigningData(writer)
        }
        stream.toByteArray()
    }

private fun EnhancedSigningData.writeSigningData(writer: DataOutputStream) {
    writer.writeLong(timestamp.toEpochMilli())
    writer.writeString(signatureCodeName)
    writer.writeByteArray(bytes)
}

fun EnhancedSigningData.encode(signature: ByteArray): ByteArray =
    ByteArrayOutputStream().use { stream ->
        DataOutputStream(stream).use { writer ->
            writer.writeShort(ENHANCED_SIGNED_ENCODING_V1_MAGIC_NUMBER.toInt())
            writeSigningData(writer)
            writer.writeByteArray(signature)
        }
        stream.toByteArray()
    }

fun ByteArray.decodeSignedData(): EnhancedSignedData =
    ByteArrayInputStream(this).use { stream ->
        DataInputStream(stream).use { reader ->
            when (reader.readShort()) {
                ENHANCED_SIGNED_ENCODING_V1_MAGIC_NUMBER -> {
                    val timestamp = reader.readLong()
                    val signatureCodeName = reader.readUTF()
                    require(!signatureCodeName.isNullOrBlank()) {
                        "The signature spec id must not be blank."
                    }
                    val bytes = reader.readByteArray()
                    val signature = reader.readByteArray()
                    EnhancedSignedData(
                        signature = EnhancedSignature(
                            timestamp = Instant.ofEpochMilli(timestamp),
                            signatureCodeName = signatureCodeName,
                            signature = signature
                        ),
                        bytes = bytes
                    )
                }
                else -> throw CryptoServiceException("Unknown signature format.")
            }
        }
    }


private fun DataOutputStream.writeString(value: String?): DataOutputStream {
    if (value.isNullOrBlank()) {
        writeUTF("")
    } else {
        writeUTF(value)
    }
    return this
}

private fun DataOutputStream.writeByteArray(bytes: ByteArray) {
    write(bytes.sha256Bytes())
    write(bytes.size)
    write(bytes)
}

private fun DataInputStream.readByteArray(): ByteArray {
    val bytesSha256 = ByteArray(32)
    read(bytesSha256)
    val bytesSize = readInt()
    require(bytesSize > 0) {
        "The byte array must not be empty."
    }
    val bytes = ByteArray(bytesSize)
    read(bytes)
    require(bytes.sha256Bytes().contentEquals(bytesSha256)) {
        "Bad format."
    }
    return bytes
}