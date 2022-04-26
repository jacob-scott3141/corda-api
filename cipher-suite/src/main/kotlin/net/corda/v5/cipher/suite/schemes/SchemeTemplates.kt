@file:JvmName("SchemeTemplates")

package net.corda.v5.cipher.suite.schemes

import net.corda.v5.crypto.CompositeKey
import net.corda.v5.crypto.OID_COMPOSITE_KEY_IDENTIFIER
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.ASN1ObjectIdentifier
import org.bouncycastle.asn1.DLSequence
import org.bouncycastle.asn1.bc.BCObjectIdentifiers
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers
import org.bouncycastle.asn1.gm.GMObjectIdentifiers
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
import org.bouncycastle.asn1.sec.SECObjectIdentifiers
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.spec.GOST3410ParameterSpec
import org.bouncycastle.pqc.jcajce.spec.SPHINCS256KeyGenParameterSpec

const val RSA_CODE_NAME = "CORDA.RSA"
const val ECDSA_SECP256K1_CODE_NAME = "CORDA.ECDSA.SECP256K1"
const val ECDSA_SECP256R1_CODE_NAME = "CORDA.ECDSA.SECP256R1"
const val EDDSA_ED25519_CODE_NAME = "CORDA.EDDSA.ED25519"
const val SM2_CODE_NAME = "CORDA.SM2"
const val GOST3410_CODE_NAME = "CORDA.GOST3410"
const val SPHINCS256_CODE_NAME = "CORDA.SPHINCS-256"
const val COMPOSITE_KEY_CODE_NAME = "COMPOSITE"

// OID taken from https://tools.ietf.org/html/draft-ietf-curdle-pkix-00
@JvmField
val ID_CURVE_25519PH = ASN1ObjectIdentifier("1.3.101.112")

/** DLSequence (ASN1Sequence) for SHA512 truncated to 256 bits, used in SPHINCS-256 signature scheme. */
@JvmField
val SHA512_256 = DLSequence(arrayOf(NISTObjectIdentifiers.id_sha512_256))


@JvmField
val RSA_TEMPLATE = KeySchemeTemplate(
    codeName = RSA_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, null)),
    algorithmName = "RSA",
    algSpec = null,
    keySize = 3072
)

@JvmField
val ECDSA_SECP256K1_TEMPLATE = KeySchemeTemplate(
    codeName = ECDSA_SECP256K1_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, SECObjectIdentifiers.secp256k1)),
    algorithmName = "EC",
    algSpec = ECNamedCurveTable.getParameterSpec("secp256k1"),
    keySize = null
)

@JvmField
val ECDSA_SECP256R1_TEMPLATE = KeySchemeTemplate(
    codeName = ECDSA_SECP256R1_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, SECObjectIdentifiers.secp256r1)),
    algorithmName = "EC",
    algSpec = ECNamedCurveTable.getParameterSpec("secp256r1"),
    keySize = null
)

@JvmField
val EDDSA_ED25519_TEMPLATE = KeySchemeTemplate(
    codeName = EDDSA_ED25519_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(ID_CURVE_25519PH, null)),
    algorithmName = "Ed25519",
    algSpec = EdDSAParameterSpec(EdDSAParameterSpec.Ed25519),
    keySize = null
)

@JvmField
val SPHINCS256_TEMPLATE = KeySchemeTemplate(
    codeName = SPHINCS256_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(BCObjectIdentifiers.sphincs256, DLSequence(arrayOf(ASN1Integer(0), SHA512_256)))),
    algorithmName = "SPHINCS256",
    algSpec = SPHINCS256KeyGenParameterSpec(SPHINCS256KeyGenParameterSpec.SHA512_256),
    keySize = null
)

@JvmField
val SM2_TEMPLATE = KeySchemeTemplate(
    codeName = SM2_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, GMObjectIdentifiers.sm2p256v1)),
    algorithmName = "EC",
    algSpec = ECNamedCurveTable.getParameterSpec("sm2p256v1"),
    keySize = null
)

@JvmField
val GOST3410_TEMPLATE = KeySchemeTemplate(
    codeName = GOST3410_CODE_NAME,
    algorithmOIDs = listOf(
        AlgorithmIdentifier(
            CryptoProObjectIdentifiers.gostR3410_94, DLSequence(
                arrayOf(
                    CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A,
                    CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet
                )
            )
        )
    ),
    algorithmName = "GOST3410",
    algSpec = GOST3410ParameterSpec(CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A.id),
    keySize = null
)

@JvmField
val COMPOSITE_KEY_TEMPLATE = KeySchemeTemplate(
    codeName = COMPOSITE_KEY_CODE_NAME,
    algorithmOIDs = listOf(AlgorithmIdentifier(OID_COMPOSITE_KEY_IDENTIFIER)),
    algorithmName = CompositeKey.KEY_ALGORITHM,
    algSpec = null,
    keySize = null
)
