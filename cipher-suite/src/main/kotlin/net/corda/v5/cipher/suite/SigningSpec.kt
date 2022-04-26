package net.corda.v5.cipher.suite

import net.corda.v5.cipher.suite.schemes.KeyScheme

/**
 * Marker interface denoting the signing parameters.
 *
 * @property tenantId The tenant id which the key pair belongs to.
 * @property keyScheme The scheme for the signing operation.
 */
interface SigningSpec {
    val tenantId: String
    val keyScheme: KeyScheme
}