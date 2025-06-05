package taskaround.domain.model.entity.account

/**
 * 認証情報
 *
 * @property id ログインID
 * @property password パスワード。不用意な値の埋め込みを避けるため、nullableにする。
 */
data class Authentication(
    val id: String,
    val password: String? = null,
    val hashedPassword: String? = null,
) {
    init {
        password.let {
            require(!it.isNullOrEmpty()) { "パスワードが設定されていません。" }
            require(it.length >= 8) { "パスワードは8文字以上である必要があります" }
            require(it.matches(Regex("^[a-z0-9]+$"))) { "パスワードは半角英字小文字と半角数字のみで構成する必要があります" }
        }
    }
}

