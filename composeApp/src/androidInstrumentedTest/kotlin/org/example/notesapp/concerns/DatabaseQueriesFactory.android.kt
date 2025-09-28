package org.example.notesapp.concerns

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.notesapp.Database
import orgexamplenotesappdatalocaldatabase.NoteQueries
import orgexamplenotesappdatalocaldatabase.UserQueries

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DatabaseQueriesFactory {
    actual fun getUserQueriesInstance(): UserQueries {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val databaseDriverImplementation: SqlDriver =
            AndroidSqliteDriver(Database.Schema, context, null)

        return Database(databaseDriverImplementation).userQueries
    }

    actual fun getNoteQueriesInstance(): NoteQueries {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val databaseDriverImplementation: SqlDriver =
            AndroidSqliteDriver(Database.Schema, context, null)

        return Database(databaseDriverImplementation).noteQueries
    }
}