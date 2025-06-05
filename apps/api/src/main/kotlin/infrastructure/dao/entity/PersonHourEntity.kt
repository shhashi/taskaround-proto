package taskaround.infrastructure.dao.entity

import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import java.time.OffsetDateTime

data class PersonHourEntity(
    val personHourId: Long? = null,
    val accountId: Int? = null,
    val taskId: String? = null,
    val personHour: BigDecimal? = null,
    val workingDay: LocalDate? = null,
    val createdAt: OffsetDateTime? = null,
)
