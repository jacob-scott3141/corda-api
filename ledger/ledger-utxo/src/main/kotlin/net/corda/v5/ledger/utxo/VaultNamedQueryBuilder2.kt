package net.corda.v5.ledger.utxo

interface VaultNamedQueryBuilder2 {

    // do we have functions for contract state type etc, or do they go in the JSON?

    fun filter(filter: Class<out VaultNamedQueryFilter<*>>): VaultNamedQueryBuilder2

    fun map(transform: Class<out VaultNamedQueryTransformer<*, *>>): VaultNamedQueryBuilder2

    fun where(where: String): VaultNamedQueryBuilder2

    // not done now
    fun select(select: String): VaultNamedQueryBuilder2

    fun register()
}