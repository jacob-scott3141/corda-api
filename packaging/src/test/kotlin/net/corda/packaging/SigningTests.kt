@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")
package net.corda.packaging

import jdk.security.jarsigner.JarSigner
import net.corda.packaging.util.ZipTweaker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import sun.security.x509.AlgorithmId
import sun.security.x509.CertificateAlgorithmId
import sun.security.x509.CertificateExtensions
import sun.security.x509.CertificateSerialNumber
import sun.security.x509.CertificateValidity
import sun.security.x509.CertificateVersion
import sun.security.x509.CertificateX509Key
import sun.security.x509.KeyUsageExtension
import sun.security.x509.X500Name
import sun.security.x509.X509CertImpl
import sun.security.x509.X509CertInfo
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path
import java.security.CodeSigner
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.time.Duration
import java.time.Instant
import java.util.Date
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.security.auth.x500.X500Principal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SigningTests {
    private val secureRandom = SecureRandom()

    private val keyPairGenerator = KeyPairGenerator.getInstance("EC").apply {
        initialize(256, secureRandom)
    }

    private val certFactory = CertificateFactory.getInstance("X.509")

    private lateinit var tmpDir: Path
    private lateinit var originalJar: Path
    private lateinit var signedJar: Path
    private lateinit var jarWithEntryAdded: Path
    private lateinit var signedJarWithEntryAdded: Path
    private lateinit var editedSignedJarWithEntryAdded: Path

    private fun createCertificate(
        subject: X500Principal,
        subjectKey: PublicKey,
        issuer: X500Principal,
        issuerKey: PrivateKey,
        duration: Duration = Duration.ofDays(365),
        signAlgo: String = "SHA512withECDSA"
    ): Certificate {
        val info = X509CertInfo().also {
            val from = Date.from(Instant.now())
            val to = Date.from(from.toInstant().plus(duration))
            it[X509CertInfo.VALIDITY] = CertificateValidity(from, to)
            it[X509CertInfo.SERIAL_NUMBER] = CertificateSerialNumber(1)
            it[X509CertInfo.SUBJECT] = X500Name.asX500Name(subject)
            it[X509CertInfo.ISSUER] = X500Name.asX500Name(issuer)
            it[X509CertInfo.KEY] = CertificateX509Key(subjectKey)
            it[X509CertInfo.VERSION] = CertificateVersion(CertificateVersion.V3)
            it[X509CertInfo.ALGORITHM_ID] = CertificateAlgorithmId(AlgorithmId(AlgorithmId.sha512WithECDSA_oid))
            val keyUsage = KeyUsageExtension().apply {
                set(KeyUsageExtension.DIGITAL_SIGNATURE, true)
                set(KeyUsageExtension.KEY_CERTSIGN, true)
                set(KeyUsageExtension.CRL_SIGN, true)
            }
            it[X509CertInfo.EXTENSIONS] = CertificateExtensions().apply {
                this.set(KeyUsageExtension.NAME, keyUsage)
            }

        }
        var cert = X509CertImpl(info)
        cert.sign(issuerKey, signAlgo)

        val algo = cert[X509CertImpl.SIG_ALG] as AlgorithmId
        info[CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM] = algo
        cert = X509CertImpl(info)
        cert.sign(issuerKey, signAlgo)
        return cert
    }

    private fun createOriginalJar(destination: Path) {
        JarOutputStream(Files.newOutputStream(destination)).use { jos ->
            val entry = JarEntry("hello.txt")
            jos.putNextEntry(entry)
            BufferedWriter(OutputStreamWriter(jos)).let { writer ->
                writer.write("Hello world!!\n")
                writer.flush()
            }
            jos.closeEntry()
        }
    }

    private fun signJar(src: Path, dst: Path, key: PrivateKey, signatureFileName: String, vararg certs: Certificate) {
        val jarSignerBuilder = JarSigner.Builder(key, certFactory.generateCertPath(certs.toList()))
            .signerName(signatureFileName)
        Files.newOutputStream(dst).use { outputStream ->
            jarSignerBuilder.build().sign(ZipFile(src.toFile()), outputStream)
        }
    }

    private fun extractSignatures(jarFilePath : Path) : Map<String, Array<CodeSigner>> {
        return JarFile(jarFilePath.toFile(), true).use { jarFile ->
            jarFile.entries().asSequence().mapNotNull { entry ->
                jarFile.getInputStream(entry).use { it.copyTo(OutputStream.nullOutputStream()) }
                entry.takeIf { it.codeSigners != null }?.run { name to codeSigners }
            }.toMap()
        }
    }

    @BeforeEach
    fun setup(@TempDir testDir: Path) {
        tmpDir = testDir
        originalJar = testDir.resolve("original.jar")
        signedJar = tmpDir.resolve("signedJar.jar")
        jarWithEntryAdded = tmpDir.resolve("jarWithEntryAdded.jar")
        signedJarWithEntryAdded = tmpDir.resolve("signedJarWithEntryAdded.jar")
        editedSignedJarWithEntryAdded = tmpDir.resolve("editedSignedJarWithEntryAdded.jar")

        createOriginalJar(originalJar)
        val pair = keyPairGenerator.genKeyPair()
        val owner = X500Principal("C=IE, L=Dublin, CN=Signature test")
        val cert = createCertificate(owner, pair.public, owner, pair.private)
        signJar(originalJar, signedJar, pair.private, "SIGNER", cert)

        ZipOutputStream(Files.newOutputStream(jarWithEntryAdded)).use { zos ->
            ZipFile(signedJar.toFile()).use { zipFile ->
                zipFile.entries().asSequence().forEach { jarEntry ->
                    zos.putNextEntry(jarEntry)
                    zipFile.getInputStream(jarEntry).use { inputStream ->
                        inputStream.copyTo(zos)
                    }
                    zos.closeEntry()
                }
            }
            val entry = JarEntry("hello2.txt")
            zos.putNextEntry(entry)
            BufferedWriter(OutputStreamWriter(zos)).let { writer ->
                writer.write("Hello world2!!\n")
                writer.flush()
            }
            zos.closeEntry()
        }
        val pair2 = keyPairGenerator.genKeyPair()
        val signer2 = X500Principal("C=IE, L=Dublin, CN=Signature test2")
        val cert2 = createCertificate(signer2, pair2.public, signer2, pair2.private)
        signJar(jarWithEntryAdded, signedJarWithEntryAdded, pair2.private, "SIGNER2", cert2)

        object : ZipTweaker() {
            override fun tweakEntry(
                inputStream: ZipInputStream,
                outputStream: ZipOutputStream,
                currentEntry: ZipEntry,
                buffer: ByteArray
            ): AfterTweakAction {
                return when (currentEntry.name) {
                    "hello2.txt" -> {
                        writeZipEntry(
                            outputStream,
                            { ByteArrayInputStream("Hello world42!!\n".toByteArray()) },
                            currentEntry.name
                        )
                        AfterTweakAction.DO_NOTHING
                    }
                    else -> {
                        AfterTweakAction.WRITE_ORIGINAL_ENTRY
                    }
                }
            }
        }.run(Files.newInputStream(signedJarWithEntryAdded), Files.newOutputStream(editedSignedJarWithEntryAdded))
    }

    @Test
    fun `double signed jar have two code signers per entry`() {
        val signatureMap = extractSignatures(signedJarWithEntryAdded)
        signatureMap["hello.txt"]?.let {
            Assertions.assertEquals(2, it.size)
        } ?: throw IllegalStateException("No entry found with name 'hello.txt'")
    }

    @Test
    fun `if only one of the signatures is invalid verification fails`() {
        assertThrows<SecurityException> {
            extractSignatures(editedSignedJarWithEntryAdded)
        }
    }

    @Test
    fun `verification does not fail if entry streams are not completely consumed`() {
        assertDoesNotThrow {
            JarFile(editedSignedJarWithEntryAdded.toFile()).use { jarFile ->
                jarFile.entries().asSequence().forEach { it.codeSigners }
            }
        }
    }
}