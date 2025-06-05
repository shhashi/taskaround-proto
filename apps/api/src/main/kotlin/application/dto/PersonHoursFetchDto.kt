package taskaround.application.dto

import kotlinx.datetime.LocalDate

data class PersonHoursFetchDto(
    val accountId: Int,
    val dateFrom: LocalDate,
    val dateTo: LocalDate,
)
