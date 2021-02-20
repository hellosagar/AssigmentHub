package dev.sagar.assigmenthub.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
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
        binding.btnSignUp.setOnClickListener {
            email = binding.tietEmail.text.toString()
            val name = binding.tietName.text.toString()
            password = binding.tietPassword.text.toString()
            val confirmPassword = binding.tietConfirmPassword.text.toString()

            viewModel.createUser(email, name, password, confirmPassword)
        }

        viewModel.createUser.observe(
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
                                    password
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

    fun signOut() {
        Amplify.Auth.signOut(
            { Timber.i("AuthQuickstart Signed out successfully") },
            { error -> Timber.e("AuthQuickstart $error") }
        )
    }
}

/*
        Amplify.Auth.fetchAuthSession(
            { result -> Timber.i("AmplifyQuickstart $result") },
            { error -> Timber.e("AmplifyQuickstart $error") }
        )
         */
