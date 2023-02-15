package net.corda.v5.ledger.utxo

interface VaultNamedQueryCollector<T, R> {

    fun collect(resultSet: List<T>, parameters: Map<String, Any>): Result<R>

    class Result<R>(val resultSet: List<R>, val isDone: Boolean)

}