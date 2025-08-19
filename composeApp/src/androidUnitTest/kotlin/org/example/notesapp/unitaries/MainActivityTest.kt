package org.example.notesapp.unitaries

import org.example.notesapp.MainActivity
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    @Test
    fun testIfMainActivitySetsUpAllActivityNeededDependencies() {
        Robolectric.buildActivity(MainActivity::class.java).use { controller ->
            controller.setup()

            val activityInstance = controller.get()

            assertNotNull(activityInstance)
        }
    }
}
