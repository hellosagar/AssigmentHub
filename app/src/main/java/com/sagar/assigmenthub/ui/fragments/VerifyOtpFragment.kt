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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnVerify.setOnClickListener {

            val otp = binding.tietOtp.text.toString()
            val email = args.email
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

                            findNavController().navigate(
                                VerifyOtpFragmentDirections.actionVerifyOtpFragmentToHomeFragment()
                            )

                            Timber.i(result.response)
                        }
                        is ResponseModel.Error -> {
                            toast(result.error.toString())
                            Timber.i(result.error)
                        }
                    }
                }
            }
        )
    }
}
