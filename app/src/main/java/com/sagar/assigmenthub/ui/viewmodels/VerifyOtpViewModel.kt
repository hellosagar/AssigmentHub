package com.sagar.assigmenthub.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagar.assigmenthub.other.Constants.OTP_LENGTH
import com.sagar.assigmenthub.other.Event
import com.sagar.assigmenthub.other.ResponseModel
import com.sagar.assigmenthub.repositories.AuthRepo

class VerifyOtpViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _verifyUser = MutableLiveData<Event<ResponseModel<String>>>()
    val verifyUser: LiveData<Event<ResponseModel<String>>> = _verifyUser

    fun verifyOtp(email: String, otp: String) {

        if (!validateInput(otp)) {
            return
        }

        _verifyUser.value = Event(ResponseModel.Loading())
        val response = authRepo.verifyUser(email, otp)
        _verifyUser.value = Event(response.value)
    }

    private fun validateInput(otp: String): Boolean {

        if (otp.isEmpty()) {
            _verifyUser.value = Event(ResponseModel.Error(null, "OTP cannot be empty"))
            return false
        }

        if (otp.length != 6) {
            _verifyUser.value = Event(ResponseModel.Error(null, "OTP should be $OTP_LENGTH"))
            return false
        }

        return true
    }
}
