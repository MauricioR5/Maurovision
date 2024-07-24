package com.cacuango_alexander.maurovision.ui.activities

import android.content.Intent
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
import com.cacuango_alexander.maurovision.ui.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    // biometric
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    // Authentication
    private lateinit var auth: FirebaseAuth
    //MV
    private val loginViewModel : LoginViewModel by viewModels()
    //Dialog
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        initListeners()
        initObservables()
        autenticationVariables()
        loginViewModel.checkBiometric(this)
    }

    fun initListeners() {
        binding.btnAccess.setOnClickListener {
            val user = binding.txtInputUser.text.toString()
            val password = binding.txtInputPassword.text.toString()
            if(user.isNotBlank() && password.isNotBlank()){
                signInUsers(user, password)
            }else{
                dialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.title_dialog_warning))
                    .setMessage(getString(R.string.txt_dialog_login))
                    .setPositiveButton(getString(R.string.aceptar)) { _, _ ->
                        this
                    }
                    .setCancelable(false)
                    .create()

                dialog.show()
            }

        }
        binding.imgfinger.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
        binding.linkAccountCreate.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun autenticationVariables(){
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

    private fun initObservables(){
        loginViewModel.resultCheckBiometric.observe(this){code ->
            when(code){
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    binding.imgfinger.visibility = View.VISIBLE
                    binding.txtFinger.visibility = View.VISIBLE
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    binding.txtFinger.text = getString(R.string.biometric_no_hardware)
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

    // SignIn para firebase
    private fun signInUsers(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Snackbar.make(
                        this,
                        binding.txtInputUser,
                        "signInWithEmail:failure",
                        Snackbar.LENGTH_LONG
                    ).show()

                }
                binding.txtInputUser.text?.clear()
                binding.txtInputPassword.text?.clear()
            }
    }

}