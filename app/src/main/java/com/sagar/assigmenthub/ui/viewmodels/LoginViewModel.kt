package com.sagar.assigmenthub.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagar.assigmenthub.other.Event
import com.sagar.assigmenthub.other.ResponseModel
import com.sagar.assigmenthub.other.isEmailValid
import com.sagar.assigmenthub.repositories.AuthRepo

class LoginViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private var _loginUser: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var loginUser: LiveData<Event<ResponseModel<String>>> = _loginUser

    fun loginUser(email: String, password: String) {

        if (!validateInput(email, password)) {
            return
        }

        _loginUser.postValue(Event((ResponseModel.Loading())))

        authRepo.loginUser(
            email,
            password
        ) { result ->
            _loginUser.postValue(Event(result))
        }
    }

    private fun validateInput(email: String, password: String): Boolean {

        if (email.isEmpty()) {
            _loginUser.value = Event((ResponseModel.Error(null, "Email cannot be empty")))
            return false
        }

        if (!email.isEmailValid()) {
            _loginUser.value = Event(ResponseModel.Error(null, "Email is not valid"))
            return false
        }

        if (password.isEmpty()) {
            _loginUser.value = Event(ResponseModel.Error(null, "Password cannot be empty"))
            return false
        }

        if (password.length < 8 || password.length >= 20) {
            _loginUser.value =
                Event(ResponseModel.Error(null, "Password must be at least 8 characters"))
            return false
        }

        return true
    }
}
