package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class TaskEntity(
    val taskId: String? = null,
    val taskName: String? = null,
    val description: String? = null,
    val finishDate: OffsetDateTime? = null,
    val accountId: Int? = null,
    val projectId: String? = null,
    val statusId: Int? = null,
    val createdAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
)
