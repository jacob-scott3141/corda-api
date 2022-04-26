@file:JvmName("SignatureSpecs")

package net.corda.v5.crypto

import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec

@JvmField
val RSA_SHA256 = SignatureScheme(
    codeName = "CORDA.RSA.SHA256",
    signatureName = "SHA256withRSA"
)

@JvmField
val RSA_SHA512 = SignatureScheme(
    codeName = "CORDA.RSA.SHA512",
    signatureName = "SHA512withRSA"
)

@JvmField
val RSASSA_PSS_SHA256 = SignatureScheme(
        codeName = "CORDA.RSASSA-PSS.SHA256",
        signatureName = "RSASSA-PSS",
        params = PSSParameterSpec(
            "SHA-256",
            "MGF1",
            MGF1ParameterSpec.SHA256,
            32,
            1
        )
    )

@JvmField
val RSASSA_PSS_SHA512 = SignatureScheme(
    codeName = "CORDA.RSASSA-PSS.SHA512",
        signatureName = "RSASSA-PSS",
        params = PSSParameterSpec(
            "SHA-512",
            "MGF1",
            MGF1ParameterSpec.SHA512,
            64,
            1
        )
    )

@JvmField
val ECDSA_SHA256 = SignatureScheme(
    codeName = "CORDA.ECDSA.SHA256",
    signatureName = "SHA256withECDSA"
)

@JvmField
val ECDSA_SHA512 = SignatureScheme(
    codeName = "CORDA.ECDSA.SHA512",
    signatureName = "SHA512withECDSA"
)

@JvmField
val EDDSA_ED25519_NONE = SignatureScheme(
    codeName = "CORDA.EDDSA.NONE",
    signatureName = "EdDSA"
)

@JvmField
val EDDSA_ED25519_CUSTOM_SHA256 = SignatureScheme(
    codeName = "CORDA.EDDSA.SHA256",
    signatureName = "EdDSA",
    customDigestName = DigestAlgorithmName("SHA-256")
)

@JvmField
val EDDSA_ED25519_CUSTOM_SHA512 = SignatureScheme(
    codeName = "CORDA.EDDSA.CHA512",
    signatureName = "EdDSA",
    customDigestName = DigestAlgorithmName("SHA-512")
)

@JvmField
val SPHINCS256_SHA512 = SignatureScheme(
    codeName = "CORDA.SPHINCS.SHA512",
    signatureName = "SHA512withSPHINCS256"
)

@JvmField
val SM2_SM3 = SignatureScheme(
    codeName = "CORDA.SM2.SM3",
    signatureName = "SM3withSM2"
)

@JvmField
val GOST3410_GOST3411 = SignatureScheme(
    codeName = "CORDA.GOST3410.GOST3411",
    signatureName = "GOST3411withGOST3410"
)