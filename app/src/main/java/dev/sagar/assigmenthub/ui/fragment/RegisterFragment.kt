package dev.sagar.assigmenthub.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.FragmentRegisterBinding
import dev.sagar.assigmenthub.ui.viewmodel.RegisterViewModel
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.toast
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        var email = ""
        var password = ""
        var name = ""
        binding.btnSignUp.setOnClickListener {
            email = binding.tietEmail.text.toString()
            name = binding.tietName.text.toString()
            password = binding.tietPassword.text.toString()
            val confirmPassword = binding.tietConfirmPassword.text.toString()

            viewModel.createUser(email, name, password, confirmPassword)
        }

        viewModel.createTeacher.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            findNavController().navigate(
                                RegisterFragmentDirections.actionRegisterFragmentToVerifyOtpFragment(
                                    email,
                                    password,
                                    name
                                )
                            )
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
    }
}
