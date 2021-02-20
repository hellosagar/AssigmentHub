package com.sagar.assigmenthub.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagar.assigmenthub.other.ResponseModel
import com.sagar.assigmenthub.other.isEmailValid
import com.sagar.assigmenthub.repositories.AuthRepo

class RegisterViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    var createUser: MutableLiveData<ResponseModel<String>> = MutableLiveData()

    fun createUser(email: String, name: String, password: String, confirmPassword: String) {
        if (!validateInput(email, name, password, confirmPassword)) {
            return
        }

        authRepo.createUser(email, name, password)
    }

    private fun validateInput(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (email.isEmpty()) {
            createUser.value = ResponseModel.Error(null, "Email cannot be empty")
            return false
        }

        if (!email.isEmailValid()) {
            createUser.value = ResponseModel.Error(null, "Email is not valid")
            return false
        }

        if (name.isEmpty()) {
            createUser.value = ResponseModel.Error(null, "Name cannot be empty")
            return false
        }

        if (password.isEmpty()) {
            createUser.value = ResponseModel.Error(null, "Password cannot be empty")
            return false
        }

        if (confirmPassword.isEmpty()) {
            createUser.value = ResponseModel.Error(null, "Confirm password cannot be empty")
            return false
        }

        if (password.length < 8 || password.length >= 20) {
            createUser.value =
                ResponseModel.Error(null, "Password must be at least 8 characters")
            return false
        }

        if (confirmPassword.length < 8 || confirmPassword.length >= 20) {
            createUser.value =
                ResponseModel.Error(null, "Confirm Password must be at least 8 characters")
            return false
        }

        return true
    }
}
