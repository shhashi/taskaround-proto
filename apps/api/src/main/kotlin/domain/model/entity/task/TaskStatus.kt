package taskaround.domain.model.entity.task

/**
 * タスクステータス
 *
 * @property id ステータスID
 * @property name ステータス名
 */
data class TaskStatus(
    val id: Int,
    val name: String? = null,
)
