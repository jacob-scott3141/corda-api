package net.corda.v5.application.crypto

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.crypto.SignatureScheme
import java.security.InvalidKeyException
import java.security.PublicKey
import java.security.SignatureException

/**
 * The [DigitalSignatureVerificationService] digital signature verification operations.
 */
interface DigitalSignatureVerificationService : CordaServiceInjectable, CordaFlowInjectable {

    fun verify(publicKey: PublicKey, signatureData: ByteArray, clearData: ByteArray)
}