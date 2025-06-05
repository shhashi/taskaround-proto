package taskaround.infrastructure.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object Tasks : Table("tasks") {
    val taskId = varchar("task_id", 100)
    val taskName = varchar("task_name", 100).nullable()
    val description = text("description").nullable()
    val finishDate = timestampWithTimeZone("finish_date").nullable()
    val accountId = integer("account_id")
        .references(Accounts.accountId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
        .nullable()
    val projectId = varchar("project_id", 100)
        .references(Projects.projectId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
        .nullable()
    val statusId = integer("status_id")
        .references(Status.statusId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
        .nullable()
    val createdAt = timestampWithTimeZone("created_at")
    val deletedAt = timestampWithTimeZone("deleted_at").nullable()

    override val primaryKey = PrimaryKey(taskId)
}
