package net.quiltservertools.ledger.databases.databases

import com.github.quiltservertools.ledger.Ledger
import org.sqlite.SQLiteDataSource
import java.nio.file.Path
import javax.sql.DataSource
import kotlin.io.path.pathString

object SQLite : LedgerDatabase {
    override fun getDataSource(savePath: Path): DataSource {  // ✅ 明确返回类型帮助类型推断
        return SQLiteDataSource().apply {
            setUrl("jdbc:sqlite:${savePath.resolve("ledger.sqlite").pathString}")  // ✅ url= → setUrl()
        }
    }

    override fun getDatabaseIdentifier() = Ledger.identifier(Ledger.DEFAULT_DATABASE)
}