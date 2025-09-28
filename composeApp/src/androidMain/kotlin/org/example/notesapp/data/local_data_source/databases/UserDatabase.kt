package org.example.notesapp.data.local_data_source.databases

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.notesapp.Database
import org.example.notesapp.Database.Companion.Schema
import org.example.notesapp.constants.data.DomainAgnosticConstants.DATABASE_FILE_NAME
import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserDatabase(private val context: Context) {
    actual fun getInstance(): UserDataAccessObject {
        val databaseDriverImplementation: SqlDriver =
            AndroidSqliteDriver(Schema, context, DATABASE_FILE_NAME)

        val databaseImplementation = Database(databaseDriverImplementation)

        return UserDataAccessObject(databaseImplementation.userQueries)
    }
}
