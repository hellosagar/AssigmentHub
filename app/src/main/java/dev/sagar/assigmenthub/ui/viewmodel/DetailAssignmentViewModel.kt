package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.datastore.generated.model.StudentAssignmentMapping
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel

class DetailAssignmentViewModel @ViewModelInject constructor(
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    private var _assignmentEnded: MutableLiveData<Event<ResponseModel<String>>> =
        MutableLiveData()
    var assignmentEnded: LiveData<Event<ResponseModel<String>>> = _assignmentEnded

    private var _studentAssignmentUpdated: MutableLiveData<Event<ResponseModel<String>>> =
        MutableLiveData()
    var studentAssignmentUpdated: LiveData<Event<ResponseModel<String>>> = _studentAssignmentUpdated

    fun updateStudentAssignmentMapping(studentAssignmentMapping: StudentAssignmentMapping) {
        databaseRepo.updateStudentAssignmentMapping(
            studentAssignmentMapping
        ) { result ->
            _studentAssignmentUpdated.postValue(Event(result))
        }
    }

    fun endAssignment(assignment: Assignment) {
        databaseRepo.endAssignment(
            assignment
        ) { result ->
            _assignmentEnded.postValue(Event(result))
        }
    }

    fun getCompletedStudentsList(data: Assignment): MutableList<StudentAssignmentMapping> {
        val list = mutableListOf<StudentAssignmentMapping>()
        for (studentAssignmentMapping in data.students) {
            if (studentAssignmentMapping.status == true) {
                list.add(studentAssignmentMapping)
            }
        }
        return list
    }

    fun getUncompletedStudentsList(data: Assignment): MutableList<StudentAssignmentMapping> {
        val list = mutableListOf<StudentAssignmentMapping>()
        for (studentAssignmentMapping in data.students) {
            if (studentAssignmentMapping.status == false) {
                list.add(studentAssignmentMapping)
            }
        }
        return list
    }
}
