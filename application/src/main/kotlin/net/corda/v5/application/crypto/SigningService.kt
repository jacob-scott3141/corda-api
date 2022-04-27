package net.corda.v5.application.crypto

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.crypto.CompositeKey
import net.corda.v5.crypto.DigestAlgorithmName
import net.corda.v5.crypto.DigitalSignature
import net.corda.v5.crypto.SignatureScheme
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*

/**
 * The [SigningService] is responsible for storing and using private keys to sign things. An implementation of this may, for example,
 * call out to a hardware security module that enforces various auditing and frequency-of-use requirements.
 */
@DoNotImplement
interface SigningService : CordaServiceInjectable, CordaFlowInjectable {
    @Suspendable
    fun sign(bytes: ByteArray, publicKey: PublicKey, signatureScheme: SignatureScheme): DigitalSignature.WithKey

    @Suspendable
    fun sign(bytes: ByteArray, publicKey: PublicKey, signatureDigest: DigestAlgorithmName): DigitalSignature.WithKey
}
