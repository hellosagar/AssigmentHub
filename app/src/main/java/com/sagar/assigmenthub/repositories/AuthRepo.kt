package com.sagar.assigmenthub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.sagar.assigmenthub.other.ResponseModel
import timber.log.Timber
import javax.inject.Inject

class AuthRepo @Inject constructor() {

    private var userLoginLiveData = MutableLiveData<ResponseModel<String>>()
    private var userSignUpLiveData = MutableLiveData<ResponseModel<String>>()
    private var verifyUserLiveData = MutableLiveData<ResponseModel<String>>()

    fun createUser(email: String, name: String, password: String): LiveData<ResponseModel<String>> {

        val attributes: ArrayList<AuthUserAttribute> = ArrayList()
        attributes.add(AuthUserAttribute(AuthUserAttributeKey.email(), email))
        attributes.add(AuthUserAttribute(AuthUserAttributeKey.name(), name))

        userSignUpLiveData.value = ResponseModel.Loading()

        Amplify.Auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().userAttributes(attributes).build(),
            { result ->
                Timber.i("AuthQuickStart Result: $result")
                userSignUpLiveData.postValue(ResponseModel.Success("User created"))
            },
            { error ->
                Timber.e("AuthQuickStart Sign up failed $error")
                userSignUpLiveData.postValue(ResponseModel.Error(error))
            }
        )

        return userSignUpLiveData
    }

    fun loginUser(email: String, password: String): LiveData<ResponseModel<String>> {

        userLoginLiveData.postValue(ResponseModel.Loading())

        Amplify.Auth.signIn(
            email,
            password,
            { result ->
                Timber.i("AuthQuickstart" + if (result.isSignInComplete) "Sign in succeeded" else "Sign in not complete")
                userLoginLiveData.postValue(ResponseModel.Success("User created"))
            },
            { error ->
                Timber.e("AuthQuickstart $error) ")
                userLoginLiveData.postValue(ResponseModel.Error(error))
            }
        )
        return userLoginLiveData
    }

    fun verifyUser(email: String, otp: String): LiveData<ResponseModel<String>> {

        verifyUserLiveData.value = ResponseModel.Loading()

        Amplify.Auth.confirmSignUp(
            email,
            otp,
            { result ->
                Timber.i("AuthQuickstart" + if (result.isSignUpComplete) "Confirm signUp succeeded" else "Confirm sign up not complete")
                verifyUserLiveData.postValue(ResponseModel.Success("User created"))
            },
            { error ->
                Timber.e("AuthQuickstart $error")
                verifyUserLiveData.postValue(ResponseModel.Error(error))
            }
        )

        return verifyUserLiveData
    }
}
