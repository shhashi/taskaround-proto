package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class StatusEntity(
    val statusId: Int? = null,
    val statusName: String? = null,
    val createdAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
)
