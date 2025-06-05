package taskaround.application.dto

import kotlinx.datetime.LocalDate

data class PersonHoursFetchedDto(
    val personHoursList: List<PersonHoursDto>
) {
    data class PersonHoursDto(
        val taskId: String,
        val accountId: Int,
        val hour: Float,
        val workedAt: LocalDate,
    )
}
