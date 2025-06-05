package taskaround.infrastructure.dao.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object Accounts : Table("accounts") {
    val accountId = integer("account_id").autoIncrement()
    val loginId = varchar("login_id", 100)
    val accountName = varchar("account_name", 20)
    val password = varchar("password", 100)
    val createdAt = timestampWithTimeZone("created_at")
    val deletedAt = timestampWithTimeZone("deleted_at").nullable()

    override val primaryKey = PrimaryKey(accountId)
}
