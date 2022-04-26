package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.KeyScheme
import net.corda.v5.crypto.SignatureScheme

/**
 * Holding class for the private key material.
 *
 * @property tenantId The tenant id which the key pair belongs to.
 * @property keyMaterial The encoded and encrypted private key.
 * @property masterKeyAlias The wrapping key's alias which was used for wrapping, the value
 * could be null for HSMs which use built-in wrapping keys.
 * @property encodingVersion The encoding version which was used to encode the private key.
 * @property keyScheme The scheme for the signing operation.
 */
@Suppress("LongParameterList")
class SigningWrappedSpec(
    override val tenantId: String,
    val keyMaterial: ByteArray,
    val masterKeyAlias: String?,
    val encodingVersion: Int,
    override val keyScheme: KeyScheme,
    override val signatureScheme: SignatureScheme
) : SigningSpec