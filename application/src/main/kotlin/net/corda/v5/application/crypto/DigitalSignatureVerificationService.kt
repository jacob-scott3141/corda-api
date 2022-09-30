package net.corda.v5.application.crypto

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.SignatureSpec
import net.corda.v5.crypto.exceptions.CryptoSignatureException
import java.security.PublicKey

/**
 * Allows flows to verify digital signatures.
 *
 * Corda provides an instance of [DigitalSignatureVerificationService] to flows via property injection.
 */
@DoNotImplement
interface DigitalSignatureVerificationService {
    /**
     * Verifies a digital signature by using [signatureSpec].
     * Always throws an exception if verification fails.
     *
     * @param publicKey The signer's [PublicKey].
     * @param signatureSpec The signature spec.
     * @param signatureData The signatureData on a message.
     * @param clearData The clear data/message that was signed (usually the Merkle root).
     *
     * @throws CryptoSignatureException If verification of the digital signature fails.
     * @throws IllegalArgumentException If the signature scheme is not supported or if any of the clear or signature
     * data is empty.
     */
    fun verify(publicKey: PublicKey, signatureSpec: SignatureSpec, signatureData: ByteArray, clearData: ByteArray)

    /**
     * Verifies a digital signature by inferring [SignatureSpec] from the [publicKey] and
     * the [digest], e.g. for the "CORDA.ECDSA.SECP256R1" it will use "SHA256withECDSA" and for "CORDA.EDDSA.ED25519"
     * it will use "Ed25519".
     * Always throws an exception if verification fails.
     *
     * @param publicKey The signer's [PublicKey].
     * @param digest The digest spec.
     * @param signatureData The signatureData on a message.
     * @param clearData The clear data/message that was signed (usually the Merkle root).
     *
     * @throws CryptoSignatureException If verification of the digital signature fails.
     * @throws IllegalArgumentException If the signature scheme is not supported or if any of the clear or signature
     * data is empty.
     */
    fun verify(publicKey: PublicKey, digest: DigestAlgorithmName, signatureData: ByteArray, clearData: ByteArray)

    /**
     * Verifies a digital signature by using default [SignatureSpec] for the given [publicKey]'s scheme,
     * e.g. for the "CORDA.ECDSA.SECP256R1" it will use "SHA256withECDSA" and for "CORDA.EDDSA.ED25519"
     * it will use "Ed25519".
     * Always throws an exception if verification fails.
     *
     * @param publicKey The signer's [PublicKey].
     * @param digest The digest spec.
     * @param signatureData The signatureData on a message.
     * @param clearData The clear data/message that was signed (usually the Merkle root).
     *
     * @throws CryptoSignatureException If verification of the digital signature fails.
     * @throws IllegalArgumentException If the signature scheme is not supported or if any of the clear or signature
     * data is empty.
     */
    fun verify(publicKey: PublicKey, signatureData: ByteArray, clearData: ByteArray)}
