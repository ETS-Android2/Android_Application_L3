package fr.info.pl2020.activity;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.InetAddress;

import fr.info.pl2020.BuildConfig;
import fr.info.pl2020.R;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Permet de tester si les champs Input et le bouton "Se connecter" existent
     */
    @Test
    public void credentialsInputExists() {
        onView(withId(R.id.emailInput)).check(matches(isDisplayed()));
        onView(withId(R.id.emailInput)).check(matches(withHint(R.string.email)));
        onView(withId(R.id.passwordInput)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordInput)).check(matches(withHint(R.string.password)));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    /**
     * Permet de tester si le bouton "login" nous renvoie à
     * une autre activity qui contient la liste des semestres
     */
    @Test
    public void loginButtonExecute_EmptyEmail() {
        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void loginButtonExecute_BadEmail() {
        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void loginExecute_Ok() throws Exception {
        MockWebServer mockServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody("{\"token\":\"leToken\"}");

        mockServer.enqueue(response);
        mockServer.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);

        Intents.init();
        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.emailInput)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordInput)).perform(replaceText("12345"));
        onView(withId(R.id.loginButton)).perform(click());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST /login HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("{\"email\":\"toto@gmail.com\",\"password\":\"12345\"}", request.getBody().readUtf8());
        intended(hasComponent(SemestersListActivity.class.getName()));

        mockServer.close();
        Intents.release();
    }

    @Test
    public void loginExecute_BadCredentials() throws Exception {
        MockWebServer mockServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(401)
                .setBody("{\"error\":\"Unauthorized\"}");

        mockServer.enqueue(response);
        mockServer.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);

        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.emailInput)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordInput)).perform(replaceText("1234"));
        onView(withId(R.id.loginButton)).perform(click());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST /login HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("{\"email\":\"toto@gmail.com\",\"password\":\"1234\"}", request.getBody().readUtf8());

        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.loginErrorTextView)).check(matches(withText(R.string.bad_credentials)));

        mockServer.close();
    }
}
