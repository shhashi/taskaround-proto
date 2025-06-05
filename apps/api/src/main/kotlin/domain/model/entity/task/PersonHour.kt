package taskaround.domain.model.entity.task

import kotlinx.datetime.LocalDate
import taskaround.domain.model.entity.Account

/**
 * 工数
 *
 * @property account 作業者
 * @property hours 稼働時間
 * @property workedAt 作業日時
 */
data class PersonHour(
    val account: Account? = null,
    val hours: Float? = null,
    val workedAt: LocalDate ? = null,
)
