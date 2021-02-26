package dev.sagar.assigmenthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Assignment
import dev.hellosagar.assigmenthub.databinding.ItemAssignmentBinding
import java.util.Locale

class AssignmentAdapter(
    private val onAssignmentClick: (Assignment) -> Unit
) : ListAdapter<Assignment, AssignmentAdapter.AssignmentViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Assignment>() {
            override fun areItemsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val binding =
            ItemAssignmentBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        return AssignmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val currentItem: Assignment = getItem(position)
        currentItem.let {
            holder.bind(it, onAssignmentClick)
        }
    }

    class AssignmentViewHolder(private val binding: ItemAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            assignment: Assignment,
            onAssignmentClick: (Assignment) -> Unit
        ) {
            binding.apply {
                tvAssignmentTitle.text = assignment.name
                tvAssignmentDescription.text = assignment.description

                tvSubjectName.text = assignment.subject.substring(0, 1)
                    .toUpperCase(Locale.getDefault())

                clAssignment.setOnClickListener {
                    onAssignmentClick.invoke(assignment)
                }
            }
        }
    }
}
