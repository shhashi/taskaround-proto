package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class ProjectEntity(
    val projectId: String? = null,
    val projectName: String? = null,
    val createdAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
)
