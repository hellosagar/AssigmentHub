package dev.sagar.assigmenthub

import android.app.Application
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import dagger.hilt.android.HiltAndroidApp
import dev.hellosagar.assigmenthub.BuildConfig
import dev.sagar.assigmenthub.utils.ReleaseTree
import timber.log.Timber

@HiltAndroidApp
class AssignmentHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Timber.i("MyAmplifyApp Initialized Amplify")
        } catch (error: AmplifyException) {
            Timber.e("MyAmplifyApp Could not initialize Amplify $error")
        }
    }
}
