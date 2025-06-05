package taskaround.infrastructure.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone

object PersonHours : Table("person_hours") {
    val personHourId = long("person_hour_id").autoIncrement()
    val accountId = integer("account_id")
        .references(Accounts.accountId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val taskId = varchar("task_id", 100)
        .references(Tasks.taskId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val personHour = decimal("person_hour", 3, 2).nullable()
    val workingDay = date("working_day").nullable()
    val createdAt = timestampWithTimeZone("created_at")

    override val primaryKey = PrimaryKey(personHourId)
}
