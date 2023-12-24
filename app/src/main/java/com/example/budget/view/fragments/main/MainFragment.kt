package com.example.budget.view.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.budget.R
import com.example.budget.databinding.FragmentMainBinding
import com.example.budget.model.domain.BudgetEntryTable
import com.example.budget.view.activities.MainActivity
import com.example.budget.viewmodel.MainFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber


class MainFragment : Fragment() {
    companion object {
        const val TAG = "!!! MainFragment"
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    var listAdapter: MainFragmentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MainFragmentViewModel by viewModels()

        val budgetEntryList: List<BudgetEntryTable>? = viewModel.budgetEntityTableList.value
        Timber.tag(TAG).i("budgetEntryList = $budgetEntryList")

        val listAdapter = MainFragmentAdapter(budgetEntryList)
        Timber.tag(TAG).i("listAdapter = $listAdapter")

        binding.mainRecyclerView.adapter = listAdapter

        viewModel.budgetEntityTableList.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
            Timber.tag(TAG).i("viewModel._budgetEntityTableList   listAdapter = $listAdapter")
        }

        bottomSheetInitialization()
    }


    private fun bottomSheetInitialization(){
        (requireActivity() as MainActivity).setSupportActionBar(binding.mainRecyclerBottomAppbar)
        setHasOptionsMenu(true)

        val behavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        var isMain = true
        binding.mainRecyclerFab.setOnClickListener {
            if (isMain) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.mainRecyclerBottomAppbar.menu.clear()
                binding.mainRecyclerBottomAppbar.navigationIcon = null
                binding.mainRecyclerFab.setImageResource(R.drawable.ic_back_fab)
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.mainRecyclerBottomAppbar.menu.hasVisibleItems()
                binding.mainRecyclerBottomAppbar.replaceMenu(R.menu.menu_bottom_main)
                binding.mainRecyclerBottomAppbar.navigationIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_main_menu_bottom_bar)
                binding.mainRecyclerFab.setImageResource(R.drawable.ic_plus_fab)
            }
            isMain = !isMain
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(
                    requireActivity().supportFragmentManager,
                    "ff"
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_main, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}



