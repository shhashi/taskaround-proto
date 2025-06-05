package taskaround.application.repository

import taskaround.application.dto.RegistrationCodeDto

interface AccountRegistrationApplicationRepository {
    fun fetchAccountRegistrationCodes(accountRegistrationCode: String): List<RegistrationCodeDto>?
    fun updateRegistrationCode(registrationCode: String, accountId: Int): Boolean
}
