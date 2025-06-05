package taskaround.application.service

import org.koin.core.annotation.Single
import taskaround.application.dto.AccountDto
import taskaround.application.repository.AccountRegistrationApplicationRepository
import taskaround.config
import taskaround.domain.model.entity.Account
import taskaround.domain.model.entity.account.Authentication
import taskaround.domain.repository.AccountDomainRepository
import taskaround.infrastructure.DatabaseFactory
import taskaround.infrastructure.loggedTransaction
import java.time.OffsetDateTime
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

@Single
class AccountRegistrationApplicationService(
    private val accountRegistrationApplicationRepository: AccountRegistrationApplicationRepository,
    private val accountDomainRepository: AccountDomainRepository,
) {
    fun validateAccountRegistrationCode(accountRegistrationCode: String): Boolean {
        val registrationCodes =
            accountRegistrationApplicationRepository.fetchAccountRegistrationCodes(accountRegistrationCode)
        if (registrationCodes.isNullOrEmpty() || registrationCodes.size > 1) return false

        val registrationCode = registrationCodes.first()
        if (registrationCode.expiredIn?.isBefore(OffsetDateTime.now()) == true) return false
        if (registrationCode.accountId != null) return false

        return true
    }

    fun registerAccount(registrationCode: String, accountDto: AccountDto): AccountDto? {
        // 全体のトランザクションを管理するブロック
        // 以下 AI 回答
        // > application service でトランザクションを組むのはおかしいですか
        //   - アプリケーションサービスは、ユースケースを実現するための調整役です
        //   - 複数のドメインオブジェクトやリポジトリの操作を調整する責務があります
        //   - トランザクション境界の管理もこの調整の一部と考えられます
        return loggedTransaction(DatabaseFactory.taskaround) {
            // Validate registration code
            if (!validateAccountRegistrationCode(registrationCode)) {
                return@loggedTransaction null
            }

            // TODO インスタンス化の部分は共通部分でシングルトンで定義したい
            // パスワードのハッシュ化
            val hashInstance = Mac.getInstance("HmacSHA256")
            hashInstance.apply {
                init(SecretKeySpec(config.hash.secret.toByteArray(Charsets.UTF_8), "HmacSHA256"))
            }

            if (accountDto.password.isNullOrEmpty()) {
                throw IllegalArgumentException("パスワードが設定されていません。")
            }

            val hashedPassword = hashInstance.doFinal(
                accountDto.password.toByteArray(Charsets.UTF_8)
            ).joinToString("") { String.format("%02x", it and 255.toByte()) }

            // Create account
            val account = Account(
                accountName = accountDto.accountName,
                authentication = Authentication(
                    id = accountDto.loginId,
                    password = accountDto.password,
                    hashedPassword = hashedPassword,
                )
            )

            val createdAccount = accountDomainRepository.create(account)

            if (createdAccount.id == null) {
                throw IllegalStateException("登録済みのアカウント ID が設定されているはずが設定されていない。")
            }

            // Update registration code
            accountRegistrationApplicationRepository.updateRegistrationCode(
                registrationCode = registrationCode,
                accountId = createdAccount.id
            )

            // Return created account
            return@loggedTransaction AccountDto(
                accountId = createdAccount.id,
                accountName = createdAccount.accountName!!,
                loginId = createdAccount.authentication!!.id,
                password = null // Don't return the password
            )
        }
    }
}
