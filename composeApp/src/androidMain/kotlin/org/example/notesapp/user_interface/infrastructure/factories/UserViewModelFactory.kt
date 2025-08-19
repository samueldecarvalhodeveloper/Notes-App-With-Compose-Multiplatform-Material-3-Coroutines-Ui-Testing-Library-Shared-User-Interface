package org.example.notesapp.user_interface.infrastructure.factories

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.example.notesapp.data.local_data_source.databases.UserDatabase
import org.example.notesapp.data.remote_data_source.gateways.UserGateway
import org.example.notesapp.data.repositories.UserRepository
import org.example.notesapp.user_interface.view_models.UserViewModel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserViewModelFactory(private val context: Context) {
    private lateinit var instance: UserViewModel

    actual fun getViewModelInstance(): UserViewModel {
        if (::instance.isInitialized.not()) {
            val httpClientImplementation = HttpClient(CIO)
            val userGateway = UserGateway(httpClientImplementation)
            val userDataAccessObject = UserDatabase(context).getInstance()
            val userRepository = UserRepository(userGateway, userDataAccessObject)

            instance = UserViewModel(userRepository)
        }

        return instance
    }
}