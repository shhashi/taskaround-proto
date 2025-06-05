package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class AccountEntity(
    val accountId: Int? = null,
    val loginId: String? = null,
    val accountName: String? = null,
    val password: String? = null,
    val createdAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null,
)
