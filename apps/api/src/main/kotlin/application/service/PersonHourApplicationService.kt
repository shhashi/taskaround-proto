package taskaround.application.service

import org.koin.core.annotation.Single
import taskaround.application.dto.PersonHourUpsertDto
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.Task
import taskaround.domain.model.entity.task.PersonHour
import taskaround.domain.repository.TaskDomainRepository

@Single
class PersonHourApplicationService(
    private val taskDomainRepository: TaskDomainRepository
) {
    fun savePersonHour(personHourUpsertDto: PersonHourUpsertDto) {
        val task = Task(
            id = personHourUpsertDto.taskId,
        )
        val personHour = PersonHour(
            account = Account(id = personHourUpsertDto.accountId),
            hours = personHourUpsertDto.hour,
            workedAt = personHourUpsertDto.workedAt,
        )
        taskDomainRepository.savePersonHour(task, personHour)
    }
}
