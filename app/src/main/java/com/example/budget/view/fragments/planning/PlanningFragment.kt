package com.example.budget.view.fragments.planning

import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.App
import com.example.budget.databinding.FragmentPlanningListBinding
import com.example.budget.model.constants.BudgetGroupEnum
import com.example.budget.model.database.entity.PlanningNoteEntity
import com.example.budget.model.domain.OperationType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import kotlin.math.abs


class PlanningFragment : Fragment() {

    private var binding: FragmentPlanningListBinding? = null
    private val viewModel by viewModels<PlanningViewModel>()
    private var adapter: PlanningItemsAdapter? = null

    val decimalFormater = DecimalFormat("#,###,###")
    val dateFormater = SimpleDateFormat("dd.mm.yyyy", Locale.getDefault())

    var isEditSheetOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlanningListBinding.inflate(inflater)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(
            (requireContext().applicationContext as App)
                .getDatabaseHelper().getPlanningNoteDao()
        )

        val listAdapter = PlanningItemsAdapter(viewModel.notes)
        this.adapter = listAdapter

        viewModel.onUpdateListEvent = { list ->
            CoroutineScope(Dispatchers.Main).launch {
                recalculateBalance(list)
                println(list)
                //todo replace diffcallback
                listAdapter.notifyDataSetChanged()
            }
        }

        val binding = binding ?: return

        binding.list.run {
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }


        binding.addNote.setOnClickListener {
            changeVisibilityOfSheet()
        }

        initSheetViews()

        viewModel.loadNotes()
    }

    private fun changeVisibilityOfSheet() {
        val binding = binding ?: return
        val behavior = BottomSheetBehavior.from(binding.sheetEdit.bottomSheetContainer)
        if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            initDateField()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        isEditSheetOpen = !isEditSheetOpen
    }

    fun initSheetViews() = binding?.sheetEdit?.run {

        with(editTextAccountTypeText) {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listOf("Расход", "Доход")
                )
            )

            setOnItemClickListener { _, _, position, _ ->
                viewModel.operation = OperationType.entries.find { it.id == position + 1 }
            }
        }

        with(editTextExpenseItemText) {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    BudgetGroupEnum.entries
                )
            )

            setOnItemClickListener { _, _, position, _ ->
                viewModel.budgetGroup = BudgetGroupEnum.entries[position]
            }
        }

        saveButton.setOnClickListener {
            val date = getDateFromField() ?: return@setOnClickListener
            val value = getValueFromField() ?: return@setOnClickListener
            val description = binding?.sheetEdit?.description?.text?.toString() ?: ""

            changeVisibilityOfSheet()

            viewModel.save(date, description, value)
            clearSheetFields()
        }
    }

    private fun getValueFromField(): Long? = try {
        binding?.sheetEdit?.amountEditText?.text.toString().toLong()
    } catch (e: Exception) {
        Toast.makeText(
            requireContext(),
            "Сумма введена не верно.",
            Toast.LENGTH_LONG
        ).show()
        null
    }

    private fun getDateFromField(): Date? {
        val text = binding?.sheetEdit?.editTextDate?.editText?.text ?: return null
        return try {
            val parts = text.split(".").map { it.toInt() }
            Date(parts[2], parts[1], parts[0])
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Дата введена неверно. Пример 28.01.2023",
                Toast.LENGTH_LONG
            ).show()
            null
        }
    }

    private fun initDateField() {
        binding?.sheetEdit?.editTextDate?.editText?.setText(
            dateFormater.format(Date())
        )
    }

    private fun recalculateBalance(notes: List<PlanningNoteEntity>) {
        var income = 0L
        var expense = 0L

        notes.forEach {
            when (it.operationType) {
                OperationType.EXPENSE -> expense += it.value
                OperationType.INCOME -> income += it.value
            }
        }

        val balanceValue = income - expense
        val prefix = when {
            balanceValue == 0L -> ""
            balanceValue > 0 -> "+"
            else -> "-"
        }

        val balanceText = "$prefix ${(abs(balanceValue).formatToText())}"

        binding?.let {
            it.balanceValue.text = balanceText
            it.expenseValue.text = expense.formatToText()
            it.incomeValue.text = income.formatToText()
        }
    }

    private fun clearSheetFields() {
        binding?.sheetEdit?.run {
            editTextDate.editText?.setText("")
            amountEditText.setText("")
            editTextExpenseItemText.setText("")
            editTextAccountTypeText.setText("")
        }
    }

    private fun Number.formatToText() =
        decimalFormater.format(this).replace(",", " ") + " р."

    companion object {

        @JvmStatic
        fun newInstance() = PlanningFragment()
    }
}