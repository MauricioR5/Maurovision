package com.cacuango_alexander.maurovision.ui.activities

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
        binding =ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        initListener()

    }

    private fun initListener(){
        binding.btnRegister.setOnClickListener {
            val user = binding.txtInputUser.text.toString()
            val password = binding.txtInputPassword.text.toString()
            if(user.isNotBlank() && password.isNotBlank()){
                createNewUsers(user, password)
            }else{
                dialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.title_dialog_warning))
                    .setMessage(getString(R.string.txt_dialog_register))
                    .setPositiveButton(getString(R.string.aceptar)) { _, _ ->
                        this
                    }
                    .setCancelable(false)
                    .create()

                dialog.show()
            }
        }
        binding.linkAccountCreate.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun createNewUsers(user:String, password:String){
        auth.createUserWithEmailAndPassword(user, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Snackbar.make(this,
                        binding.txtInputUser,
                        "createUserWithEmail:success",
                        Snackbar.LENGTH_LONG
                    ).show()
                    binding.txtInputUser.text?.clear()
                    binding.txtInputPassword.text?.clear()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        this,
                        binding.txtInputUser,
                        task.exception!!.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()

                    Log.d("TAG", task.exception!!.stackTraceToString())
                }
            }
    }

}