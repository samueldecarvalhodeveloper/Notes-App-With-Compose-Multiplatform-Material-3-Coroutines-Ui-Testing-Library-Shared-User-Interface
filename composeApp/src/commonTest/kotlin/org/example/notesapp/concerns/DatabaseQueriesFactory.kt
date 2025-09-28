package org.example.notesapp.concerns

import orgexamplenotesappdatalocaldatabase.NoteQueries
import orgexamplenotesappdatalocaldatabase.UserQueries

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object DatabaseQueriesFactory {
    fun getUserQueriesInstance(): UserQueries

    fun getNoteQueriesInstance(): NoteQueries
}