package taskaround.domain.service

import org.koin.core.annotation.Single
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.Project
import taskaround.domain.repository.ProjectDomainRepository

@Single
class ProjectDomainService(
    private val projectDomainRepository: ProjectDomainRepository
) {
    fun addMember(project: Project, account: Account) {
        // 既存メンバーリストを取得する
        val registeredMembers = projectDomainRepository.fetchMembers(project = project)
        // 既存メンバーに含まれていればスキップ
        if (registeredMembers.any { registeredMember -> registeredMember.id == account.id }) return
        // そうでなければ永続化
        projectDomainRepository.addMember(
            project = project,
            member = account
        )
    }
}
