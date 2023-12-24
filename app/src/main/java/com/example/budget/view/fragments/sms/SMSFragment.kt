package com.example.budget.view.fragments.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.budget.broadcastreceiver.SMS_BODY
import com.example.budget.broadcastreceiver.SMS_DATE
import com.example.budget.broadcastreceiver.SMS_SENDER
import com.example.budget.databinding.FragmentSmsBinding
import com.example.budget.model.database.entity.SmsDataEntity
import com.example.budget.model.domain.SmsData
import com.example.budget.viewmodel.SMSFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

class SMSFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "SMSFragment"
    }

    private var _binding: FragmentSmsBinding? = null

    private val binding get() = _binding!!

    val viewModel: SMSFragmentViewModel by viewModels()

    var broadcastReceiver:BroadcastReceiver? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var smsList: List<SmsData>? = viewModel._smsDataList.value

        val smsAdapter = SMSFragmentAdapter(smsList)

        binding.smsRecyclerView.adapter = smsAdapter

        registerReceiver()

        viewModel._smsDataList.observe(viewLifecycleOwner) {
            smsAdapter.setList(it)
        }
        Timber.i("onViewCreated SMSFragment")
    }

    private fun registerReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                intent?.let {
                    val date = it.getLongExtra(SMS_DATE, 0)
                    val body = it.getStringExtra(SMS_BODY).toString()
                    val sender = it.getStringExtra(SMS_SENDER).toString()
                    viewModel.updateSMS(SmsDataEntity(date, sender, body, false))
                }
            }

        }
        context?.registerReceiver(broadcastReceiver, IntentFilter("com.example.budget"))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (broadcastReceiver!=null){
            activity?.unregisterReceiver(broadcastReceiver)
        }
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