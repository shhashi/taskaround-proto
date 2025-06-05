package taskaround.infrastructure.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object RegistrationCodes : Table("registration_codes") {
    val registrationCodeId = integer("registration_code_id").autoIncrement()
    val registrationCode = varchar("registration_code", 32).nullable()
    val createAccountId = integer("create_account_id")
        .references(Accounts.accountId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
        .nullable()
    val accountId = integer("account_id")
        .references(Accounts.accountId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
        .nullable()
    val expiredIn = timestampWithTimeZone("expired_in").nullable()
    val createdAt = timestampWithTimeZone("created_at")

    override val primaryKey = PrimaryKey(registrationCodeId)
}
