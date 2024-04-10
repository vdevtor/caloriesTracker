package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Composable
fun DaySelector(
     date: LocalDate,
     onPreviousDayClick: () -> Unit,
     onNextDayClick: () -> Unit,
     modifier: Modifier = Modifier

) {
     Row(
          modifier = modifier,
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
     ) {
          IconButton(onClick = onPreviousDayClick) {
               Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = com.vitorthemyth.core.R.string.previous_day)
               )
          }
          Text(
               text = ParseDateText(date = date),
               style = MaterialTheme.typography.h3
          )
          
          IconButton(onClick = onNextDayClick) {
               Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(id = com.vitorthemyth.core.R.string.next_day)
               )
          }
     }
}

@Preview(
     device = "spec:width=1079.9px,height=1919.9px,dpi=480",
     locale = "pt-rBR",
     fontScale = 1.0f,
     backgroundColor = 0xFFFFFFFF
)
@Composable
fun PreviewDaySelector() {
     DaySelector(date = LocalDate.now(),
          onPreviousDayClick = { },
          onNextDayClick = { }
     )
}