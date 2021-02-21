package dev.sagar.assigmenthub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.ActivityAuthBinding
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Amplify.Auth.currentUser != null) {
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
            finish()
            Timber.i("AmplifyQuickstart)")
        }
    }
}
