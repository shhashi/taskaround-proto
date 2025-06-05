package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class RoleEntity(
    val roleId: String? = null,
    val roleName: String? = null,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
)
