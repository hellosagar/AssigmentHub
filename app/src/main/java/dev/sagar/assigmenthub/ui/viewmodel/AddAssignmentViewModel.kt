package dev.sagar.assigmenthub.ui.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.datastore.generated.model.Branch
import com.amplifyframework.datastore.generated.model.Year
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Constants
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.getTeacherInfo
import kotlinx.coroutines.launch
import java.util.Date

class AddAssignmentViewModel @ViewModelInject constructor(
    private val databaseRepo: DatabaseRepo,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private var _createAssignment: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var createAssignment: LiveData<Event<ResponseModel<String>>> = _createAssignment

    fun createAssignment(
        name: String,
        subject: String,
        branchString: String,
        yearString: String,
        lastDateString: String,
        description: String
    ) {
        if (!validateInput(name, subject, branchString, yearString, lastDateString, description)) {
            return
        }

        _createAssignment.postValue(Event(ResponseModel.Loading()))
        val year = Year.valueOf(yearString)
        val branch = Branch.valueOf(branchString)
        val date = Date()
        viewModelScope.launch {
            val teacherID = getTeacherInfo(dataStore, Constants.TEACHER_ID)

            databaseRepo.createAssignment(
                name, subject, branch, year, date, description, teacherID
            ) { result ->
                if (result is ResponseModel.Success) {
                    getAllStudentWithBranchYear(result.response)
                } else {
                    _createAssignment.postValue(
                        Event(
                            ResponseModel.Error(
                                null,
                                "Unable to create the Assignment!"
                            )
                        )
                    )
                }
            }
        }
    }

    private fun getAllStudentWithBranchYear(assignment: Assignment) {
        var mappingCount = 0
        val branchYearID = assignment.branchYearId

        databaseRepo.getStudentsFromBranchYearID(
            branchYearID
        ) { result ->
            if (result is ResponseModel.Success) {
                for (student in result.response) {
                    createStudentAssignmentMapping(
                        student.id, assignment.id
                    ) {
                        mappingCount++
                        if (result.response.size == mappingCount) {
                            _createAssignment.postValue(
                                Event(
                                    ResponseModel.Success(
                                        "Assignment created!"
                                    )
                                )
                            )
                        }
                    }
                }
            } else {
                _createAssignment.postValue(
                    Event(
                        ResponseModel.Error(
                            null,
                            "Unable to create the Assignment!"
                        )
                    )
                )
            }
        }
    }

    private fun createStudentAssignmentMapping(
        studentID: String,
        assignmentID: String,
        callback: (ResponseModel<String>) -> Unit
    ) {
        databaseRepo.createStudentAssignmentMapping(studentID, assignmentID, callback)
    }

    private fun validateInput(
        name: String,
        subject: String,
        branchString: String,
        yearString: String,
        lastSubmissionDateString: String,
        description: String
    ): Boolean {

        if (name.isEmpty()) {
            _createAssignment.value = Event(ResponseModel.Error(null, "Name cannot be empty"))
            return false
        }

        if (subject.isEmpty()) {
            _createAssignment.value = Event(ResponseModel.Error(null, "Subject cannot be empty"))
            return false
        }

        if (branchString.isEmpty()) {
            _createAssignment.value = Event(ResponseModel.Error(null, "Branch cannot be empty"))
            return false
        }

        if (yearString.isEmpty()) {
            _createAssignment.value = Event(ResponseModel.Error(null, "Year cannot be empty"))
            return false
        }

        if (lastSubmissionDateString.isEmpty()) {
            _createAssignment.value =
                Event(ResponseModel.Error(null, "Last submission date cannot be empty"))
            return false
        }

        if (description.isEmpty()) {
            _createAssignment.value =
                Event(ResponseModel.Error(null, "Description cannot be empty"))
            return false
        }

        return true
    }
}
