package taskaround.application.dto

import java.time.OffsetDateTime

data class RegistrationCodeDto(
    val registrationCode: String? = null,
    val createAccountId: Int? = null,
    val accountId: Int? = null,
    val expiredIn: OffsetDateTime? = null,
)
