package info.hannes.cvscanner.sample

import android.view.Gravity
import androidx.test.core.graphics.writeToTestStorage
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.screenshot.captureToBitmap
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import info.hannes.cvscanner.DocumentScannerActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MenuTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    // a handy JUnit rule that stores the method name, so it can be used to generate unique screenshot files per test method
    @get:Rule
    var nameRule = TestName()

    @Before
    fun setup() {
        Intents.init()
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()) // Open Drawer
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun clickOnYourNavigationItem_ShowsYourScreen() {
        onView(ViewMatchers.isRoot())
            .captureToBitmap()
            .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}-start")
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_simple_scan))
        onView(ViewMatchers.isRoot())
            .captureToBitmap()
            .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}-scan")

        Intents.intended(hasComponent(DocumentScannerActivity::class.java.name))
        onView(ViewMatchers.isRoot())
            .captureToBitmap()
            .writeToTestStorage("${javaClass.simpleName}_${nameRule.methodName}-3")
    }

}