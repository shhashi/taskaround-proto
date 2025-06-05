package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class AccountRoleEntity(
    val accountRoleId: Int? = null,
    val accountId: Int? = null,
    val projectId: String? = null,
    val roleId: String? = null,
    val createdAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
)
