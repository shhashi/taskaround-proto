package taskaround.application.dto

data class AccountDto(
    val accountId: Int? = null,
    val accountName: String,
    val loginId: String,
    val password: String? = null,
)
