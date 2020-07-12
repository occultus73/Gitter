package io.github.occultus73.gitter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.occultus73.gitter.ui.authentication.AuthenticationActivity
import junit.runner.Version.main

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4ClassRunner::class)
class AuthenticationActivityTest {

    @Test
    fun testActivity_inView() {
        val activityScenario = ActivityScenario.launch(AuthenticationActivity::class.java)

        onView(withId(R.id.authentication)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}