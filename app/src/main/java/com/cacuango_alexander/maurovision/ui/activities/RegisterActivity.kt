package com.cacuango_alexander.maurovision.ui.activities;
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            val user = binding.txtInputUser.text.toString()
            val password = binding.txtInputPassword.text.toString()
            if (user.isNotBlank() && password.isNotBlank()) {
                createNewUsers(user, password)
            } else {
                dialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.title_dialog_warning))
                    .setMessage(getString(R.string.txt_dialog_register))
                    .setPositiveButton(getString(R.string.aceptar)) { _, _ -> }
                    .setCancelable(false)
                    .create()

                dialog.show()
            }
        }

        binding.linkAccountCreate.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun createNewUsers(user: String, password: String) {
        auth.createUserWithEmailAndPassword(user, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "createUserWithEmail:success",
                        Snackbar.LENGTH_LONG
                    ).show()
                    binding.txtInputUser.text?.clear()
                    binding.txtInputPassword.text?.clear()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        binding.root,
                        task.exception?.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }
}
