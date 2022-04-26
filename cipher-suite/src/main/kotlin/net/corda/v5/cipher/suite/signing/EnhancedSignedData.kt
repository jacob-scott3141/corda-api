package net.corda.v5.cipher.suite.signing

class EnhancedSignedData(
    val signature: EnhancedSignature,
    val bytes: ByteArray,
) {
    init {
        require(bytes.isNotEmpty()) {
            "The data must not be empty."
        }
    }
}