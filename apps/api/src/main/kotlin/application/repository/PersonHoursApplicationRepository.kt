package taskaround.application.repository

import taskaround.application.dto.PersonHoursFetchDto
import taskaround.application.dto.PersonHoursFetchedDto

interface PersonHoursApplicationRepository {
    fun fetchPersonHours(personHoursFetchDto: PersonHoursFetchDto): PersonHoursFetchedDto
}
