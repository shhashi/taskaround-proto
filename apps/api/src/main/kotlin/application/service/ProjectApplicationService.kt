package taskaround.application.service

import org.koin.core.annotation.Single
import taskaround.application.dto.ProjectAddMemberDto
import taskaround.application.dto.ProjectCreateDto
import taskaround.application.dto.ProjectListDto
import taskaround.application.dto.ProjectUpdateDto
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.Project
import taskaround.domain.model.entity.account.Role
import taskaround.domain.repository.ProjectDomainRepository
import taskaround.domain.service.ProjectDomainService
import taskaround.infrastructure.DatabaseFactory
import taskaround.infrastructure.loggedTransaction

@Single
class ProjectApplicationService(
    private val projectDomainRepository: ProjectDomainRepository,
    private val projectDomainService: ProjectDomainService
) {
    fun create(project: ProjectCreateDto): Project {
        val projectDomain = Project(
            id = project.projectId,
            name = project.projectName,
        )
        val createdProject = loggedTransaction(DatabaseFactory.taskaround) {
            val createdProject = projectDomainRepository.create(projectDomain)
            // プロジェクトを作成したアカウントをメンバーとして登録する
            val projectAddMemberDto = ProjectAddMemberDto(
                projectId = createdProject.id,
                accountId = project.createdByAccountId,
                roleId = "ADMIN",
            )
            addMember(projectAddMemberDto)
            return@loggedTransaction createdProject
        }
        return createdProject
    }

    fun update(project: ProjectUpdateDto): Project {
        val projectDomain = Project(
            id = project.projectId,
            name = project.projectName,
        )
        val updatedProject = projectDomainRepository.update(projectDomain)
        return Project(
            id = updatedProject.id,
            name = updatedProject.name
        )
    }

    fun addMember(projectAddMemberDto: ProjectAddMemberDto) {
        if (projectAddMemberDto.roleId == null) throw IllegalArgumentException("アカウントロールが設定されていません。")

        val projectDomain = Project(
            id = projectAddMemberDto.projectId,
        )
        val accountDomain = Account(
            id = projectAddMemberDto.accountId,
            role = Role(
                id = projectAddMemberDto.roleId,
            )
        )
        projectDomainService.addMember(projectDomain, accountDomain)
    }

    fun listMembers(projectListDto: ProjectListDto): List<Account> {
        val project = Project(
            id = projectListDto.projectId,
        )
        return projectDomainRepository.fetchMembers(project)
    }
}
