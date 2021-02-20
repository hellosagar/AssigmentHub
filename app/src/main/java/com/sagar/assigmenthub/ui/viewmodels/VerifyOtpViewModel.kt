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

    private var _verifyUser: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var verifyUser: LiveData<Event<ResponseModel<String>>> = _verifyUser

    private var _loginUser: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var loginUser: LiveData<Event<ResponseModel<String>>> = _loginUser

    fun loginUser(email: String, password: String) {

        _loginUser.postValue(Event((ResponseModel.Loading())))

        authRepo.loginUser(
            email,
            password
        ) { result ->
            _loginUser.postValue(Event(result))
        }
    }

    fun verifyOtp(email: String, otp: String) {
        if (!validateInput(otp)) {
            return
        }

        authRepo.verifyUser(
            email, otp
        ) { result ->
            _verifyUser.postValue(Event(result))
        }
    }

    private fun validateInput(otp: String): Boolean {

        if (otp.isEmpty()) {
            _verifyUser.value = Event(ResponseModel.Error(null, "OTP cannot be empty"))
            return false
        }

        if (otp.length != 6) {
            _verifyUser.value = Event(ResponseModel.Error(null, "OTP should be $OTP_LENGTH digit"))
            return false
        }

        return true
    }
}
