package com.broccoli.bnc.ui.enroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.broccoli.bnc.databinding.FragmentEnrollStartBinding
import com.broccoli.bnc.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnrollStartFragment : Fragment() {
    private var binding: FragmentEnrollStartBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnrollStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRequestAccess.setOnClickListener {
            findNavController().navigate(
                EnrollStartFragmentDirections.toEnrollRequestDialog()
            )
        }
    }
}
