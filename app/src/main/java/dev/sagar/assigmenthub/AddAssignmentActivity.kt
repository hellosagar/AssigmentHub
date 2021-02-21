package dev.sagar.assigmenthub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.hellosagar.assigmenthub.databinding.ActivityAddAssignmentBinding

class AddAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssignmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
