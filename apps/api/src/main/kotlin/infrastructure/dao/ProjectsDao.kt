package taskaround.infrastructure.dao

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.ProjectEntity
import taskaround.infrastructure.dao.tables.Projects
import java.time.OffsetDateTime

@Single
class ProjectsDao {
    fun create(projectEntity: ProjectEntity): ProjectEntity {
        val project =
            Projects.insert {
                it[Projects.projectId] = projectEntity.projectId!!
                it[Projects.projectName] = projectEntity.projectName!!
                it[Projects.createdAt] = OffsetDateTime.now()
            }
        return ProjectEntity(
            projectId = project[Projects.projectId],
            projectName = project[Projects.projectName],
            createdAt = project[Projects.createdAt],
        )
    }

    fun update(projectEntity: ProjectEntity) {
        Projects.update({ Projects.projectId eq projectEntity.projectId!! and Projects.deletedAt.isNull() }) {
            it[Projects.projectName] = projectEntity.projectName!!
        }
    }

    fun findByProjectId(projectId: String) =
        Projects
            .select(Projects.columns)
            .where { Projects.projectId eq projectId and Projects.deletedAt.isNull() }
            .map {
                ProjectEntity(
                    projectId = it[Projects.projectId],
                    projectName = it[Projects.projectName],
                    createdAt = it[Projects.createdAt],
                )
            }
}
