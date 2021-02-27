package dev.sagar.assigmenthub

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amplifyframework.datastore.generated.model.Branch
import com.amplifyframework.datastore.generated.model.Year
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.ActivityAddStudentBinding
import dev.hellosagar.assigmenthub.databinding.ProgressButtonLayoutBinding
import dev.sagar.assigmenthub.ui.viewmodel.AddStudentViewModel
import dev.sagar.assigmenthub.utils.ProgressButton
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.toast
import timber.log.Timber

@AndroidEntryPoint
class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private val viewModel: AddStudentViewModel by viewModels()
    private lateinit var progressView: ProgressButtonLayoutBinding
    private lateinit var progressButton: ProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressView = binding.btnSubmit
        progressButton = ProgressButton(this, progressView, getString(R.string.submit))

        initBranchDropdown()
        initYearDropdown()

        binding.ivBack.setOnClickListener {
            finish()
        }

        progressView.cvProgressButton.setOnClickListener {
            val name = binding.tietName.text.toString()
            val rollNo = binding.tietRollNo.text.toString()
            val branch = binding.actBranch.text.toString()
            val year = binding.actYear.text.toString()
            val email = binding.tietEmail.text.toString()

            viewModel.createStudent(name, rollNo, branch, year, email)
        }

        viewModel.createStudent.observe(
            this,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                            progressButton.btnActivated(getString(R.string.please_wait))
                        }
                        is ResponseModel.Success -> {
                            toast("Student Added!")
                            Timber.i(result.response)
                            progressButton.btnFinished(getString(R.string.done))
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                        is ResponseModel.Error -> {
                            toast(result.message)
                            Timber.i(result.error)
                            progressButton.btnReset()
                        }
                    }
                }
            }
        )
    }

    private fun initBranchDropdown() {
        val items = Branch.values().toList()
        val adapter = ArrayAdapter(this, R.layout.item_dropdown, items)
        (binding.textInputLayoutBranch.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initYearDropdown() {
        val items = Year.values().toList()
        val adapter = ArrayAdapter(this, R.layout.item_dropdown, items)
        (binding.textInputLayoutYear.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}
