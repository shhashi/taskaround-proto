package taskaround.infrastructure.dao

import org.jetbrains.exposed.sql.insert
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.AccountEntity
import taskaround.infrastructure.dao.tables.Accounts
import java.time.OffsetDateTime

@Single
class AccountsDao {
    fun create(accountEntity: AccountEntity): AccountEntity {
        val account =
            Accounts.insert {
                it[loginId] = accountEntity.loginId!!
                it[accountName] = accountEntity.accountName!!
                it[password] = accountEntity.password!!
                it[createdAt] = OffsetDateTime.now()
            }
        return AccountEntity(
            accountId = account[Accounts.accountId],
            loginId = account[Accounts.loginId],
            accountName = account[Accounts.accountName],
            createdAt = account[Accounts.createdAt],
        )
    }
}
