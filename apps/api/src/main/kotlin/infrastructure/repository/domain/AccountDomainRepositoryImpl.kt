package taskaround.infrastructure.repository.domain

import org.koin.core.annotation.Single
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.account.Authentication
import taskaround.domain.repository.AccountDomainRepository
import taskaround.infrastructure.DatabaseFactory
import taskaround.infrastructure.dao.AccountsDao
import taskaround.infrastructure.dao.entity.AccountEntity
import taskaround.infrastructure.loggedTransaction
import java.time.OffsetDateTime

@Single
class AccountDomainRepositoryImpl(
    private val accountsDao: AccountsDao,
) : AccountDomainRepository {
    /**
     * アカウントを登録する。
     */
    override fun create(account: Account): Account {
        val createdAccountEntity =
            loggedTransaction(DatabaseFactory.taskaround) {
                val accountEntity =
                    AccountEntity(
                        loginId = account.authentication?.id,
                        accountName = account.accountName,
                        password = account.authentication?.password,
                        createdAt = OffsetDateTime.now(),
                    )
                return@loggedTransaction accountsDao.create(accountEntity)
            }
        return Account(
            id = createdAccountEntity.accountId!!,
            authentication =
                Authentication(
                    id = createdAccountEntity.loginId!!,
                ),
            accountName = createdAccountEntity.accountName!!,
        )
    }
}
