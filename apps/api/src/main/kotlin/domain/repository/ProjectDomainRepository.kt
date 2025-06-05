package taskaround.domain.repository

import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.Project
import taskaround.domain.model.entity.Task

interface ProjectDomainRepository {
    fun create(project: Project): Project
    fun update(project: Project): Project
    fun fetchMembers(project: Project): List<Account>
    fun addMember(project: Project, member: Account): Boolean
    fun addTask(project: Project, task: Task): Task
}
