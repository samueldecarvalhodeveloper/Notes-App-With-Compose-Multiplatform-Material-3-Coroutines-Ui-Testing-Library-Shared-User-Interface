package org.example.notesapp.data.local_data_source.databases

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.notesapp.Database
import org.example.notesapp.Database.Companion.Schema
import org.example.notesapp.constants.data.DomainAgnosticConstants.DATABASE_FILE_NAME
import org.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class NoteDatabase(private val context: Context) {
    actual fun getInstance(): NoteDataAccessObject {
        val databaseDriverImplementation: SqlDriver =
            AndroidSqliteDriver(Schema, context, DATABASE_FILE_NAME)

        val databaseImplementation = Database(databaseDriverImplementation)

        return NoteDataAccessObject(databaseImplementation.noteQueries)
    }
}
