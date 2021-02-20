package com.sagar.assigmenthub.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagar.assigmenthub.other.Event
import com.sagar.assigmenthub.other.ResponseModel
import com.sagar.assigmenthub.other.isEmailValid
import com.sagar.assigmenthub.repositories.AuthRepo

class RegisterViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private var _createUser: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var createUser: LiveData<Event<ResponseModel<String>>> = _createUser

    fun createUser(email: String, name: String, password: String, confirmPassword: String) {
        if (!validateInput(email, name, password, confirmPassword)) {
            return
        }

        authRepo.createUser(
            email, name, password
        ) { result ->
            _createUser.postValue(Event(result))
        }
    }

    private fun validateInput(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (name.isEmpty()) {
            _createUser.value = Event(ResponseModel.Error(null, "Name cannot be empty"))
            return false
        }

        if (email.isEmpty()) {
            _createUser.value = Event(ResponseModel.Error(null, "Email cannot be empty"))
            return false
        }

        if (!email.isEmailValid()) {
            _createUser.value = Event(ResponseModel.Error(null, "Email is not valid"))
            return false
        }

        if (password.isEmpty()) {
            _createUser.value = Event(ResponseModel.Error(null, "Password cannot be empty"))
            return false
        }

        if (confirmPassword.isEmpty()) {
            _createUser.value = Event(ResponseModel.Error(null, "Confirm password cannot be empty"))
            return false
        }

        if (password.length < 8 || password.length >= 20) {
            _createUser.value =
                Event(ResponseModel.Error(null, "Password must be at least 8 characters"))
            return false
        }

        if (confirmPassword.length < 8 || confirmPassword.length >= 20) {
            _createUser.value =
                Event(ResponseModel.Error(null, "Confirm Password must be at least 8 characters"))
            return false
        }

        if (password != confirmPassword) {
            _createUser.value =
                Event(ResponseModel.Error(null, "Password must be same"))
            return false
        }

        return true
    }
}
