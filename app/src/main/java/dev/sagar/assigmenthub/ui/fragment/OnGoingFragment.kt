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
import dev.hellosagar.assigmenthub.databinding.FragmentOngoingBinding
import dev.sagar.assigmenthub.DetailAssignmentActivity
import dev.sagar.assigmenthub.ui.adapter.AssignmentAdapter
import dev.sagar.assigmenthub.utils.Constants
import dev.sagar.assigmenthub.utils.Constants.ASSIGNMENT
import dev.sagar.assigmenthub.utils.visible

@AndroidEntryPoint
class OnGoingFragment : Fragment(R.layout.fragment_ongoing) {

    private val binding by viewBinding(FragmentOngoingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token: TypeToken<List<Assignment>> = object : TypeToken<List<Assignment>>() {}
        val data: List<Assignment> = GsonFactory.instance()
            .fromJson(arguments?.getString(Constants.ON_GOING_ASSIGNMENTS), token.type)

        if (!data.isNullOrEmpty()) {
            val adapter = AssignmentAdapter(data, onAssignmentClick)
            binding.rvOnGoingAssignment.setHasFixedSize(true)
            binding.rvOnGoingAssignment.adapter = adapter
        } else {
            binding.tvNoData.visible()
        }
    }

    private val onAssignmentClick = fun(assignment: Assignment) {
        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
        intent.putExtra(ASSIGNMENT, GsonFactory.instance().toJson(assignment))
        startActivity(intent)
    }

    companion object {
        fun newInstance(data: String): OnGoingFragment {
            val bundle = Bundle()
            bundle.putString(Constants.ON_GOING_ASSIGNMENTS, data)
            val fragment = OnGoingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
