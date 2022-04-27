package net.corda.v5.crypto.signing

import java.time.Instant

class EnhancedSignature(
    val timestamp: Instant, // delete
    val signatureCodeName: String,
    val signature: ByteArray
) {
    init {
        require(signatureCodeName.isNotEmpty()) { "The signature spec id must not be empty." }
        require(signature.isNotEmpty()) { "The signature must not be empty." }
    }

    val encoded: ByteArray by lazy(LazyThreadSafetyMode.PUBLICATION) {
        this.encode()
    }
}