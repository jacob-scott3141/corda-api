package net.corda.v5.ledger.utxo

interface VaultNamedQueryTransformer<T : ContractState, R> {

    fun transform(state: T, parameters: Map<String, Any>): R
}