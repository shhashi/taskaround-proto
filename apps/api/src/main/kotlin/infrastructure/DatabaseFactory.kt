package taskaround.infrastructure

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import taskaround.config
import java.sql.Connection

object DatabaseFactory {
    lateinit var taskaround: Database

    fun init() {
        taskaround = Database.connect(
            url = config.database.taskaround.url,
            driver = "org.postgresql.Driver",
            user = config.database.taskaround.user,
            password = config.database.taskaround.password
        )
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }
}
