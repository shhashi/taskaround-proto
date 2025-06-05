package taskaround.infrastructure.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object AccountRoles : Table("account_roles") {
    val accountRoleId = integer("account_role_id").autoIncrement()
    val accountId = integer("account_id").references(
        Accounts.accountId,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val projectId = varchar("project_id", 100).references(
        Projects.projectId,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val roleId = varchar("role_id", 100).references(
        Roles.roleId,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val createdAt = timestampWithTimeZone("created_at")
    val deletedAt = timestampWithTimeZone("deleted_at").nullable()

    override val primaryKey = PrimaryKey(accountRoleId)
}
