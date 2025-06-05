package taskaround.infrastructure.dao

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.TaskEntity
import taskaround.infrastructure.dao.tables.Tasks

@Single
class TaskDao {
    fun create(taskEntity: TaskEntity): TaskEntity {
        val task = Tasks.insert {
            it[Tasks.taskName] = taskEntity.taskName
            it[Tasks.description] = taskEntity.description
            it[Tasks.finishDate] = taskEntity.finishDate
            it[Tasks.accountId] = taskEntity.accountId
            it[Tasks.projectId] = taskEntity.projectId
            it[Tasks.statusId] = taskEntity.statusId
            it[Tasks.createdAt] = taskEntity.createdAt
        }
        return TaskEntity(
            taskId = task[Tasks.taskId],
            taskName = task[Tasks.taskName],
            description = task[Tasks.description],
            finishDate = task[Tasks.finishDate],
            accountId = task[Tasks.accountId],
            projectId = task[Tasks.projectId],
            statusId = task[Tasks.statusId],
            createdAt = task[Tasks.createdAt],
        )
    }

    fun update(taskEntity: TaskEntity) {
        Tasks.update({ Tasks.taskId eq taskEntity.taskId!! }) { record ->
            taskEntity.taskName?.let { it -> record[Tasks.taskName] = it }
            taskEntity.description?.let { it -> record[Tasks.description] = it }
            taskEntity.finishDate?.let { it -> record[Tasks.finishDate] = it }
            taskEntity.statusId?.let { it -> record[Tasks.statusId] = it }
            taskEntity.accountId?.let { it -> record[Tasks.accountId] = it }
        }
    }
}
