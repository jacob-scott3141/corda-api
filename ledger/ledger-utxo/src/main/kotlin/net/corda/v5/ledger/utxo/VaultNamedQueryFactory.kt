package net.corda.v5.ledger.utxo

interface VaultNamedQueryFactory {

    fun create(vaultNamedQueryBuilderFactory: VaultNamedQueryBuilderFactory): List<VaultNamedQuery>
}