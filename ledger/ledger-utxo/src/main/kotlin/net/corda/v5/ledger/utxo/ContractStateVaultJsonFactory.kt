package net.corda.v5.ledger.utxo

import net.corda.v5.application.marshalling.JsonMarshallingService

interface ContractStateVaultJsonFactory<T : ContractState> {

    val stateType: Class<T>

    fun create(state: T, jsonMarshallingService: JsonMarshallingService): String
}