package taskaround.infrastructure.dao

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.RegistrationCodeEntity
import taskaround.infrastructure.dao.tables.RegistrationCodes

@Single
class RegistrationCodesDao {
    fun create(registrationCodeEntity: RegistrationCodeEntity): RegistrationCodeEntity {
        val registrationCode =
            RegistrationCodes.insert {
                it[registrationCode] = registrationCodeEntity.registrationCode!!
                it[createAccountId] = registrationCodeEntity.createAccountId!!
                it[expiredIn] = registrationCodeEntity.expiredIn!!
                it[createdAt] = registrationCodeEntity.createdAt!!
            }
        return RegistrationCodeEntity(
            registrationCodeId = registrationCode[RegistrationCodes.registrationCodeId],
            registrationCode = registrationCode[RegistrationCodes.registrationCode],
            createAccountId = registrationCode[RegistrationCodes.createAccountId],
            expiredIn = registrationCode[RegistrationCodes.expiredIn],
            createdAt = registrationCode[RegistrationCodes.createdAt],
        )
    }

    fun findByRegistrationCodeId(registrationCode: String): List<RegistrationCodeEntity> =
        RegistrationCodes
            .select(RegistrationCodes.columns)
            .where { RegistrationCodes.registrationCode eq registrationCode }
            .map {
                RegistrationCodeEntity(
                    registrationCodeId = it[RegistrationCodes.registrationCodeId],
                    registrationCode = it[RegistrationCodes.registrationCode],
                    createAccountId = it[RegistrationCodes.createAccountId],
                    expiredIn = it[RegistrationCodes.expiredIn],
                    createdAt = it[RegistrationCodes.createdAt],
                )
            }

    fun updateWithRegisteredAccountId(
        registrationCodeEntity: RegistrationCodeEntity,
        accountId: Int,
    ) {
        RegistrationCodes.update(where = { RegistrationCodes.registrationCode eq registrationCodeEntity.registrationCode }) {
            it[RegistrationCodes.accountId] = accountId
        }
    }
}
