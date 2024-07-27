package com.cacuango_alexander.maurovision.ui.activities;

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.databinding.ActivityLoginBinding
import com.cacuango_alexander.maurovision.ui.activities.MainActivity
import com.cacuango_alexander.maurovision.ui.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var auth: FirebaseAuth
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var dialog: AlertDialog
    private val activityScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {

        // Debe llamar a setTheme() antes de super.onCreate() y setContentView()
        setTheme(R.style.SplashTheme) // Reemplaza 'Theme_Maurovision' con tu tema principal

        super.onCreate(savedInstanceState)
        Log.d("LoginActivity", "onCreate called")

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        initListeners()
        initObservables()
        autenticationVariables()
        loginViewModel.checkBiometric(this)
    }


    override fun onStart() {
        super.onStart()
        Log.d("LoginActivity", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LoginActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LoginActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LoginActivity", "onStop called")
        if (this::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.resultCheckBiometric.removeObservers(this)
        activityScope.cancel()
    }

    private fun initListeners() {
        Log.d("LoginActivity", "initListeners called")
        binding.btnAccess.setOnClickListener {
            val user = binding.txtInputUser.text.toString()
            val password = binding.txtInputPassword.text.toString()
            if (user.isNotBlank() && password.isNotBlank()) {
                signInUsers(user, password)
            } else {
                dialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.title_dialog_warning))
                    .setMessage(getString(R.string.txt_dialog_login))
                    .setPositiveButton(getString(R.string.aceptar)) { _, _ -> }
                    .setCancelable(false)
                    .create()

                dialog.show()
            }
        }

        binding.imgfinger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        binding.linkAccountCreate.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun autenticationVariables() {
        Log.d("LoginActivity", "autenticationVariables called")
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
    }

    private fun initObservables() {
        Log.d("LoginActivity", "initObservables called")
        loginViewModel.resultCheckBiometric.observe(this) { code ->
            when (code) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    binding.imgfinger.visibility = View.VISIBLE
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    binding.imgfinger.visibility = View.VISIBLE
                    binding.imgfinger.setImageResource(R.drawable.fingerprint)
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        )
                    }
                    startActivityForResult(enrollIntent, 100)
                }
            }
        }
    }

    private fun signInUsers(email: String, password: String) {
        Log.d("LoginActivity", "signInUsers called with email: $email")
        activityScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Log.d("LoginActivity", "signInWithEmail:success")
                // Espera a que todas las operaciones en curso se completen antes de iniciar MainActivity
                binding.root.post {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            } catch (e: Exception) {
                Log.w("LoginActivity", "signInWithEmail:failure", e)
                Snackbar.make(
                    binding.root,
                    "signInWithEmail:failure",
                    Snackbar.LENGTH_LONG
                ).show()
                binding.txtInputUser.text?.clear()
                binding.txtInputPassword.text?.clear()
            }
        }
    }
}