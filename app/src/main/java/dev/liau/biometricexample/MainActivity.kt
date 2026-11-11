package dev.liau.biometricexample

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.nio.charset.StandardCharsets
import android.util.Base64
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val cryptoManager = CryptoManager()
    private lateinit var tvResult: TextView
    private lateinit var etPin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initListeners()
    }

    private fun initListeners() {
        etPin = findViewById(R.id.etPin)
        tvResult = findViewById(R.id.tvResult)
        val btnEncryptSave = findViewById<Button>(R.id.btnEncryptSave)
        val btnDecryptRead = findViewById<Button>(R.id.btnDecryptRead)

        // 1. Proses Enkripsi & Simpan PIN
        btnEncryptSave.setOnClickListener {
            val pinText = etPin.text.toString()
            if (pinText.isEmpty()) {
                Toast.makeText(this, "Isi PIN terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (canAuthenticateStrong()) {
                val cipher = cryptoManager.getEncryptCipher()
                showBiometricPrompt(
                    cryptoObject = BiometricPrompt.CryptoObject(cipher),
                    onSuccess = { authCipher ->
                        val encryptedBytes = authCipher.doFinal(pinText.toByteArray(StandardCharsets.UTF_8))
                        saveEncryptedData(encryptedBytes, authCipher.iv)
                        etPin.text.clear()
                        val resultText = String.format(getString(R.string.status_placeholder), " PIN berhasil dienkripsi dan disimpan!")
                        tvResult.text = resultText
                    }
                )
            }
        }

        // 2. Proses Baca & Dekripsi PIN
        btnDecryptRead.setOnClickListener {
            val (encryptedData, iv) = getEncryptedData()
            if (encryptedData == null || iv == null) {
                Toast.makeText(this, "Belum ada PIN tersimpan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (canAuthenticateStrong()) {
                val cipher = cryptoManager.getDecryptCipher(iv)
                showBiometricPrompt(
                    cryptoObject = BiometricPrompt.CryptoObject(cipher),
                    onSuccess = { authCipher ->
                        val decryptedBytes = authCipher.doFinal(encryptedData)
                        val originalPin = String(decryptedBytes, StandardCharsets.UTF_8)
                        tvResult.text = "Status: PIN Asli Anda adalah $originalPin"
                    }
                )
            }
        }
    }

    private fun canAuthenticateStrong(): Boolean {
        val biometricManager = BiometricManager.from(this)
        // CryptoObject wajib menggunakan BIOMETRIC_STRONG (Class 3)
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Toast.makeText(this, "Perangkat tidak mendukung biometrik kuat / belum terdaftar", Toast.LENGTH_LONG).show()
                false
            }
        }
    }

    private fun showBiometricPrompt(
        cryptoObject: BiometricPrompt.CryptoObject,
        onSuccess: (javax.crypto.Cipher) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    result.cryptoObject?.cipher?.let { unlockedCipher ->
                        onSuccess(unlockedCipher)
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    tvResult.text = "Error: $errString"
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verifikasi Keamanan PIN")
            .setSubtitle("Sentuh sensor sidik jari untuk melanjutkan")
            .setNegativeButtonText("Batal")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo, cryptoObject)
    }

    // Simpan ciphertext dan IV ke SharedPreferences dalam format Base64
    private fun saveEncryptedData(ciphertext: ByteArray, iv: ByteArray) {
        val prefs = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
        prefs.edit {
            putString("encrypted_pin", Base64.encodeToString(ciphertext, Base64.DEFAULT))
                .putString("pin_iv", Base64.encodeToString(iv, Base64.DEFAULT))
        }
    }

    private fun getEncryptedData(): Pair<ByteArray?, ByteArray?> {
        val prefs = getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
        val cipherTextStr = prefs.getString("encrypted_pin", null)
        val ivStr = prefs.getString("pin_iv", null)

        if (cipherTextStr == null || ivStr == null) return Pair(null, null)

        return Pair(
            Base64.decode(cipherTextStr, Base64.DEFAULT),
            Base64.decode(ivStr, Base64.DEFAULT)
        )
    }
}