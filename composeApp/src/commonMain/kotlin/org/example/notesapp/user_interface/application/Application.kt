package org.example.notesapp.user_interface.application

import androidx.compose.runtime.*
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTES_LISTING_SCREEN
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_ARGUMENT
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_ARGUMENT_KEY
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_MANIPULATING_SCREEN
import org.example.notesapp.constants.user_interface.UserInterfaceConstants.USER_SIGN_IN_SCREEN
import org.example.notesapp.user_interface.screens.NoteManipulatingScreen
import org.example.notesapp.user_interface.screens.NotesListingScreen
import org.example.notesapp.user_interface.screens.UserSignInScreen
import org.example.notesapp.user_interface.view_models.NoteEditingViewModel
import org.example.notesapp.user_interface.view_models.NotesListingViewModel
import org.example.notesapp.user_interface.view_models.UserViewModel

@Composable
fun Application(
    userViewModel: UserViewModel,
    notesListingViewModelFactory: () -> NotesListingViewModel,
    noteEditingViewModelFactory: () -> NoteEditingViewModel
) {
    val navController = rememberNavController()

    userViewModel.verifyIfUserExists {
        navController.navigate(route = NOTES_LISTING_SCREEN)
    }

    NavHost(navController = navController, startDestination = USER_SIGN_IN_SCREEN) {
        composable(USER_SIGN_IN_SCREEN) {
            UserSignInScreen(userViewModel) {
                navController.navigate(route = NOTES_LISTING_SCREEN)
            }
        }
        composable(NOTES_LISTING_SCREEN) {
            val notesListingViewModel =
                remember { notesListingViewModelFactory() }

            NotesListingScreen(
                notesListingViewModel = notesListingViewModel,
                userViewModel = userViewModel
            ) {
                navController.navigate(
                    route = NOTE_MANIPULATING_SCREEN.replace(
                        NOTE_ID_ARGUMENT_KEY,
                        it.toString()
                    )
                )
            }
        }

        composable(
            route = NOTE_MANIPULATING_SCREEN,
            arguments = listOf(navArgument(NOTE_ID_ARGUMENT) { type = IntType })
        ) {
            val noteId = it.arguments!!.getInt(NOTE_ID_ARGUMENT)

            val noteEditingViewModel =
                remember { noteEditingViewModelFactory() }

            NoteManipulatingScreen(
                noteId = noteId,
                noteEditingViewModel = noteEditingViewModel,
            ) {
                navController.navigate(route = NOTES_LISTING_SCREEN)
            }
        }
    }
}