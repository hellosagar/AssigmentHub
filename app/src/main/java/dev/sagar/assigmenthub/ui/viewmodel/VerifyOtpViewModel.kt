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
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.datastore.generated.model.Teacher
import dev.sagar.assigmenthub.data.repositories.AuthRepo
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Constants.OTP_LENGTH
import dev.sagar.assigmenthub.utils.Constants.TEACHER_EMAIL
import dev.sagar.assigmenthub.utils.Constants.TEACHER_ID
import dev.sagar.assigmenthub.utils.Constants.TEACHER_NAME
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel
import kotlinx.coroutines.launch

class VerifyOtpViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo,
    private val databaseRepo: DatabaseRepo,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private var _verifyTeacher: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var verifyTeacher: LiveData<Event<ResponseModel<String>>> = _verifyTeacher

    private var _loginTeacher: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var loginTeacher: LiveData<Event<ResponseModel<String>>> = _loginTeacher

    private var _createTeacher: MutableLiveData<Event<ResponseModel<GraphQLResponse<Teacher>>>> =
        MutableLiveData()
    var createTeacher: LiveData<Event<ResponseModel<GraphQLResponse<Teacher>>>> = _createTeacher

    fun loginTeacher(email: String, password: String) {
        authRepo.signInTeacher(
            email,
            password
        ) { result ->
            _loginTeacher.postValue(Event(result))
        }
    }

    fun verifyOtp(email: String, otp: String) {
        if (!validateInput(otp)) {
            return
        }

        _verifyTeacher.postValue(Event((ResponseModel.Loading())))

        authRepo.confirmTeacher(
            email, otp
        ) { result ->
            _verifyTeacher.postValue(Event(result))
        }
    }

    fun createTeacher(name: String, email: String) {
        databaseRepo.createTeacher(
            name,
            email
        ) { result ->
            viewModelScope.launch {
                val teacher: Teacher = (result as ResponseModel.Success).response.data
                saveTeacherInfo(teacher.id, teacher.name, teacher.email)
                _createTeacher.postValue(Event(result))
            }
        }
    }

    private fun validateInput(otp: String): Boolean {

        if (otp.isEmpty()) {
            _verifyTeacher.value = Event(ResponseModel.Error(null, "OTP cannot be empty"))
            return false
        }

        if (otp.length != 6) {
            _verifyTeacher.value =
                Event(ResponseModel.Error(null, "OTP should be $OTP_LENGTH digit"))
            return false
        }

        return true
    }

    private suspend fun saveTeacherInfo(id: String, name: String, email: String) {
        val idKey = preferencesKey<String>(TEACHER_ID)
        val nameKey = preferencesKey<String>(TEACHER_NAME)
        val emailKey = preferencesKey<String>(TEACHER_EMAIL)

        dataStore.edit { settings ->
            settings[idKey] = id
            settings[nameKey] = name
            settings[emailKey] = email
        }
    }
}
