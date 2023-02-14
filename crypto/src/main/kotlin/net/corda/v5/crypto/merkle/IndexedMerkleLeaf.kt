package net.corda.v5.crypto.merkle

import net.corda.v5.base.annotations.CordaSerializable

/**
 * [IndexedMerkleLeaf]s are building blocks of [MerkleProof]s.
 * They contain the required information about a particular leaf which is needed for the verification.
 *
 * @property index The leaf's index.
 * @property nonce The leaf's optional nonce.
 * @property leafData The leaf's data.
 *
 */

@CordaSerializable
class IndexedMerkleLeaf(
    val index: Int,
    val nonce: ByteArray?,
    val leafData: ByteArray
) {
    override fun toString(): String {
        return "Leaf($index)[${leafData.size} bytes]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IndexedMerkleLeaf

        if (index != other.index) return false
        if (nonce != null) {
            if (other.nonce == null) return false
            if (!nonce.contentEquals(other.nonce)) return false
        } else if (other.nonce != null) return false
        if (!leafData.contentEquals(other.leafData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = index
        result = 31 * result + (nonce?.contentHashCode() ?: 0)
        result = 31 * result + leafData.contentHashCode()
        return result
    }

}