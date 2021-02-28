package dev.sagar.assigmenthub.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.FragmentLoginBinding
import dev.hellosagar.assigmenthub.databinding.ProgressButtonLayoutBinding
import dev.sagar.assigmenthub.HomeActivity
import dev.sagar.assigmenthub.ui.viewmodel.LoginViewModel
import dev.sagar.assigmenthub.utils.ProgressButton
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.toast
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var loginView: ProgressButtonLayoutBinding
    private lateinit var progressButton: ProgressButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginView = binding.btnLogin
        progressButton = ProgressButton(requireContext(), loginView, getString(R.string.login))

        loginView.cvProgressButton.setOnClickListener {
            val email = binding.tietEmail.text.toString()
            val password = binding.tietPassword.text.toString()

            viewModel.loginUser(email, password)
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        binding.clParentLogin.setOnClickListener {
            val imm: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        viewModel.loginUser.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                            progressButton.btnActivated(getString(R.string.please_wait))
                        }
                        is ResponseModel.Success -> {
                            progressButton.btnFinished(getString(R.string.done))
                            Timber.i(result.response)
                            requireActivity().startActivity(
                                Intent(
                                    requireActivity(),
                                    HomeActivity::class.java
                                )
                            )
                            requireActivity().finish()
                        }
                        is ResponseModel.Error -> {
                            toast(result.message)
                            Timber.i(result.error)
                            progressButton.btnReset()
                        }
                    }
                }
            }
        )
    }
}
