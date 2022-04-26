package net.corda.v5.cipher.suite.schemes

import net.corda.v5.crypto.DigestService
import net.corda.v5.crypto.SignatureScheme
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.assertThrows

class SignatureSchemeTests {
    companion object {
        private lateinit var digestService: DigestService

        @BeforeAll
        @JvmStatic
        fun setup() {
            digestService = DigestServiceMock()
        }
    }

    @Test
    @Timeout(5)
    fun `Should throw IllegalArgumentException when initializing with blank signature name`() {
        assertThrows<IllegalArgumentException> {
            SignatureScheme(
                codeName = "CODE1",
                signatureName = "  "
            )
        }
    }

    @Test
    @Timeout(5)
    fun `Should throw IllegalArgumentException when initializing with blank code name`() {
        assertThrows<IllegalArgumentException> {
            SignatureScheme(
                codeName = "   ",
                signatureName = "SHA256withRSA"
            )
        }
    }}
