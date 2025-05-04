package org.example.notesapp.concerns

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver.Companion.IN_MEMORY
import org.example.notesapp.Database
import orgexamplenotesappdatalocaldatabase.NoteQueries
import orgexamplenotesappdatalocaldatabase.UserQueries

object DatabaseQueriesFactory {
    fun getUserQueriesInstance(): UserQueries {
        val databaseDriverImplementation: SqlDriver = JdbcSqliteDriver(IN_MEMORY)

        Database.Schema.create(databaseDriverImplementation)

        return Database(databaseDriverImplementation).userQueries
    }

    fun getNoteQueriesInstance(): NoteQueries {
        val databaseDriverImplementation: SqlDriver = JdbcSqliteDriver(IN_MEMORY)

        Database.Schema.create(databaseDriverImplementation)

        return Database(databaseDriverImplementation).noteQueries
    }
}