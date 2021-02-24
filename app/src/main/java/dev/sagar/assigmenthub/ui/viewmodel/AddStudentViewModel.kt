package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.Branch
import com.amplifyframework.datastore.generated.model.Year
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.isEmailValid
import dev.sagar.assigmenthub.utils.isNumeric

class AddStudentViewModel @ViewModelInject constructor(
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    private var _createStudent: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var createStudent: LiveData<Event<ResponseModel<String>>> = _createStudent

    fun createStudent(name: String, rollNo: String, branchString: String, yearString: String, email: String) {
        if (!validateInput(name, rollNo, branchString, yearString, email)) {
            return
        }

        val year = Year.valueOf(yearString)
        val branch = Branch.valueOf(branchString)
        databaseRepo.createStudent(
            name, rollNo.toInt(), branch, year, email
        ) { result ->
            _createStudent.postValue(Event(result))
        }
    }

    private fun validateInput(name: String, rollNo: String, branch: String, year: String, email: String): Boolean {

        if (name.isEmpty()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Name cannot be empty"))
            return false
        }

        if (rollNo.isEmpty()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Roll number cannot be empty"))
            return false
        }

        if (!rollNo.isNumeric()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Roll number should be integer"))
            return false
        }

        if (branch.isEmpty()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Branch cannot be empty"))
            return false
        }

        if (year.isEmpty()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Year cannot be empty"))
            return false
        }

        if (email.isEmpty()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Email cannot be empty"))
            return false
        }

        if (!email.isEmailValid()) {
            _createStudent.value = Event(ResponseModel.Error(null, "Email is not valid"))
            return false
        }

        return true
    }
}
