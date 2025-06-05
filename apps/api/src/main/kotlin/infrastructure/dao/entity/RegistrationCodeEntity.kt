package taskaround.infrastructure.dao.entity

import java.time.OffsetDateTime

data class RegistrationCodeEntity(
    val registrationCodeId: Int? = null,
    val registrationCode: String? = null,
    val createAccountId: Int? = null,
    val accountId: Int? = null,
    val expiredIn: OffsetDateTime? = null,
    val createdAt: OffsetDateTime? = null,
)
