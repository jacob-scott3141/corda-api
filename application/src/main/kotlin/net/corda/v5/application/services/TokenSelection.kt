package net.corda.v5.application.services

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

@DoNotImplement
interface TokenSelection {
    /**
     * Try and claim tokens to fill the required amount, this call will block for the specified time if tokens are
     * potentially available but currently claimed by another flow.
     *
     * @param criteria specifies the selection criteria and target amount to claim.
     * @param timeoutMilliseconds the maximum time in milliseconds the claim will wait for tokens to become available.
     * @return TBD
     * @throws CordaServicException if an error happens during query operation
     */
    @Suspendable
    fun claim(criteria: ClaimCriteria, timeoutMilliseconds: Int): ClaimedTokens

    /**
     * Try and claim tokens to fill the required amount, this call will return null if it can't immediately fulfil the
     * claim request.
     *
     * @param criteria specifies the selection criteria and target amount to claim.
     * @return TBD
     * @throws CordaServicException if an error happens during query operation
     */
    @Suspendable
    fun tryClaim(criteria: ClaimCriteria): ClaimedTokens
}


