package fr.info.pl2020.activity;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.info.pl2020.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void logoExists() {
        onView(withId(R.id.logo)).check(matches(isDisplayed()));
    }

    @Test
    public void loginButtonExists() {
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    @Test
    public void loginButtonExecute() {
        Intents.init();
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(SemestersListActivity.class.getName()));
    }
}
