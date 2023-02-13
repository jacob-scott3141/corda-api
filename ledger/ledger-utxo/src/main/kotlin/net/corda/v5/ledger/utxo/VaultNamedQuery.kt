package net.corda.v5.ledger.utxo

interface VaultNamedQuery {

    val name: String
    val query: String
}