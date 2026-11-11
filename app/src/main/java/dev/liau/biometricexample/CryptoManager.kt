package dev.liau.biometricexample

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val keyAlias = "secret_pin_key"

    private fun getOrCreateSecretKey(): SecretKey {
        val existingKey = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createSecretKey()
    }

    private fun createSecretKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val spec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            // Wajib autentikasi biometrik setiap kali kunci digunakan
            .setUserAuthenticationRequired(true)
            // Jika pengguna menambah sidik jari baru di pengaturan HP, kunci otomatis hangus demi keamanan
            .setInvalidatedByBiometricEnrollment(true)
            .build()

        keyGen.init(spec)
        return keyGen.generateKey()
    }

    fun getEncryptCipher(): Cipher {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        return cipher
    }

    fun getDecryptCipher(iv: ByteArray): Cipher {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), spec)
        return cipher
    }
}
