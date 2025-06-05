package taskaround.shared.config

data class Configuration(
    val application: Application,
    val jwt: Jwt,
    val hash: Hash,
    val database: Database,
) {
    data class Application(
        val port: Int,
    )

    data class Jwt(
        val secret: String,
    )

    data class Hash(
        val secret: String,
    )

    data class Database(
        val taskaround: Taskaround,
    ) {
        data class Taskaround(
            val url: String,
            val user: String,
            val password: String,
        )
    }
}
