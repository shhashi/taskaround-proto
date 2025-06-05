package taskaround.infrastructure.repository.domain

import org.koin.core.annotation.Single
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.Project
import taskaround.domain.model.entity.Task
import taskaround.domain.model.entity.task.TaskStatus
import taskaround.domain.repository.ProjectDomainRepository
import taskaround.infrastructure.DatabaseFactory
import taskaround.infrastructure.dao.AccountRolesDao
import taskaround.infrastructure.dao.ProjectsDao
import taskaround.infrastructure.dao.TaskDao
import taskaround.infrastructure.dao.entity.AccountRoleEntity
import taskaround.infrastructure.dao.entity.ProjectEntity
import taskaround.infrastructure.dao.entity.TaskEntity
import taskaround.infrastructure.loggedTransaction
import java.time.OffsetDateTime

@Single
class ProjectDomainRepositoryImpl(
    private val projectsDao: ProjectsDao,
    private val accountRolesDao: AccountRolesDao,
    private val taskDao: TaskDao,
) : ProjectDomainRepository {
    override fun create(project: Project): Project {
        val projectEntity = ProjectEntity(
            projectId = project.id,
            projectName = project.name,
            createdAt = OffsetDateTime.now()
        )
        val createdProjectEntity = loggedTransaction(DatabaseFactory.taskaround) {
            projectsDao.create(projectEntity)
        }

        // プロジェクト作成時にプロジェクト内のタスク番号のシーケンスを作成する。
        loggedTransaction(DatabaseFactory.taskaround) {
            exec("CREATE SEQUENCE task_id_sequence_${project.id} START WITH 1 INCREMENT BY 1")
        }

        return Project(
            id = createdProjectEntity.projectId!!,
            name = createdProjectEntity.projectName!!
        )
    }

    override fun update(project: Project): Project {
        val projectEntity = ProjectEntity(
            projectId = project.id,
            projectName = project.name,
        )
        val updatedProject = loggedTransaction(DatabaseFactory.taskaround) {
            projectsDao.update(projectEntity)
            return@loggedTransaction projectsDao.findByProjectId(project.id).first()
        }
        return Project(
            id = updatedProject.projectId!!,
            name = updatedProject.projectName!!
        )
    }

    override fun fetchMembers(project: Project): List<Account> {
        val accountRoleEntities = loggedTransaction(DatabaseFactory.taskaround) {
            accountRolesDao.findByProjectId(project.id)
        }
        return accountRoleEntities.map { accountRoleEntity ->
            Account(
                id = accountRoleEntity.accountId!!,
            )
        }
    }

    override fun addMember(
        project: Project,
        member: Account
    ): Boolean {
        val accountRoleEntity = AccountRoleEntity(
            accountId = member.id,
            projectId = project.id,
            roleId = member.role?.id,
        )
        loggedTransaction(DatabaseFactory.taskaround) {
            accountRolesDao.create(accountRoleEntity)
        }
        return true
    }

    override fun addTask(project: Project, task: Task): Task {
        val task = loggedTransaction(DatabaseFactory.taskaround) {
            // タスク ID 用のシーケンスから値を取得
            val sequenceId = exec("SELECT nextval('task_id_sequence_${project.id}')") { rs ->
                if (rs.next()) rs.getLong(1) else null
            }
            val taskEntity = TaskEntity(
                taskId = project.id + "-" + sequenceId!!.toString(),
                taskName = task.name,
                description = task.description,
                finishDate = task.deadline,
                accountId = task.assignee?.id,
                projectId = project.id,
                statusId = task.taskStatus?.id,
                createdAt = OffsetDateTime.now(),
            )
            val task = taskDao.create(taskEntity)
            return@loggedTransaction Task(
                id = task.taskId!!,
                name = task.taskName,
                description = task.description,
                deadline = task.finishDate,
                assignee = Account(
                    id = task.accountId!!,
                ),
                taskStatus = TaskStatus(
                    id = task.statusId!!,
                ),
            )
        }
        return task
    }
}
