package dev.sagar.assigmenthub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.util.GsonFactory
import com.google.gson.reflect.TypeToken
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.FragmentEndedBinding
import dev.sagar.assigmenthub.DetailAssignmentActivity
import dev.sagar.assigmenthub.ui.adapter.AssignmentAdapter
import dev.sagar.assigmenthub.utils.Constants
import dev.sagar.assigmenthub.utils.visible

@AndroidEntryPoint
class EndedFragment : Fragment(R.layout.fragment_ended) {

    private val binding by viewBinding(FragmentEndedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token: TypeToken<List<Assignment>> = object : TypeToken<List<Assignment>>() {}
        val data: List<Assignment> = GsonFactory.instance()
            .fromJson(arguments?.getString(Constants.ENDED_ASSIGNMENTS), token.type)

        if (!data.isNullOrEmpty()) {
            val adapter = AssignmentAdapter(data, onAssignmentClick)
            binding.rvEndedAssignment.setHasFixedSize(true)
            binding.rvEndedAssignment.adapter = adapter
        } else {
            binding.tvNoData.visible()
        }
    }

    private val onAssignmentClick = fun(assignment: Assignment) {
        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
        intent.putExtra(Constants.ASSIGNMENT, GsonFactory.instance().toJson(assignment))
        startActivity(intent)
    }

    companion object {
        fun newInstance(data: String): EndedFragment {
            val bundle = Bundle()
            bundle.putString(Constants.ENDED_ASSIGNMENTS, data)
            val fragment = EndedFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
