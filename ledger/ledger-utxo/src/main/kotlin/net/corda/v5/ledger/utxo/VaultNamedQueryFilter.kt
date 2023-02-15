package net.corda.v5.ledger.utxo

interface VaultNamedQueryFilter<T : ContractState> {

    fun filter(state: T, parameters: Map<String, Any>): Boolean
}