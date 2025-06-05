package taskaround.application.dto

import kotlinx.datetime.LocalDate

data class PersonHourUpsertDto(
    val taskId: String,
    val accountId: Int,
    val hour: Float,
    val workedAt: LocalDate,
)
