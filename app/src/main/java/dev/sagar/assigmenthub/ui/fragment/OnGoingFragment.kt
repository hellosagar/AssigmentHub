package dev.sagar.assigmenthub.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.util.GsonFactory
import com.google.gson.reflect.TypeToken
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.FragmentOngoingBinding
import dev.sagar.assigmenthub.DetailAssignmentActivity
import dev.sagar.assigmenthub.ui.adapter.AssignmentAdapter
import dev.sagar.assigmenthub.ui.viewmodel.HomeViewModel
import dev.sagar.assigmenthub.utils.Constants
import dev.sagar.assigmenthub.utils.Constants.ASSIGNMENT
import dev.sagar.assigmenthub.utils.OnDataUpdate
import dev.sagar.assigmenthub.utils.getTeacherInfo
import dev.sagar.assigmenthub.utils.visible
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnGoingFragment : Fragment(R.layout.fragment_ongoing), OnDataUpdate {

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private val binding by viewBinding(FragmentOngoingBinding::bind)
    private lateinit var dataList: MutableList<Assignment>
    private lateinit var adapter: AssignmentAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var teacherID: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token: TypeToken<List<Assignment>> = object : TypeToken<List<Assignment>>() {}
        dataList = GsonFactory.instance()
            .fromJson(arguments?.getString(Constants.ON_GOING_ASSIGNMENTS), token.type)

        lifecycleScope.launch {
            teacherID = getTeacherInfo(dataStore, Constants.TEACHER_ID)
        }

        if (!dataList.isNullOrEmpty()) {
            adapter = AssignmentAdapter(onAssignmentClick)
            sortListByDate()
            adapter.submitList(dataList)
            binding.rvOnGoingAssignment.adapter = adapter
            binding.rvOnGoingAssignment.setHasFixedSize(true)
        } else {
            binding.tvNoData.visible()
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.getAssignments(teacherID)
                }
            }
    }

    private val onAssignmentClick = fun(assignment: Assignment) {
        val intent = Intent(requireContext(), DetailAssignmentActivity::class.java)
        intent.putExtra(ASSIGNMENT, GsonFactory.instance().toJson(assignment))
        resultLauncher.launch(intent)
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

    override fun onDataUpdate(data: List<Assignment>) {
        dataList.clear()
        dataList.addAll(data)
        sortListByDate()
        adapter.submitList(dataList)
    }

    private fun sortListByDate() {
        dataList.sortBy {
            it.lastDateSubmission.toDate().time
        }
        dataList.reverse()
    }
}
