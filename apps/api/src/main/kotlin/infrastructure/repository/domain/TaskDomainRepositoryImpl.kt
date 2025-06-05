package taskaround.infrastructure.repository.domain

import org.koin.core.annotation.Single
import taskaround.domain.model.entity.Task
import taskaround.domain.model.entity.task.PersonHour
import taskaround.domain.repository.TaskDomainRepository
import taskaround.infrastructure.DatabaseFactory
import taskaround.infrastructure.dao.PersonHoursDao
import taskaround.infrastructure.dao.TaskDao
import taskaround.infrastructure.dao.entity.PersonHourEntity
import taskaround.infrastructure.dao.entity.TaskEntity
import taskaround.infrastructure.loggedTransaction

@Single
class TaskDomainRepositoryImpl(
    private val taskDao: TaskDao,
    private val personHourDao: PersonHoursDao,
) : TaskDomainRepository {
    override fun update(task: Task) {
        val taskEntity = TaskEntity(
            taskId = task.id!!,
            taskName = task.name,
            description = task.description,
            finishDate = task.deadline,
            accountId = task.assignee?.id,
            projectId = task.parentProject?.id,
            statusId = task.taskStatus?.id,
        )
        loggedTransaction(DatabaseFactory.taskaround) {
            taskDao.update(taskEntity)
        }
    }

    override fun savePersonHour(
        task: Task,
        personHour: PersonHour
    ) {
        val personHourEntity = PersonHourEntity(
            taskId = task.id!!,
            accountId = personHour.account?.id!!,
            personHour = personHour.hours?.toBigDecimal()!!,
            workingDay = personHour.workedAt,
        )
        loggedTransaction(DatabaseFactory.taskaround) {
            personHourDao.upsert(personHourEntity)
        }
    }
}
