package dev.sagar.assigmenthub.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.amplifyframework.datastore.generated.model.Assignment
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.FragmentHomeBinding
import dev.sagar.assigmenthub.ui.adapter.HomePagerAdapter
import dev.sagar.assigmenthub.ui.viewmodel.HomeViewModel
import dev.sagar.assigmenthub.utils.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            viewModel.getAssignments(getTeacherInfo(dataStore, Constants.TEACHER_EMAIL))

            viewModel.getAssignments.observe(
                viewLifecycleOwner,
                Observer {
                    it.getContentIfNotHandled()?.let { result ->
                        when (result) {
                            is ResponseModel.Loading -> {
                                Timber.i("Loading")
                            }
                            is ResponseModel.Success -> {
                                initView(result.response)
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
    }

    private fun initView(result: List<Assignment>) {

        val viewpager = binding.viewpager

        viewpager.adapter = HomePagerAdapter(
            requireActivity(),
            viewModel.getOnGoingAssignments(result),
            viewModel.getEndedAssignments(result)
        )

        TabLayoutMediator(binding.tableLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Ongoing"
                }
                1 -> {
                    tab.text = "Ended"
                }
            }
        }.attach()
        Timber.i(result.toString())
    }
}
