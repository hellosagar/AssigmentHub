package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.datastore.generated.model.Status
import dev.sagar.assigmenthub.data.repositories.DatabaseRepo
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel

class HomeViewModel @ViewModelInject constructor(
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    private var _getAssignments: MutableLiveData<Event<ResponseModel<List<Assignment>>>> =
        MutableLiveData()
    var getAssignments: LiveData<Event<ResponseModel<List<Assignment>>>> = _getAssignments

    fun getAssignments(teacherID: String) {
        databaseRepo.getAssignments(
            teacherID
        ) { result ->
            _getAssignments.postValue(Event(result))
        }
    }

    fun getOnGoingAssignments(data: List<Assignment>): List<Assignment> {
        val list = mutableListOf<Assignment>()
        for (assignment in data) {
            if (assignment.status == Status.ONGOING) {
                list.add(assignment)
            }
        }
        return list
    }

    fun getEndedAssignments(data: List<Assignment>): List<Assignment> {
        val list = mutableListOf<Assignment>()
        for (assignment in data) {
            if (assignment.status == Status.ENDED) {
                list.add(assignment)
            }
        }
        return list
    }
}
