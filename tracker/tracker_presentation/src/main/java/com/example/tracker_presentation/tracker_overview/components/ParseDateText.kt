package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private const val DATE_PATTERN = "dd LLLL"
@Composable
fun ParseDateText(date: LocalDate) : String {
     val today = LocalDate.now()
     return when(date){
          today -> stringResource(id = com.vitorthemyth.core.R.string.today)
          today.minusDays(1) -> stringResource(id = com.vitorthemyth.core.R.string.yesterday)
          today.plusDays(1) -> stringResource(id = com.vitorthemyth.core.R.string.tomorrow)
          else -> DateTimeFormatter.ofPattern(DATE_PATTERN).format(date)
     }
}