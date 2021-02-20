package dev.sagar.assigmenthub

import android.app.Application
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.hellosagar.assigmenthub.BuildConfig
import dagger.hilt.android.HiltAndroidApp
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
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Timber.i("MyAmplifyApp Initialized Amplify")
        } catch (error: AmplifyException) {
            Timber.e("MyAmplifyApp Could not initialize Amplify $error")
        }
    }
}
