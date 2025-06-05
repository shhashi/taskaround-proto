package taskaround.presentation.routing.resource

import io.ktor.resources.*

@Resource("/person-hours")
class PersonHours {
    @Resource("account")
    class Account(val parent: PersonHours = PersonHours()) {
        @Resource("{accountId}")
        class AccountId(
            val parent: Account = Account(),
            val accountId: String,
            val dateFrom: String,
            val dateTo: String
        )
    }
}
