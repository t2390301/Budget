package com.example.budget.view.fragments.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.budget.databinding.FragmentSmsBinding
import com.example.budget.model.utils.AppLogger
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SMSFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSmsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppLogger.i("onViewCreated SMSFragment")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressedDispatcher
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}