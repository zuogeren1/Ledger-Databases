package net.quiltservertools.ledger.databases.databases

import com.github.quiltservertools.ledger.Ledger  // ✅ 修正包名，按你本地 Ledger 实际包名调整
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.quiltservertools.ledger.databases.DatabaseExtensionSpec
import net.quiltservertools.ledger.databases.LedgerDatabases
import java.nio.file.Path
import javax.sql.DataSource

object MariaDB : LedgerDatabase {
    override fun getDataSource(savePath: Path): DataSource = HikariDataSource(HikariConfig().apply {
        jdbcUrl = "jdbc:mariadb://${Ledger.config[DatabaseExtensionSpec.url]!!}"          // ✅ !! 断言非空
        username = Ledger.config[DatabaseExtensionSpec.userName]!!
        password = Ledger.config[DatabaseExtensionSpec.password]!!
        maximumPoolSize = Ledger.config[DatabaseExtensionSpec.maxPoolSize]!!
        connectionTimeout = Ledger.config[DatabaseExtensionSpec.connectionTimeout]!!.toLong()  // ✅ Int→Long
        maxLifetime = Ledger.config[DatabaseExtensionSpec.maxLifetime]!!.toLong()
        addDataSourceProperty("rewriteBatchedStatements", "true")
        addDataSourceProperty("cachePrepStmts", true)
        addDataSourceProperty("prepStmtCacheSize", 250)
        addDataSourceProperty("prepStmtCacheSqlLimit", 2048)
        addDataSourceProperty("useServerPrepStmts", true)
        addDataSourceProperty("cacheCallableStmts", true)
        addDataSourceProperty("cacheResultSetMetadata", true)
        addDataSourceProperty("cacheServerConfiguration", true)
        addDataSourceProperty("useLocalSessionState", true)
        addDataSourceProperty("elideSetAutoCommits", true)
        addDataSourceProperty("alwaysSendSetIsolation", false)
        addDataSourceProperty("useJDBCCompliantTimezoneShift", true)
        addDataSourceProperty("useLegacyDatetimeCode", false)
        addDataSourceProperty("serverTimezone", "UTC")
        // ✅ 显式转为 Map<String, Any> 再遍历
        (Ledger.config[DatabaseExtensionSpec.properties] as? Map<String, Any>)?.forEach { (key, value) ->
            addDataSourceProperty(key, value)
        }
    })

    override fun getDatabaseIdentifier() = LedgerDatabases.identifier("mariadb")
}