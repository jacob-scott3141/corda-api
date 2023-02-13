package net.corda.v5.ledger.utxo

interface VaultNamedQueryBuilderFactory {

    fun create(name: String): VaultNamedQueryBuilder
}