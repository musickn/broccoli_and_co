package com.broccoli.bnc.ui.enroll

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.broccoli.bnc.R
import com.broccoli.bnc.databinding.FragmentEnrollCompleteBinding
import com.broccoli.bnc.util.autoCleared
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnrollCompleteFragment : Fragment() {
    private var binding: FragmentEnrollCompleteBinding by autoCleared()

    @Inject
    lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnrollCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancelAccess.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.sure_cancel_the_invite))
                .setPositiveButton(R.string.yes) { _, _ ->
                    preferences.edit().clear().apply()
                    findNavController().navigate(
                        EnrollCompleteFragmentDirections.toEnrollStartFragment()
                    )
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }
}
