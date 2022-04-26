package net.corda.v5.crypto.signing

import java.time.Instant

class EnhancedSigningData(
    val timestamp: Instant,
    val signatureCodeName: String,
    val bytes: ByteArray
) {
    val encoded: ByteArray by lazy(LazyThreadSafetyMode.PUBLICATION) {
        this.encode()
    }
}