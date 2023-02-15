package net.corda.v5.ledger.utxo

// in the filter and map we need to know the query parameters.
interface VaultNamedQueryBuilder {

    // do we have functions for contract state type etc, or do they go in the JSON?

//    fun filter(filter: Class<out VaultNamedQueryFilter<*>>): VaultNamedQueryBuilder
//
//    fun map(transform: Class<out VaultNamedQueryTransformer<*, *>>): VaultNamedQueryBuilder

    fun filter(filter: VaultNamedQueryFilter<*>): VaultNamedQueryBuilder

    fun map(transform: VaultNamedQueryTransformer<*, *>): VaultNamedQueryBuilder

    fun collect(transform: VaultNamedQueryCollector<*, *>): VaultNamedQueryBuilder

    fun whereStateType(type: Class<out ContractState>)

    fun whereParty()

    fun whereJson(where: String): VaultNamedQuery

    // not done now
    fun select(select: String): VaultNamedQuery
}