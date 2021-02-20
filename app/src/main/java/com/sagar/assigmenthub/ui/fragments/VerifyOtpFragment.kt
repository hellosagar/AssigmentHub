package com.sagar.assigmenthub.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sagar.assigmenthub.R
import com.sagar.assigmenthub.databinding.FragmentVerifyOtpBinding
import com.sagar.assigmenthub.other.ResponseModel
import com.sagar.assigmenthub.other.toast
import com.sagar.assigmenthub.ui.viewmodels.VerifyOtpViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class VerifyOtpFragment : Fragment(R.layout.fragment_verify_otp) {

    private val binding by viewBinding(FragmentVerifyOtpBinding::bind)
    private val args by navArgs<VerifyOtpFragmentArgs>()
    private val viewModel: VerifyOtpViewModel by viewModels()
    private lateinit var email: String
    private lateinit var password: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnVerify.setOnClickListener {

            val otp = binding.tietOtp.text.toString()
            email = args.email
            password = args.password
            viewModel.verifyOtp(email, otp)
        }

        viewModel.verifyUser.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            viewModel.loginUser(email, password)
                        }
                        is ResponseModel.Error -> {
                            toast(result.message)
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )

        viewModel.loginUser.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is ResponseModel.Loading -> {
                            Timber.i("Loading")
                        }
                        is ResponseModel.Success -> {
                            Timber.i(result.response)
                            findNavController().navigate(
                                VerifyOtpFragmentDirections.actionVerifyOtpFragmentToHomeFragment()
                            )
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
