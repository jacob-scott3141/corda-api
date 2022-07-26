package net.corda.v5.application.services

import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
interface TokenSelection {
    /**
     * Try and claim tokens to fill the required amount, this call will block for the specified time if tokens are
     * potentially available but currently claimed by another flow.
     *
     * @param criteria specifies the selection criteria and target amount to claim.
     * @param timeoutMilliseconds the maximum time in milliseconds the claim will wait for tokens to become available.
     * @return true if the claim completed within the timeout otherwise false.
     *         null if the claim could not be fulfilled, otherwise the set of claimed tokens.
     * @throws CordaServicException if an error happens during query operation
     */
    fun claim(criteria: ClaimCriteria, timeoutMilliseconds: Int): Pair<Boolean,ClaimedTokens?>

    /**
     * Try and claim tokens to fill the required amount, this call will return null if it can't immediately fulfil the
     * claim request.
     *
     * @param criteria specifies the selection criteria and target amount to claim.
     * @return null if the claim could not be fulfilled, otherwise the set of claimed tokens.
     * @throws CordaServicException if an error happens during query operation
     */
    fun tryClaim(criteria: ClaimCriteria): ClaimedTokens?
}

@DoNotImplement
interface ClaimedTokens {
    val claimId: String

    val tokens: List<ClaimedToken>

    fun releaseAll()

    fun releaseOnly(tokensToRelease: List<String>)

    fun useAndRelease(usedTokensRefs: List<String>)
}

class ClaimCriteria(
    val tokenType: String,
    val issuerHash: String,
    val notaryHash: String,
    val symbol: String,
    val targetAmount: Long
) {
    var tagRegex: String? = null
    var ownerHash: String? = null
}

@DoNotImplement
interface ClaimedToken {
    val stateRef: String
    val tokenType: String
    val issuerHash: String
    val notaryHash: String
    val symbol: String
    var tagRegex: String
    var ownerHash: String
    val amount: Long
}


