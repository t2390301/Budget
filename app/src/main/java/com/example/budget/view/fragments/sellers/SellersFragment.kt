package com.example.budget.view.fragments.sellers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.budget.R
import com.example.budget.databinding.FragmentSellersBinding
import com.example.budget.model.domain.Seller
import com.example.budget.viewmodel.AppState
import com.example.budget.viewmodel.SellerFragmentViewMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SellersFragment : BottomSheetDialogFragment(){

    private var _binding: FragmentSellersBinding? = null

    val binding: FragmentSellersBinding
        get() = _binding!!

    val viewModel: SellerFragmentViewMode by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSellersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sellersList = mutableListOf<Seller>()

        val sellersAdapter = SellerFragmentAdapter(sellersList){
            viewModel.updateSeller(it)
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.main_container, SellerDetailFragment())
                .addToBackStack("")
                .commit()

        }

        binding.sellersRecyclerView.adapter = sellersAdapter

        viewModel.sellersViewModel.observe(viewLifecycleOwner){appState ->
            if (appState != null && appState is AppState.Success){
                appState.data?.let {
                sellersList = it
                sellersAdapter.setData(sellersList)}
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val TAG = "SellersFragment"
    }
}