package dev.sagar.assigmenthub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }
    private var clicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationViewHome
        val navController = findNavController(R.id.navFragmentHome)

        bottomNavigationView.setupWithNavController(navController)

        binding.floatingActionButton.setOnClickListener {
            onAddButtonClicked()
        }

        binding.clBg.setOnClickListener {
            onAddButtonClicked()
        }
        binding.clBg.isClickable = false

        binding.fabAddStudent.setOnClickListener {
            onAddButtonClicked()
            startActivity(
                Intent(this, AddStudentActivity::class.java)
            )
        }

        binding.fabAddAssignment.setOnClickListener {
            onAddButtonClicked()
            startActivity(
                Intent(this, AddAssignmentActivity::class.java)
            )
        }
    }

    override fun onBackPressed() {
        if (!clicked) {
            super.onBackPressed()
        } else {
            onAddButtonClicked()
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        setBackground(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabAddAssignment.visibility = View.VISIBLE
            binding.fabAddStudent.visibility = View.VISIBLE
        } else {
            binding.fabAddAssignment.visibility = View.INVISIBLE
            binding.fabAddStudent.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.floatingActionButton.startAnimation(rotateOpen)
            binding.fabAddAssignment.startAnimation(fromBottom)
            binding.fabAddStudent.startAnimation(fromBottom)
        } else {
            binding.floatingActionButton.startAnimation(rotateClose)
            binding.fabAddAssignment.startAnimation(toBottom)
            binding.fabAddStudent.startAnimation(toBottom)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabAddAssignment.isClickable = true
            binding.fabAddStudent.isClickable = true
        } else {
            binding.fabAddAssignment.isClickable = false
            binding.fabAddStudent.isClickable = false
        }
    }

    private fun setBackground(clicked: Boolean) {
        if (!clicked) {
            binding.clBg.isClickable = true
            binding.clBg.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        } else {
            binding.clBg.isClickable = false
            binding.clBg.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        }
    }
}
