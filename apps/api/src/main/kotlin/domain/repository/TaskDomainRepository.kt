package taskaround.domain.repository

import taskaround.domain.model.entity.Task
import taskaround.domain.model.entity.task.PersonHour

interface TaskDomainRepository {
    fun update(task: Task)
    fun savePersonHour(task: Task, personHour: PersonHour)
}
