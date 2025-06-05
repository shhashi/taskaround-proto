package taskaround.infrastructure.repository.application

import taskaround.application.dto.PersonHoursFetchDto
import taskaround.application.dto.PersonHoursFetchedDto
import taskaround.application.repository.PersonHoursApplicationRepository
import taskaround.infrastructure.dao.PersonHoursDao

class PersonHoursApplicationRepositoryImpl(
    private val personHoursDao: PersonHoursDao
) : PersonHoursApplicationRepository {
    override fun fetchPersonHours(personHoursFetchDto: PersonHoursFetchDto): PersonHoursFetchedDto {
        val fetchedPersonHour = personHoursDao.findByAccountIdBetweenWorkingDays(
            personHoursFetchDto.accountId,
            personHoursFetchDto.dateFrom,
            personHoursFetchDto.dateTo
        )
        return PersonHoursFetchedDto(
            personHoursList = fetchedPersonHour.map {
                PersonHoursFetchedDto.PersonHoursDto(
                    taskId = it.taskId!!,
                    accountId = it.accountId!!,
                    hour = it.personHour!!.toFloat(),
                    workedAt = it.workingDay!!,
                )
            }
        )
    }
}
