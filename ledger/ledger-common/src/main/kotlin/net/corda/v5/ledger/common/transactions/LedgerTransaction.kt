package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.crypto.SecureHash
import java.security.PublicKey

@DoNotImplement
interface LedgerTransaction {
    val id: SecureHash
    val wireTransaction: WireTransaction // TODO(to be removed if wireTx gets private)
    val requiredSigningKeys: List<PublicKey>
}