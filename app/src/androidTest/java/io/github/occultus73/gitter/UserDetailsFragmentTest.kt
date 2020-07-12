package io.github.occultus73.gitter

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.occultus73.gitter.ui.authentication.AuthenticationActivity
import io.github.occultus73.gitter.ui.authentication.login.LoginFragment
import io.github.occultus73.ui.home.search.SearchFragment
import io.github.occultus73.ui.home.userdetails.UserDetailsFragment
import io.github.occultus73.ui.home.users.UserListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserDetailsFragmentTest {

    @Test
    fun testUserDetailsFragment_inView() {
        val fragmentScenario = launchFragmentInContainer<UserDetailsFragment>()

        onView(withId(R.id.user_details)).check(matches(ViewMatchers.isDisplayed()))
    }

}