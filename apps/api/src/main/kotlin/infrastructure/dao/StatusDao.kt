package taskaround.infrastructure.dao

import org.jetbrains.exposed.sql.selectAll
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.StatusEntity
import taskaround.infrastructure.dao.tables.Status

@Single
class StatusDao {
    fun findAll(): List<StatusEntity> =
        Status.selectAll().where {
            Status.deletedAt.isNull()
        }.map {
            StatusEntity(
                statusId = it[Status.statusId],
                statusName = it[Status.statusName],
            )
        }
}
