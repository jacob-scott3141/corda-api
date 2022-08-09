package net.corda.v5.application.services

import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
interface ClaimedToken {
    val stateRef: String
    val tokenType: String
    val issuerHash: String
    val notaryHash: String
    val symbol: String
    val tag: String?
    val ownerHash: String?
    val amount: Long
}