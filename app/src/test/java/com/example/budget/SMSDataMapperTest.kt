package com.example.budget

import com.example.budget.model.domain.SmsData
import com.example.budget.model.utils.SmsDataMapper
import com.example.budget.repository.DBRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class SMSDataMapperTest {
    //val appclass: App = Mockito.mock<App>()

    private lateinit var databaseHelper : DatabaseHelper

    @Mock
    private lateinit var app: App



    @Before
    fun SetUp(){
        MockitoAnnotations.initMocks(this)
        databaseHelper = app.getDatabaseHelper()

    }
    @Test
    fun smsValidator_CorrectSMS_ReturnsTrue() {
        runTest {
                val  dbRepository = DBRepository(databaseHelper)
                val smsDataMapper = SmsDataMapper(dbRepository)
                val smsData = SmsData(
                    1704195664036,
                    "Tinkoff",
                    "Покупка, карта *0345. 350 RUB. UTTS KAVGOLOVO. Доступно 161669.59 RUB",
                    false,
                    null
                )
                val budgetEntry =
                    smsDataMapper.convertSMSToBudgetEntry(smsData)
                Assert.assertTrue(budgetEntry != null)

        }
    }
}
