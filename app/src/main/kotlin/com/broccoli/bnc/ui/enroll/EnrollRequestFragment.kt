package com.broccoli.bnc.ui.enroll

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.broccoli.bnc.MobileNavigationDirections
import com.broccoli.bnc.R
import com.broccoli.bnc.data.common.onError
import com.broccoli.bnc.data.common.onLoading
import com.broccoli.bnc.data.common.onSuccess
import com.broccoli.bnc.data.request.EnrollmentRequest
import com.broccoli.bnc.databinding.FragmentEnrollRequestBinding
import com.broccoli.bnc.util.Constants
import com.broccoli.bnc.util.autoCleared
import com.broccoli.bnc.util.getError
import com.broccoli.bnc.util.isValidEmail
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnrollRequestFragment : DialogFragment() {
    private val viewModel: EnrollmentViewModel by viewModels()
    private var binding: FragmentEnrollRequestBinding by autoCleared()

    @Inject
    lateinit var preferences: SharedPreferences

    // To achieve https://m3.material.io/components/dialogs/specs#bbf1acde-f8d2-4ae1-9d51-343e96c4ac20
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnrollRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            dialog?.dismiss()
        }
        binding.etName.doAfterTextChanged {
            isNameValid()
        }
        binding.etEmail.doAfterTextChanged {
            isEmailValid()
        }
        binding.etConfirmEmail.doAfterTextChanged {
            isConfirmEmailValid()
        }
        binding.btnSendInvite.setOnClickListener {
            if (isNameValid() && isEmailValid() && isConfirmEmailValid()) {
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val body = EnrollmentRequest(name, email)
                viewModel.doEnroll(body)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resultEnrollment.collect { response ->
                    response.onLoading {
                        binding.btnSendInvite.isVisible = false
                        binding.progressBar.isVisible = true
                    }.onSuccess {
                        preferences.edit().putBoolean(Constants.DID_REQUEST_ACCESS, true).apply()
                        findNavController().navigate(
                            MobileNavigationDirections.toEnrollCompleteFragment()
                        )
                    }.onError {
                        binding.btnSendInvite.isVisible = true
                        binding.progressBar.isVisible = false
                        Snackbar.make(
                            binding.root,
                            it.getError(requireContext()),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun isNameValid(): Boolean {
        binding.apply {
            return if (etName.length() > 3) {
                tilName.error = null
                true
            } else {
                tilName.error = getString(R.string.error_invalid_full_name)
                false
            }
        }
    }

    private fun isEmailValid(): Boolean {
        binding.apply {
            return if (etEmail.text.isValidEmail()) {
                tilEmail.error = null
                true
            } else {
                tilEmail.error = getString(R.string.error_invalid_email)
                false
            }
        }
    }

    private fun isConfirmEmailValid(): Boolean {
        binding.apply {
            return if (etConfirmEmail.text.isValidEmail() &&
                etConfirmEmail.text.contentEquals(etEmail.text)
            ) {
                tilConfirmEmail.error = null
                true
            } else {
                tilConfirmEmail.error = getString(R.string.error_email_mismatch)
                false
            }
        }
    }
}
