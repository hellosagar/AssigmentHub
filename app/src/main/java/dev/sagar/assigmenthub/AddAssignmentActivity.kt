package dev.sagar.assigmenthub

import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amplifyframework.datastore.generated.model.Branch
import com.amplifyframework.datastore.generated.model.Year
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.ActivityAddAssignmentBinding
import dev.hellosagar.assigmenthub.databinding.ProgressButtonLayoutBinding
import dev.sagar.assigmenthub.ui.viewmodel.AddAssignmentViewModel
import dev.sagar.assigmenthub.utils.ProgressButton
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.getFormattedDate
import dev.sagar.assigmenthub.utils.toast
import timber.log.Timber
import java.util.Calendar
import java.util.TimeZone

@AndroidEntryPoint
class AddAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssignmentBinding
    private val viewModel: AddAssignmentViewModel by viewModels()
    private lateinit var progressView: ProgressButtonLayoutBinding
    private lateinit var progressButton: ProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressView = binding.btnSubmit
        progressButton = ProgressButton(this, progressView, getString(R.string.submit))
        binding.tietLastSubmissionDate.setTextIsSelectable(false)

        initBranchDropdown()
        initYearDropdown()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tietLastSubmissionDate.setOnClickListener {
            initDatePicker()
        }

        binding.tietLastSubmissionDate.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    initDatePicker()
                }
            }

        progressView.cvProgressButton.setOnClickListener {
            val name = binding.tietName.text.toString()
            val subject = binding.tietSubject.text.toString()
            val branch = binding.actBranch.text.toString()
            val year = binding.actYear.text.toString()
            val date = binding.tietLastSubmissionDate.text.toString()
            val description = binding.tietDescription.text.toString()

            viewModel.createAssignment(name, subject, branch, year, date, description)
        }

        viewModel.createAssignment.observe(
            this,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                            progressButton.btnActivated(getString(R.string.please_wait))
                        }
                        is ResponseModel.Success -> {
                            toast("Assignment Added!")
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

    private fun initDatePicker() {

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.clear()

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val constraintBuilder: CalendarConstraints.Builder = CalendarConstraints.Builder()
        constraintBuilder.setStart(today)
        constraintBuilder.setValidator(object : CalendarConstraints.DateValidator {
            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(dest: Parcel?, flags: Int) {
            }

            override fun isValid(date: Long): Boolean {
                return date > today
            }
        })

        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setSelection(today)
        builder.setCalendarConstraints(constraintBuilder.build())

        val picker = builder.build()
        picker.show(supportFragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener {
            binding.tietLastSubmissionDate.setText(it.getFormattedDate())
        }
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
