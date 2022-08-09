package net.corda.v5.application.services

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
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

