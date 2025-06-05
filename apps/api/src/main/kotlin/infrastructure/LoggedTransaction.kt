package taskaround.infrastructure

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> loggedTransaction(
    db: Database? = null,
    statement: Transaction.() -> T,
): T =
    transaction(db) {
        addLogger(StdOutSqlLogger) // TODO できれば開発環境のみで本番環境には表示しないような設定を設けたい
        statement()
    }
