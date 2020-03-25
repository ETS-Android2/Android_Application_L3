package fr.info.pl2020.activity;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.InetAddress;

import fr.info.pl2020.BuildConfig;
import fr.info.pl2020.R;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
@Ignore
public class SemesterActivityTest {
    /**
     * Lancez le server pour faire fonctionner l'ensemble des tests.
     */

    @Rule
    public ActivityTestRule<SemestersListActivity> mActivityTestRule = new ActivityTestRule<>(SemestersListActivity.class);

    private MockWebServer server;

    @Before
    public void setup() throws IOException {
        this.server = new MockWebServer();
        this.server.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);
    }

    @After
    public void tearDown() throws IOException {
        this.server.close();
    }

    @Test
    public void displaySemesterList_OK() {
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody("{\"token\":\"leToken\"}");
    }


    /**
     * Permet de tester si on a bien la liste des semestres.
     */
    @Test
    public void semestersExists() {
        onView(withId(R.id.ListView)).check(matches(isDisplayed()));
    }


    /**
     * Creation d'un Matcher qui prend en parametre la view parent et la position du fils qu'on souhaite avoir.
     * Elle retourne la view du fils .
     */
    private static Matcher<View> positionFilsMatcher(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
