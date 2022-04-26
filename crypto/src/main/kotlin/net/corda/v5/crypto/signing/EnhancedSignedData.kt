package net.corda.v5.crypto.signing

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