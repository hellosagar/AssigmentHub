package dev.sagar.assigmenthub.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sagar.assigmenthub.data.repositories.AuthRepo
import dev.sagar.assigmenthub.utils.Event
import dev.sagar.assigmenthub.utils.ResponseModel

class ProfileViewModel @ViewModelInject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private var _signOutUser: MutableLiveData<Event<ResponseModel<String>>> = MutableLiveData()
    var signOutUser: LiveData<Event<ResponseModel<String>>> = _signOutUser

    fun signOut() {
        authRepo.signOutTeacher { result ->
            _signOutUser.postValue(Event(result))
        }
    }
}
