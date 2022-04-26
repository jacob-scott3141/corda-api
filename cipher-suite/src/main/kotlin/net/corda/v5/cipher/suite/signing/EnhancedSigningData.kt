package net.corda.v5.cipher.suite.signing

import java.time.Instant

class EnhancedSigningData(
    val timestamp: Instant,
    val signatureCodeName: String,
    val bytes: ByteArray
)