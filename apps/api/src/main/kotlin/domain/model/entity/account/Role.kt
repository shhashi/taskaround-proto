package taskaround.domain.model.entity.account

/**
 * アカウントロール
 *
 * @property id ロールID
 * @property name ロール名
 */
data class Role(
    val id: String,
    val name: String? = null,
)
