package dev.sagar.assigmenthub

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.StudentAssignmentMapping
import com.amplifyframework.util.GsonFactory
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.ActivityDetailAssignmentBinding
import dev.sagar.assigmenthub.ui.adapter.StudentAssignmentAdapter
import dev.sagar.assigmenthub.ui.viewmodel.DetailAssignmentViewModel
import dev.sagar.assigmenthub.utils.Constants.ASSIGNMENT
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.gone
import dev.sagar.assigmenthub.utils.toast
import dev.sagar.assigmenthub.utils.visible
import timber.log.Timber

@AndroidEntryPoint
class DetailAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAssignmentBinding
    private val viewModel: DetailAssignmentViewModel by viewModels()
    private lateinit var uncompletedStudentsList: MutableList<StudentAssignmentMapping>
    private lateinit var completedStudentsList: MutableList<StudentAssignmentMapping>
    private lateinit var completedStudentsAdapter: StudentAssignmentAdapter
    private lateinit var unCompletedStudentsAdapter: StudentAssignmentAdapter
    private var isChanged = false
    private var completedStudentsShown = true
    private var uncompletedStudentsShown = true
    private var completedStudentSize = 0
    private var uncompletedStudentSize = 0
    private var rotationAngleCompletedStudent: Int = 0
    private var rotationAngleUnCompletedStudent: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assignment = GsonFactory.instance().fromJson<Assignment>(
            intent.getStringExtra(ASSIGNMENT),
            Assignment::class.java
        )

        binding.tvTitle.text = assignment.name
        binding.tvStatus.text = assignment.status.toString()
        binding.tvBranch.text = assignment.branch.toString()
        binding.tvYear.text = assignment.year.toString()
        binding.tvSubject.text = assignment.subject.toString()
        binding.tvDescription.text = assignment.description

        val studentEditable = assignment.status == Status.ONGOING
        if (assignment.status == Status.ONGOING) {
            binding.tvEndAssignment.visible()
        }

        binding.ivBack.setOnClickListener {
            if (isChanged) setResult(Activity.RESULT_OK)
            finish()
        }

        uncompletedStudentsList = viewModel.getUncompletedStudentsList(assignment)
        completedStudentsList = viewModel.getCompletedStudentsList(assignment)

        completedStudentsAdapter =
            StudentAssignmentAdapter(studentEditable, onCompletedStudentClick)
        completedStudentsAdapter.submitList(completedStudentsList)
        unCompletedStudentsAdapter =
            StudentAssignmentAdapter(studentEditable, onUncompletedStudentClick)
        unCompletedStudentsAdapter.submitList(uncompletedStudentsList)

        completedStudentSize = completedStudentsList.size
        uncompletedStudentSize = uncompletedStudentsList.size

        binding.apply {
            tvCompletedStudentsSize.text =
                getString(R.string.students_completed, completedStudentSize.toString())
            tvUnCompletedStudentsSize.text =
                getString(R.string.students_uncompleted, uncompletedStudentSize.toString())

            rvCompletedStudent.adapter = completedStudentsAdapter
            rvCompletedStudent.setHasFixedSize(true)

            rvUnCompletedStudents.adapter = unCompletedStudentsAdapter
            rvUnCompletedStudents.setHasFixedSize(true)

            clUncompletedStudents.setOnClickListener {
                makeArrowAnimateUnCompletedStudent(imageView5)
                uncompletedStudentsShown = !uncompletedStudentsShown
                if (uncompletedStudentsShown) {
                    rvUnCompletedStudents.visible()
                } else {
                    rvUnCompletedStudents.gone()
                }
            }

            clComlpetedStudent.setOnClickListener {
                makeArrowAnimateUpCompletedStudent(imageView6)
                completedStudentsShown = !completedStudentsShown
                if (completedStudentsShown) {
                    rvCompletedStudent.visible()
                } else {
                    rvCompletedStudent.gone()
                }
            }

            tvEndAssignment.setOnClickListener {
                val newAssignment = assignment.copyOfBuilder()
                    .status(Status.ENDED).build()
                viewModel.endAssignment(newAssignment)
            }
        }

        viewModel.studentAssignmentUpdated.observe(
            this,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            isChanged = true
                            Timber.i(result.response)
                        }
                        is ResponseModel.Error -> {
                            toast(result.message)
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )

        viewModel.studentAssignmentUpdated.observe(
            this,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            isChanged = true
                            Timber.i(result.response)
                        }
                        is ResponseModel.Error -> {
                            toast(result.message)
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )

        viewModel.assignmentEnded.observe(
            this,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            setResult(Activity.RESULT_OK)
                            Timber.i(result.response)
                            finish()
                        }
                        is ResponseModel.Error -> {
                            toast(result.message)
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )
    }

    override fun onBackPressed() {
        if (isChanged) setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    private fun makeArrowAnimateUnCompletedStudent(iconArrow: ImageView) {
        rotationAngleCompletedStudent = if (rotationAngleCompletedStudent == 0) 180 else 0
        iconArrow.animate().rotation(rotationAngleCompletedStudent.toFloat()).setDuration(500)
            .start()
    }

    private fun makeArrowAnimateUpCompletedStudent(iconArrow: ImageView) {
        rotationAngleUnCompletedStudent = if (rotationAngleUnCompletedStudent == 0) 180 else 0
        iconArrow.animate().rotation(rotationAngleUnCompletedStudent.toFloat()).setDuration(500)
            .start()
    }

    private val onCompletedStudentClick =
        fun(position: Int, studentAssignmentMapping: StudentAssignmentMapping) {
            completedStudentsList.removeAt(position)
            uncompletedStudentsList.add(studentAssignmentMapping)

            completedStudentsAdapter.notifyDataSetChanged()
            unCompletedStudentsAdapter.notifyDataSetChanged()

            val studentAssignment = studentAssignmentMapping.copyOfBuilder().status(false).build()

            viewModel.updateStudentAssignmentMapping(studentAssignment)

            binding.tvCompletedStudentsSize.text =
                getString(R.string.students_completed, (--completedStudentSize).toString())
            binding.tvUnCompletedStudentsSize.text =
                getString(R.string.students_uncompleted, (++uncompletedStudentSize).toString())
        }

    private val onUncompletedStudentClick =
        fun(position: Int, studentAssignmentMapping: StudentAssignmentMapping) {
            uncompletedStudentsList.removeAt(position)
            completedStudentsList.add(studentAssignmentMapping)

            completedStudentsAdapter.notifyDataSetChanged()
            unCompletedStudentsAdapter.notifyDataSetChanged()

            val studentAssignment = studentAssignmentMapping.copyOfBuilder().status(true).build()

            viewModel.updateStudentAssignmentMapping(studentAssignment)

            binding.tvUnCompletedStudentsSize.text =
                getString(R.string.students_uncompleted, (--uncompletedStudentSize).toString())
            binding.tvCompletedStudentsSize.text =
                getString(R.string.students_completed, (++completedStudentSize).toString())
        }
}
