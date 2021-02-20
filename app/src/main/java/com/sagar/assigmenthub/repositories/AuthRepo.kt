package com.sagar.assigmenthub.repositories

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.sagar.assigmenthub.other.ResponseModel
import timber.log.Timber
import javax.inject.Inject

class AuthRepo @Inject constructor() {

    fun createUser(
        email: String,
        name: String,
        password: String,
        callback: (ResponseModel<String>) -> Unit
    ) {

        val attributes: ArrayList<AuthUserAttribute> = ArrayList()
        attributes.add(AuthUserAttribute(AuthUserAttributeKey.email(), email))
        attributes.add(AuthUserAttribute(AuthUserAttributeKey.name(), name))

        Amplify.Auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().userAttributes(attributes).build(),
            { result ->
                Timber.i("AuthQuickStart Result: $result")
                callback(ResponseModel.Success("User created"))
            },
            { error ->
                Timber.e("AuthQuickStart Sign up failed $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun loginUser(
        email: String,
        password: String,
        callback: (ResponseModel<String>) -> Unit
    ) {

        Amplify.Auth.signIn(
            email,
            password,
            { result ->
                Timber.i("AuthQuickstart" + if (result.isSignInComplete) "Sign in succeeded" else "Sign in not complete")
                callback(ResponseModel.Success("User created $result"))
            },
            { error ->
                Timber.e("AuthQuickstart $error) ")
                callback(ResponseModel.Error(null, error.recoverySuggestion))
            }
        )
    }

    fun verifyUser(
        email: String,
        otp: String,
        callback: (ResponseModel<String>) -> Unit
    ) {

        Amplify.Auth.confirmSignUp(
            email,
            otp,
            { result ->
                Timber.i("AuthQuickstart" + if (result.isSignUpComplete) "Confirm signUp succeeded" else "Confirm sign up not complete")
                callback(ResponseModel.Success("User created"))
            },
            { error ->
                Timber.e("AuthQuickstart $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }
}
