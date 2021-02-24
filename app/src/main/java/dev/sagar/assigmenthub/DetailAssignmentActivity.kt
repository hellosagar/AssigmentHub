package dev.sagar.assigmenthub

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.util.GsonFactory
import dev.hellosagar.assigmenthub.databinding.ActivityDetailAssignmentBinding
import dev.sagar.assigmenthub.ui.viewmodel.DetailAssignmentViewModel
import dev.sagar.assigmenthub.utils.Constants.ASSIGNMENT

class DetailAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAssignmentBinding
    private val viewModel: DetailAssignmentViewModel by viewModels()

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
        binding.tvYear.text = assignment.year.toString()
        binding.tvSubject.text = assignment.subject.toString()
        binding.tvDescription.text = assignment.description

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
