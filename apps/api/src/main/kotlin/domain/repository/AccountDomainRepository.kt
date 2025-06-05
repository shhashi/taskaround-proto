package taskaround.domain.repository

import taskaround.domain.model.entity.Account


interface AccountDomainRepository {
    fun create(account: Account): Account
}
