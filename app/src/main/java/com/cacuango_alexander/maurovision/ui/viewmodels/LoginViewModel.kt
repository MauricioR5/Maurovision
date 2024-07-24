package com.cacuango_alexander.maurovision.ui.viewmodels

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val resultCheckBiometric = MutableLiveData<Int>()
    fun checkBiometric(context: Context){
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_SUCCESS)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->{
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE)
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->{
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE)
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED)
                // Prompts the user to create credentials that your app accepts.
            }
        }
    }
}