package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Branch
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.Year
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.isEmailValid
import dev.sagar.assigmenthub.utils.isNumeric
import kotlinx.coroutines.launch

class AddStudentViewModel @ViewModelInject constructor(
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    private var _createStudent: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var createStudent: LiveData<Event<ResponseModel<String>>> = _createStudent

    fun createStudent(
        name: String,
        rollNo: String,
        branchString: String,
        yearString: String,
        email: String
    ) = viewModelScope.launch {
        if (!validateInput(name, rollNo, branchString, yearString, email)) return@launch

        _createStudent.postValue(Event(ResponseModel.Loading()))

        val year = Year.valueOf(yearString)
        val branch = Branch.valueOf(branchString)
        databaseRepo.createStudent(name, rollNo.toInt(), branch, year, email).also {
            if (it is ResponseModel.Success)
                getAllAssignmentsRelatedBranchYearID(it.response.id, it.response.branchYearId)
            else postError(null, "Unable to create the Student!")
        }
    }

    private fun postError(error: Throwable?, message: String) {
        _createStudent.postValue(Event(ResponseModel.Error(error, message)))
    }

    private fun getAllAssignmentsRelatedBranchYearID(studentID: String, branchYearID: String) =
        viewModelScope.launch {
            var mappingCount = 0
            databaseRepo.getAllAssignmentsSameBranchYearID(branchYearID).also { result ->
                if (result is ResponseModel.Success) {
                    if (result.response.isEmpty()) {
                        _createStudent.postValue(Event(ResponseModel.Success("Student created!")))
                        return@also
                    }
                    for (assignment in result.response) {
                        val status = assignment.status != Status.ONGOING

                        createStudentAssignmentMapping(studentID, assignment.id, status) {
                            mappingCount++
                            if (result.response.size == mappingCount)
                                _createStudent.postValue(Event(ResponseModel.Success("Student created!")))
                        }
                    }
                } else postError(null, "Unable to create the Student!")
            }
        }

    private suspend fun createStudentAssignmentMapping(
        studentID: String,
        assignmentID: String,
        status: Boolean,
        callback: (ResponseModel<String>) -> Unit
    ) {
        databaseRepo.createStudentAssignmentMapping(studentID, assignmentID, status).also(callback)
    }

    private fun validateInput(
        name: String,
        rollNo: String,
        branch: String,
        year: String,
        email: String
    ): Boolean {

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
