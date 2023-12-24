package com.example.budget.view.fragments.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.budget.databinding.FragmentSmsBinding
import com.example.budget.model.domain.SmsData
import com.example.budget.viewmodel.SMSFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

class SMSFragment : BottomSheetDialogFragment() {
    companion object{
        const val TAG = "!!! SMSFragment"
    }

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

        val viewModel: SMSFragmentViewModel by viewModels()

        val smsList: List<SmsData>?  = viewModel.SMSListLiveData.value
        Timber.tag(TAG).i("smsList = $smsList")

        val smsAdapter = SMSFragmentAdapter(smsList)
        Timber.tag(TAG).i("smsAdapter = $smsAdapter")

        binding.smsRecyclerView.adapter = smsAdapter

        viewModel.SMSListLiveData.observe(viewLifecycleOwner){
            smsAdapter.setList(it)
            Timber.tag(TAG).i("viewModel.SMSListLiveData   smsAdapter = $smsAdapter")
        }
        Timber.i("onViewCreated SMSFragment")
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}