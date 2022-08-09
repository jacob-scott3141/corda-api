package net.corda.v5.application.services

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
enum class ClaimedTokensResultType {
    SUCCESS, NONE_AVAILABLE, AVAILABLE_CLAIMED
}