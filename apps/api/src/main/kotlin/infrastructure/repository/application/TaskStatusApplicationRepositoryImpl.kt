package taskaround.infrastructure.repository.application

import org.koin.core.annotation.Single
import taskaround.application.dto.TaskStatusFetchDto
import taskaround.application.repository.TaskStatusApplicationRepository
import taskaround.infrastructure.dao.StatusDao

@Single
class TaskStatusApplicationRepositoryImpl(
    private val statusDao: StatusDao,
) : TaskStatusApplicationRepository {
    override fun fetchTaskStatuses() = TaskStatusFetchDto(
        status = statusDao.findAll().associate { it.statusId!! to it.statusName!! }
    )
}
