package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sagar.assigmenthub.data.repositories.AuthRepo
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.isEmailValid
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private var _createTeacher: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var createTeacher: LiveData<Event<ResponseModel<String>>> = _createTeacher

    fun createUser(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        if (!validateInput(email, name, password, confirmPassword)) return@launch

        _createTeacher.postValue(Event(ResponseModel.Loading()))

        authRepo.signUpTeacher(email, name, password).also {
            _createTeacher.postValue(Event(it))
        }
    }

    private fun validateInput(
        email: String,
        name: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (name.isEmpty()) {
            _createTeacher.value = Event(ResponseModel.Error(null, "Name cannot be empty"))
            return false
        }

        if (email.isEmpty()) {
            _createTeacher.value = Event(ResponseModel.Error(null, "Email cannot be empty"))
            return false
        }

        if (!email.isEmailValid()) {
            _createTeacher.value = Event(ResponseModel.Error(null, "Email is not valid"))
            return false
        }

        if (password.isEmpty()) {
            _createTeacher.value = Event(ResponseModel.Error(null, "Password cannot be empty"))
            return false
        }

        if (confirmPassword.isEmpty()) {
            _createTeacher.value =
                Event(ResponseModel.Error(null, "Confirm password cannot be empty"))
            return false
        }

        if (password.length < 8 || password.length >= 20) {
            _createTeacher.value =
                Event(ResponseModel.Error(null, "Password must be at least 8 characters"))
            return false
        }

        if (confirmPassword.length < 8 || confirmPassword.length >= 20) {
            _createTeacher.value =
                Event(ResponseModel.Error(null, "Confirm Password must be at least 8 characters"))
            return false
        }

        if (password != confirmPassword) {
            _createTeacher.value =
                Event(ResponseModel.Error(null, "Password must be same"))
            return false
        }

        return true
    }
}
