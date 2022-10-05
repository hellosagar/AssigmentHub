package dev.sagar.assigmenthub.ui.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Teacher
import dev.sagar.assigmenthub.data.repositories.AuthRepo
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Constants
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.isEmailValid
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo,
    private val databaseRepo: DatabaseRepo,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private var _loginUser: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var loginUser: LiveData<Event<ResponseModel<String>>> = _loginUser

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        if (!validateInput(email, password)) return@launch

        _loginUser.postValue(Event((ResponseModel.Loading())))

        authRepo.signInTeacher(email, password).also {
            if (it is ResponseModel.Success) getTeacher(it.response, email)
            else if (it is ResponseModel.Error) postError(it)
        }
    }

    private fun postError(error: ResponseModel.Error<*>) {
        _loginUser.postValue(Event(ResponseModel.Error(error.error, error.message)))
    }

    private fun getTeacher(authResponse: String, email: String) {

        databaseRepo.getTeacher(
            email
        ) { result ->
            val teacher: Teacher = (result as ResponseModel.Success).response
            viewModelScope.launch {
                saveTeacherInfo(teacher.id, teacher.name, teacher.email)
                _loginUser.postValue(Event(ResponseModel.Success(authResponse)))
            }
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

    private suspend fun saveTeacherInfo(id: String, name: String, email: String) {
        val idKey = preferencesKey<String>(Constants.TEACHER_ID)
        val nameKey = preferencesKey<String>(Constants.TEACHER_NAME)
        val emailKey = preferencesKey<String>(Constants.TEACHER_EMAIL)

        dataStore.edit { settings ->
            settings[idKey] = id
            settings[nameKey] = name
            settings[emailKey] = email
        }
    }
}
