package net.corda.v5.application.services

import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
interface TokenSelection {
    /**
     * blocking call to claim tokens
     */
    fun claim(criteria: ClaimCriteria, timeoutMilliseconds: Int): TokenClaim

    /**
     * Non-blocking call to claim tokens
     */
    fun tryClaim(criteria: ClaimCriteria): Pair<Boolean, TokenClaim?>
}

@DoNotImplement
interface TokenClaim {
    val claimId: String

    val claimedTokens: List<Token>

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
interface Token {
    val stateRef: String
    val tokenType: String
    val issuerHash: String
    val notaryHash: String
    val symbol: String
    var tagRegex: String
    var ownerHash: String
    val amount: Long
}

