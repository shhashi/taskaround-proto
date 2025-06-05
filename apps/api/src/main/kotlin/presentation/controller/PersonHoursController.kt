package taskaround.presentation.controller

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDate
import org.koin.core.annotation.Single
import taskaround.application.dto.PersonHoursFetchDto
import taskaround.application.repository.PersonHoursApplicationRepository

@Single
class PersonHoursController(
    private val personHoursApplicationRepository: PersonHoursApplicationRepository
) {
    suspend fun list(call: RoutingCall, accountId: String, dateFrom: String, dateTo: String) {
        val personHoursFetchDto = PersonHoursFetchDto(
            accountId = accountId.toInt(),
            dateFrom = LocalDate.parse(dateFrom),
            dateTo = LocalDate.parse(dateTo),
        )
        val fetchedPersonHoursFetchDto = personHoursApplicationRepository.fetchPersonHours(personHoursFetchDto)
        call.respond(HttpStatusCode.OK, fetchedPersonHoursFetchDto.personHoursList)
    }
}
