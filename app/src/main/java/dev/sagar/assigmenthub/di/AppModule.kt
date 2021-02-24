package dev.sagar.assigmenthub.di

import android.app.Application
import androidx.datastore.preferences.createDataStore
import com.amplifyframework.core.Amplify
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.sagar.assigmenthub.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAuth() = Amplify.Auth

    @Provides
    @Singleton
    fun providesApi() = Amplify.API

    @Provides
    @Singleton
    fun providesDataStore(application: Application) =
        application.createDataStore(name = Constants.SETTINGS)
}
