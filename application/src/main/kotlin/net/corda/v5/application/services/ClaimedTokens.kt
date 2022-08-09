package net.corda.v5.application.services

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

@DoNotImplement
interface ClaimedTokens {
    val claimId: String

    val tokens: List<ClaimedToken>

    val resultType: ClaimedTokensResultType

    @Suspendable
    fun releaseAll()

    @Suspendable
    fun releaseOnly(tokenRefsToRelease: List<String>)

    @Suspendable
    fun useAndRelease(usedTokensRefs: List<String>)
}