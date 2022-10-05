package dev.sagar.assigmenthub.data.repositories

import com.amplifyframework.auth.AuthCategory
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import dev.sagar.assigmenthub.utils.ResponseModel
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepo @Inject constructor(
    private val auth: AuthCategory
) {

    suspend fun signUpTeacher(
        email: String,
        name: String,
        password: String
    ) = suspendCoroutine<ResponseModel<String>> {
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.email(), email),
            AuthUserAttribute(AuthUserAttributeKey.name(), name)
        )
        auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().userAttributes(attributes).build(),
            { result ->
                Timber.i("signUpTeacher result: $result")
                it.resume(ResponseModel.Success("Teacher signUp"))
            },
            { error ->
                Timber.e("signUpTeacher error $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun signInTeacher(
        email: String,
        password: String
    ) = suspendCoroutine<ResponseModel<String>> {
        auth.signIn(
            email,
            password,
            { result ->
                Timber.i("signInTeacher $result")
                it.resume(ResponseModel.Success("Teacher signIn $result"))
            },
            { error ->
                Timber.e("signInTeacher $error) ")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun confirmTeacher(
        email: String,
        otp: String
    ) = suspendCoroutine<ResponseModel<String>> {
        auth.confirmSignUp(
            email,
            otp,
            { result ->
                Timber.i("confirmTeacher $result")
                it.resume(ResponseModel.Success("Teacher confirmed!"))
            },
            { error ->
                Timber.e("confirmTeacher $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun signOutTeacher() = suspendCoroutine<ResponseModel<String>> {
        auth.signOut(
            {
                Timber.i("signOutTeacher success")
                it.resume(ResponseModel.Success("signOutTeacher success"))
            },
            { error ->
                Timber.e("signOutTeacher $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }
}
