package net.corda.v5.crypto

import net.corda.v5.crypto.signing.EnhancedSignedData
import java.security.InvalidKeyException
import java.security.PublicKey
import java.security.SignatureException

/**
 * The [SignatureVerificationService] provides operation support for [TransactionSignatureVerificationService]
 * with base digital signature verification operations.
 */
interface SignatureVerificationService {
    /**
     * Verifies a digital signature by identifying the signature scheme used from the input public key's type.
     * Always throws an exception if verification fails.
     * Strategy on identifying the actual signing scheme is based on the [PublicKey] type, but if the schemeCodeName is known,
     * then better use doVerify(schemeCodeName: String, publicKey: PublicKey, signatureData: ByteArray, clearData: ByteArray).
     *
     * @param publicKey the signer's [PublicKey].
     * @param signatureData the signatureData on a message.
     * @param signedData signature and data.
     * @throws InvalidKeyException if the key is invalid.
     * @throws SignatureException  if verification fails.
     * @throws IllegalArgumentException if the signature scheme is not supported or if any of the clear or signature data is empty.
     */
    fun verify(publicKey: PublicKey, signedData: EnhancedSignedData)
}