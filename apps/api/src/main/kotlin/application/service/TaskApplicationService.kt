package taskaround.application.service

import org.koin.core.annotation.Single
import taskaround.application.dto.TaskCreateDto
import taskaround.application.dto.TaskUpdateDto
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.Project
import taskaround.domain.model.entity.Task
import taskaround.domain.model.entity.task.TaskStatus
import taskaround.domain.repository.ProjectDomainRepository
import taskaround.domain.repository.TaskDomainRepository
import java.time.OffsetDateTime

@Single
class TaskApplicationService(
    private val projectDomainRepository: ProjectDomainRepository,
    private val taskDomainRepository: TaskDomainRepository,
) {
    /**
     * タスクを追加する
     *
     * @return タスクID
     */
    fun create(taskCreateDto: TaskCreateDto): String {
        val project = Project(
            id = taskCreateDto.projectId!!
        )
        val task = Task(
            name = taskCreateDto.taskName!!,
            description = taskCreateDto.description,
            deadline = taskCreateDto.finishDate?.let { OffsetDateTime.parse(it) },
            taskStatus = TaskStatus(id = taskCreateDto.statusId!!),
            parentProject = project,
            assignee = Account(id = taskCreateDto.accountId),
        )
        val createdTask = projectDomainRepository.addTask(project, task)
        return createdTask.id!!
    }

    fun update(task: TaskUpdateDto) {
        val task = Task(
            id = task.taskId!!,
            name = task.taskName,
            description = task.description,
            deadline = task.finishDate?.let { OffsetDateTime.parse(it) },
            assignee = task.accountId?.let { Account(id = it) },
            taskStatus = task.statusId?.let { TaskStatus(id = it) },
        )
        taskDomainRepository.update(task)
    }
}
