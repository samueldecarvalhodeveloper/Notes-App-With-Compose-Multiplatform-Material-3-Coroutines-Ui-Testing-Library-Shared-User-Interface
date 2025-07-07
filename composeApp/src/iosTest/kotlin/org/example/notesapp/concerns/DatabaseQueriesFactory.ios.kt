package org.example.notesapp.concerns

import org.example.notesapp.constants.data.DomainAgnosticConstants.WRONG_TESTING_PLATFORM_ERROR_MESSAGE
import orgexamplenotesappdatalocaldatabase.NoteQueries
import orgexamplenotesappdatalocaldatabase.UserQueries

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DatabaseQueriesFactory {
    actual fun getUserQueriesInstance(): UserQueries {
        error(WRONG_TESTING_PLATFORM_ERROR_MESSAGE)
    }

    actual fun getNoteQueriesInstance(): NoteQueries {
        error(WRONG_TESTING_PLATFORM_ERROR_MESSAGE)
    }
}