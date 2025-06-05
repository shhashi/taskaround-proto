package taskaround.infrastructure.dao

import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.AccountRoleEntity
import taskaround.infrastructure.dao.tables.AccountRoles
import java.time.OffsetDateTime

@Single
class AccountRolesDao {
    fun create(accountRoleEntity: AccountRoleEntity): AccountRoleEntity {
        val accountRole = AccountRoles.insert {
            it[accountId] = accountRoleEntity.accountId!!
            it[projectId] = accountRoleEntity.projectId!!
            // ロールについては後程実装するため、その際に有効化
            // it[roleId] = accountRoleEntity.roleId!!
            it[createdAt] = OffsetDateTime.now()
        }
        return AccountRoleEntity(
            accountId = accountRole[AccountRoles.accountId],
            projectId = accountRole[AccountRoles.projectId],
            // roleId = accountRole[AccountRoles.roleId],
        )
    }

    fun findByProjectId(projectId: String) =
        AccountRoles
            .select(AccountRoles.columns)
            .where {
                AccountRoles.projectId eq projectId
            }
            .andWhere {
                AccountRoles.deletedAt.isNull()
            }
            .map {
                AccountRoleEntity(
                    accountId = it[AccountRoles.accountId],
                    projectId = it[AccountRoles.projectId],
                    roleId = it[AccountRoles.roleId],
                    createdAt = it[AccountRoles.createdAt],
                )
            }
}
