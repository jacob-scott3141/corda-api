package net.corda.v5.ledger.consensual

import net.corda.v5.application.messaging.FlowSession
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

data class ConsensualTestRequest(val session: FlowSession)

interface ConsensualTestResponse {
    val session: FlowSession

    @Suspendable
    fun createResponse(): String
}

@DoNotImplement
interface ConsensualLedgerService {
    @Suspendable
    fun double(n: Int): Int

    @Suspendable
    fun startTestFlow(request: ConsensualTestRequest): String

    @Suspendable
    fun receiveTestFlow(request: ConsensualTestResponse)

}
