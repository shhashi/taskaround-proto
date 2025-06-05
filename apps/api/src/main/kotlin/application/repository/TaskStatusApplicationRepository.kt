package taskaround.application.repository

import taskaround.application.dto.TaskStatusFetchDto

interface TaskStatusApplicationRepository {
    fun fetchTaskStatuses(): TaskStatusFetchDto
}
