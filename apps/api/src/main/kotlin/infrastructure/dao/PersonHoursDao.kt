package taskaround.infrastructure.dao

import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.upsert
import org.koin.core.annotation.Single
import taskaround.infrastructure.dao.entity.PersonHourEntity
import taskaround.infrastructure.dao.tables.PersonHours
import java.time.OffsetDateTime

@Single
class PersonHoursDao {
    fun findByAccountIdBetweenWorkingDays(
        accountId: Int,
        startWorkingDay: LocalDate,
        endWorkingDay: LocalDate
    ): List<PersonHourEntity> =
        PersonHours
            .select(PersonHours.columns)
            .where {
                (PersonHours.accountId eq accountId) and
                        (PersonHours.workingDay greaterEq startWorkingDay) and
                        (PersonHours.workingDay lessEq endWorkingDay)
            }
            .map {
                PersonHourEntity(
                    personHourId = it[PersonHours.personHourId],
                    accountId = it[PersonHours.accountId],
                    taskId = it[PersonHours.taskId],
                    personHour = it[PersonHours.personHour],
                    workingDay = it[PersonHours.workingDay],
                    createdAt = it[PersonHours.createdAt],
                )
            }

    fun upsert(entity: PersonHourEntity) {
        PersonHours.upsert(
            keys = arrayOf(PersonHours.accountId, PersonHours.taskId),
            where = {
                (PersonHours.accountId eq entity.accountId!!) and
                        (PersonHours.taskId eq entity.taskId!!) and
                        (PersonHours.workingDay eq entity.workingDay!!)
            }
        ) {
            it[PersonHours.accountId] = entity.accountId!!
            it[PersonHours.taskId] = entity.taskId!!
            it[PersonHours.workingDay] = entity.workingDay!!
            it[PersonHours.personHour] = entity.personHour!!
            it[PersonHours.createdAt] = OffsetDateTime.now()
        }
    }
}
