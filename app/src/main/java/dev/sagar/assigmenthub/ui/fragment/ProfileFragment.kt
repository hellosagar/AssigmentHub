package dev.sagar.assigmenthub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.FragmentProfileBinding
import dev.sagar.assigmenthub.AuthActivity
import dev.sagar.assigmenthub.ui.viewmodel.ProfileViewModel
import dev.sagar.assigmenthub.utils.Constants
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.getTeacherInfo
import dev.sagar.assigmenthub.utils.toast
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val name = getTeacherInfo(dataStore, Constants.TEACHER_NAME)
            val email = getTeacherInfo(dataStore, Constants.TEACHER_EMAIL)

            binding.tvName.text = name
            binding.tvEmail.text = email
        }

        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
        }

        viewModel.signOutUser.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            signOut()
                        }
                        is ResponseModel.Error -> {
                            signOut()
                            toast(result.message)
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )
    }

    private fun signOut() {
        lifecycleScope.launch {
            dataStore.edit { dataStoreEdit ->
                dataStoreEdit.clear()
            }
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }
}
