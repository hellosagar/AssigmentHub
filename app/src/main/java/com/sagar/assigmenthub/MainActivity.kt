package com.sagar.assigmenthub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.amplifyframework.core.Amplify
import com.sagar.assigmenthub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController

        if (Amplify.Auth.currentUser != null) {
            navGraph.startDestination = R.id.homeFragment
            navController.graph = navGraph
            Timber.i("AmplifyQuickstart)")
        } else {
            navGraph.startDestination = R.id.loginFragment
            navController.graph = navGraph
            Timber.e("AmplifyQuickstart")
        }

//        Amplify.Auth.fetchAuthSession(
//            { result ->
//                runOnUiThread {
//                    if (result.isSignedIn) {
//                        navGraph.startDestination = R.id.homeFragment
//                    } else {
//                        navGraph.startDestination = R.id.loginFragment
//                    }
//                    navController.graph = navGraph
//                    Timber.i("AmplifyQuickstart $result)")
//                }
//            },
//            { error ->
//                runOnUiThread {
//                    navGraph.startDestination = R.id.loginFragment
//                    navController.graph = navGraph
//                    Timber.e("AmplifyQuickstart $error")
//                }
//            }
//        )
    }
}
