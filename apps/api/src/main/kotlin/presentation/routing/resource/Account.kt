package taskaround.presentation.routing.resource

import io.ktor.resources.*

@Resource("/account")
class Account {
    @Resource("register")
    class Registration(
        val parent: Account = Account(),
        val registerCode: String,
    ) {
        @Resource("validate")
        class Validation(
            val parent: Registration,
        )
    }
}
