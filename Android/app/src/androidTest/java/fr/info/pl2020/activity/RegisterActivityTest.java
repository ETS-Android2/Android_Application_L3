package fr.info.pl2020.activity;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

///////////////////////////////////////---------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
///////////////////////////////////////EN CONSTRUCTION\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
///////////////////////////////////////----------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\



public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> loginActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        Intents.init();
    }

    @After
    public void tearDown() throws IOException {
        Intents.release();
    }

    /**
     * Permet de tester si les champs Input et le bouton "s'enregister" existent
     */
    @Test
    public void credentialsInputExists() {

        //est-ce qu'ils sont bien affichés
        onView(withId(R.id.lastNameRegister)).check(matches(isDisplayed()));
        onView(withId(R.id.nameRegister)).check(matches(isDisplayed()));
        onView(withId(R.id.emailRegister)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordRegister)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPasswordRegister)).check(matches(isDisplayed()));
        onView(withId(R.id.registerButton)).check(matches(isDisplayed()));
        onView(withId(R.id.already_memberButton)).check(matches(isDisplayed()));
        onView(withId(R.id.signinButton)).check(matches(isDisplayed()));

        //est-ce que ce qui est tapé a le format attendu
        onView(withId(R.id.lastNameRegister)).check(matches(withHint(R.string.last_name)));
        onView(withId(R.id.nameRegister)).check(matches(withHint(R.string.name)));
        onView(withId(R.id.emailRegister)).check(matches(withHint(R.string.email)));
        onView(withId(R.id.passwordRegister)).check(matches(withHint(R.string.password)));
        onView(withId(R.id.confirmPasswordRegister)).check(matches(withHint(R.string.confirm_password)));
    }

    //-----------------------LAST_NAME-----------------------\\
    @Test
    public void registerButtonExecute_EmptyLastName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_lastname_missing_error)));
    }
    @Test
    public void registerButtonExecute_BadLastName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("a"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_lastname_length_error)));
    }

    //-----------------------NAME-----------------------\\
    @Test
    public void registerButtonExecute_EmptyName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_name_missing_error)));
    }
    @Test
    public void registerButtonExecute_BadName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.nameRegister)).perform(replaceText("a"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_name_length_error)));
    }


    //-----------------------EMAIL-----------------------\\
    @Test
    public void registerButtonExecute_EmptyEmail() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_email_missing_error)));
    }
    @Test
    public void registerButtonExecute_BadEmail() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.emailInput)).perform(replaceText("totogmail.com"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_bad_email_error)));
    }


    //-----------------------PASSWORD-----------------------\\
    @Test
    public void registerButtonExecute_EmptyPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_email_missing_error)));
    }
    @Test
    public void registerButtonExecute_BadPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.emailInput)).perform(replaceText("totogmail.com"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_bad_email_error)));
    }


    //-----------------------CONFIRM_PASSWORD-----------------------\\
    @Test
    public void registerButtonExecute_EmptyConfirmPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_confirm_mail_missing_error)));
    }
    @Test
    public void registerButtonExecute_BadConfirmPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.emailInput)).perform(replaceText("totogmail.com"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_not_matching_passwords_error)));
    }


    /**
     * Permet de tester si le bouton "s'enregistrer" nous renvoie à
     * l'activité login
     */

    @Test
    public void registerExecute_Ok() throws Exception {
        MockWebServer mockServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody("{\"token\":\"leToken\"}");

        mockServer.enqueue(response);
        mockServer.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);

        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.lastNameRegister)).perform(replaceText("tata"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.registerButton)).perform(click());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST /register HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("{\"lastNameRegister\":\"tata\",\"nameRegister\":\"toto\",\"emailRegister\":\"toto@gmail.com\",\"passwordRegister\":\"12345\",\"confirmPasswordRegister\":\"12345\"}", request.getBody().readUtf8());
        intended(hasComponent(LoginActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void registerExecute_BadCredentials() throws Exception {
        MockWebServer mockServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(401)
                .setBody("{\"error\":\"Unauthorized\"}");

        mockServer.enqueue(response);
        mockServer.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);

        onView(withId(R.id.loginErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.lastNameRegister)).perform(replaceText("tata"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.registerButton)).perform(click());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST /register HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("{\"lastNameRegister\":\"tata\",\"nameRegister\":\"toto\",\"emailRegister\":\"toto@gmail.com\",\"passwordRegister\":\"12345\",\"confirmPasswordRegister\":\"12345\"}", request.getBody().readUtf8());

        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.bad_credentials)));

        mockServer.close();
    }

    @Test
    public void signinExecute() throws Exception {
        onView(withId(R.id.signinButton)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }



















}
