package com.example.budget.view.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.budget.R
import com.example.budget.databinding.BottomNavigationLayoutBinding
import com.example.budget.view.fragments.ExpenseItemsFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    companion object {
        fun newInstance(): BottomNavigationDrawerFragment {
            return BottomNavigationDrawerFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.navigation_backup -> Toast.makeText(requireContext(), "navigation_backup", Toast.LENGTH_SHORT).show()
                R.id.navigation_export_to_excel -> Toast.makeText(requireContext(), "navigation_export_to_excel", Toast.LENGTH_SHORT).show()
                R.id.navigation_expense_items -> navigateTo(ExpenseItemsFragment())
                R.id.navigation_bills_list -> Toast.makeText(requireContext(), "navigation_bills_list", Toast.LENGTH_SHORT).show()
            }
            dismiss()
            true
        }
    }

    private fun navigateTo(fragment: BottomSheetDialogFragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.main_container, fragment).addToBackStack("").commit()
    }

}