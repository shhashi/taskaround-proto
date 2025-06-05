package taskaround.infrastructure.dao.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object Status : Table("status") {
    val statusId = integer("status_id").autoIncrement()
    val statusName = varchar("status_name", 100).nullable()
    val createdAt = timestampWithTimeZone("created_at")
    val deletedAt = timestampWithTimeZone("deleted_at").nullable()

    override val primaryKey = PrimaryKey(statusId)
}
