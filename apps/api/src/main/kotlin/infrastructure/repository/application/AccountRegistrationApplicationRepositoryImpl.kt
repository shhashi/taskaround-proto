package taskaround.infrastructure.repository.application

import org.koin.core.annotation.Single
import taskaround.application.dto.RegistrationCodeDto
import taskaround.application.repository.AccountRegistrationApplicationRepository
import taskaround.infrastructure.DatabaseFactory
import taskaround.infrastructure.dao.RegistrationCodesDao
import taskaround.infrastructure.loggedTransaction

@Single
class AccountRegistrationApplicationRepositoryImpl(
    private val registrationCodesDao: RegistrationCodesDao,
) : AccountRegistrationApplicationRepository {
    override fun fetchAccountRegistrationCodes(accountRegistrationCode: String): List<RegistrationCodeDto>? {
        val registrationCodeEntities =
            loggedTransaction(DatabaseFactory.taskaround) {
                registrationCodesDao.findByRegistrationCodeId(accountRegistrationCode)
            }
        if (registrationCodeEntities.isEmpty()) {
            return null
        } else {
            val registrationCodeDtoList =
                registrationCodeEntities.map { registrationCodeEntity ->
                    RegistrationCodeDto(
                        registrationCode = registrationCodeEntity.registrationCode!!,
                        createAccountId = registrationCodeEntity.createAccountId!!,
                        accountId = registrationCodeEntity.accountId,
                        expiredIn = registrationCodeEntity.expiredIn!!,
                    )
                }
            return registrationCodeDtoList
        }
    }

    override fun updateRegistrationCode(registrationCode: String, accountId: Int): Boolean {
        val registrationCodeEntities = loggedTransaction(DatabaseFactory.taskaround) {
            registrationCodesDao.findByRegistrationCodeId(registrationCode)
        }

        if (registrationCodeEntities.isEmpty()) {
            return false
        }

        val registrationCodeEntity = registrationCodeEntities.first()

        loggedTransaction(DatabaseFactory.taskaround) {
            registrationCodesDao.updateWithRegisteredAccountId(registrationCodeEntity, accountId)
        }

        return true
    }
}
