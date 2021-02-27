package dev.sagar.assigmenthub.data.repositories

import com.amplifyframework.auth.AuthCategory
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import dev.sagar.assigmenthub.utils.ResponseModel
import timber.log.Timber
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val auth: AuthCategory
) {

    fun signUpTeacher(
        email: String,
        name: String,
        password: String,
        callback: (ResponseModel<String>) -> Unit
    ) {

        val attributes: ArrayList<AuthUserAttribute> = ArrayList()
        attributes.add(AuthUserAttribute(AuthUserAttributeKey.email(), email))
        attributes.add(AuthUserAttribute(AuthUserAttributeKey.name(), name))

        auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().userAttributes(attributes).build(),
            { result ->
                Timber.i("signUpTeacher result: $result")
                callback(ResponseModel.Success("Teacher signUp"))
            },
            { error ->
                Timber.e("signUpTeacher error $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun signInTeacher(
        email: String,
        password: String,
        callback: (ResponseModel<String>) -> Unit
    ) {

        auth.signIn(
            email,
            password,
            { result ->
                Timber.i("signInTeacher $result")
                callback(ResponseModel.Success("Teacher signIn $result"))
            },
            { error ->
                Timber.e("signInTeacher $error) ")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun confirmTeacher(
        email: String,
        otp: String,
        callback: (ResponseModel<String>) -> Unit
    ) {

        auth.confirmSignUp(
            email,
            otp,
            { result ->
                Timber.i("confirmTeacher $result")
                callback(ResponseModel.Success("Teacher confirmed!"))
            },
            { error ->
                Timber.e("confirmTeacher $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun signOutTeacher(callback: (ResponseModel<String>) -> Unit) {
        auth.signOut(
            {
                Timber.i("signOutTeacher success")
                callback(ResponseModel.Success("signOutTeacher success"))
            },
            { error ->
                Timber.e("signOutTeacher $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }
}
