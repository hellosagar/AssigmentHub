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
import dev.hellosagar.assigmenthub.databinding.ProgressButtonLayoutBinding
import dev.sagar.assigmenthub.ui.viewmodel.RegisterViewModel
import dev.sagar.assigmenthub.utils.ProgressButton
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.toast
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var signUpView: ProgressButtonLayoutBinding
    private lateinit var progressButton: ProgressButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        signUpView = binding.btnSignUp
        progressButton = ProgressButton(requireContext(), signUpView, getString(R.string.signup))

        var email = ""
        var password = ""
        var name = ""
        signUpView.cvProgressButton.setOnClickListener {
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
                            progressButton.btnActivated(getString(R.string.please_wait))
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            progressButton.btnFinished(getString(R.string.done))
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
                            progressButton.btnReset()
                            toast(result.message)
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )
    }
}
