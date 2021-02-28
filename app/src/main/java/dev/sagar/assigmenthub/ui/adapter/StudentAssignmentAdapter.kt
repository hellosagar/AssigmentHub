package dev.sagar.assigmenthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.StudentAssignmentMapping
import dev.hellosagar.assigmenthub.databinding.ItemStudentAssignmentBinding
import dev.sagar.assigmenthub.utils.getShortName
import java.util.Locale

class StudentAssignmentAdapter(
    private val studentEditable: Boolean,
    private val onClick: (Int, StudentAssignmentMapping) -> Unit
) : ListAdapter<StudentAssignmentMapping, StudentAssignmentAdapter.StudentAssignmentViewHolder>(
    differCallback
) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<StudentAssignmentMapping>() {
            override fun areItemsTheSame(
                oldItem: StudentAssignmentMapping,
                newItem: StudentAssignmentMapping
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StudentAssignmentMapping,
                newItem: StudentAssignmentMapping
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAssignmentViewHolder {
        val binding = ItemStudentAssignmentBinding.inflate(
            (LayoutInflater.from(parent.context)),
            parent,
            false
        )
        return StudentAssignmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentAssignmentViewHolder, position: Int) {
        val currentItem: StudentAssignmentMapping = getItem(position)
        currentItem.let {
            holder.bind(studentEditable, it, onClick)
        }
    }

    class StudentAssignmentViewHolder(private val binding: ItemStudentAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            studentEditable: Boolean,
            studentAssignmentMapping: StudentAssignmentMapping,
            onClick: (Int, StudentAssignmentMapping) -> Unit
        ) = with(binding) {
            if (studentAssignmentMapping.student != null) {
                tvStudentName.text = studentAssignmentMapping.student.name
                tvRollNo.text = "Roll No. " + studentAssignmentMapping.student.rollNo.toString()
                tvShortName.text = studentAssignmentMapping.student.name.getShortName()
                    .toUpperCase(Locale.getDefault())
            }
            if (studentEditable) {
                clStudentAssignment.setOnClickListener {
                    onClick.invoke(adapterPosition, studentAssignmentMapping)
                }
            }
        }
    }
}
