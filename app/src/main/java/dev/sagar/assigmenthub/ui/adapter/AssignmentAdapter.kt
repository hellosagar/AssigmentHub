package dev.sagar.assigmenthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Assignment
import dev.hellosagar.assigmenthub.databinding.ItemAssignmentBinding
import java.util.Locale

class AssignmentAdapter(
    private val assignments: List<Assignment>,
    private val onAssignmentClick: (Assignment) -> Unit
) : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val binding = ItemAssignmentBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        return AssignmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val currentItem: Assignment = assignments[position]
        currentItem.let {
            holder.bind(it, onAssignmentClick)
        }
    }

    override fun getItemCount(): Int {
        return assignments.size
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
