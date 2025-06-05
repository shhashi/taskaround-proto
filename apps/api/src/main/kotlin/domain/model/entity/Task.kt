package taskaround.domain.model.entity

import taskaround.domain.model.entity.task.PersonHour
import taskaround.domain.model.entity.task.TaskStatus
import java.time.OffsetDateTime

/**
 * タスク
 *
 * @property id タスクID
 * @property name タスク名
 * @property description 説明
 * @property deadline タスク終了日
 * @property taskStatus タスクステータス
 * @property parentProject 親プロジェクト
 * @property assignee 担当者
 */
data class Task(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val deadline: OffsetDateTime? = null,
    val taskStatus: TaskStatus? = null,
    val parentProject: Project? = null,
    val assignee: Account? = null,
    val personHours: List<PersonHour>? = null,
)
