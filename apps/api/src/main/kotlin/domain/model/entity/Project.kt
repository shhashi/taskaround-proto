package taskaround.domain.model.entity

/**
 * プロジェクト
 *
 * @property id プロジェクトID
 * @property name プロジェクト名
 */
data class Project(
    val id: String,
    val name: String? = null,
) {
    init {
        require(id.isNotBlank()) { "プロジェクトIDが空文字です。" }
        require(id.matches(Regex("^[A-Z]+$"))) { "プロジェクトIDに無効な文字列が含まれています" }
        name?.let {
            require(it.isNotBlank()) { "プロジェクト名が空文字です。" }
        }
    }
}
