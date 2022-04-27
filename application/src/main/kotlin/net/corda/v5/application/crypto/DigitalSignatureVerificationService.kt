package net.corda.v5.application.crypto

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import java.security.PublicKey

/**
 * The [DigitalSignatureVerificationService] digital signature verification operations.
 */
interface DigitalSignatureVerificationService : CordaServiceInjectable, CordaFlowInjectable {

    fun verify(publicKey: PublicKey, signatureData: ByteArray, clearData: ByteArray)
}