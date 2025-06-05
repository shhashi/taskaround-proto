package taskaround.domain.model.entity

import taskaround.domain.model.entity.account.Authentication
import taskaround.domain.model.entity.account.Role

/**
 * アカウント
 *
 * @property id アカウントID
 * @property accountName 表示名
 * @property authentication 認証情報
 * @property role ロール情報
 */
data class Account(
    val id: Int? = null,
    val accountName: String? = null,
    val authentication: Authentication? = null,
    val role: Role? = null,
)
