package io.github.occultus73.gitter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.occultus73.gitter.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {
    @Test
    fun testActivity_inView() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.home))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}