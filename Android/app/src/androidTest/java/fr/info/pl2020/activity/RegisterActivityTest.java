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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Intents.init();
    }

    @After
    public void tearDown() {
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
        onView(withId(R.id.signUpButton)).check(matches(isDisplayed()));
        onView(withId(R.id.already_registered)).check(matches(isDisplayed()));
        onView(withId(R.id.signin)).check(matches(isDisplayed()));

        //est-ce que ce qui est tapé a le format attendu
        onView(withId(R.id.lastNameRegister)).check(matches(withHint(R.string.last_name)));
        onView(withId(R.id.nameRegister)).check(matches(withHint(R.string.name)));
        onView(withId(R.id.emailRegister)).check(matches(withHint(R.string.email)));
        onView(withId(R.id.passwordRegister)).check(matches(withHint(R.string.password)));
        onView(withId(R.id.confirmPasswordRegister)).check(matches(withHint(R.string.confirm_password)));
    }

    //-----------------------LAST_NAME-----------------------\\
    @Test
    public void registerExecute_EmptyLastName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_lastname_missing_error)));
    }

    @Test
    public void registerExecute_BadLastName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("a"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_lastname_length_error)));
    }

    //-----------------------NAME-----------------------\\
    @Test
    public void registerExecute_EmptyName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_name_missing_error)));
    }

    @Test
    public void registerExecute_BadName() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("a"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_name_length_error)));
    }


    //-----------------------EMAIL-----------------------\\
    @Test
    public void registerExecute_EmptyEmail() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_email_missing_error)));
    }

    @Test
    public void registerExecute_BadEmail() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("totogmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_bad_email_error)));
    }


    //-----------------------PASSWORD-----------------------\\
    @Test
    public void registerExecute_EmptyPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_password_missing_error)));
    }

    @Test
    public void registerExecute_BadPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_password_length_error)));
    }


    //-----------------------CONFIRM_PASSWORD-----------------------\\
    @Test
    public void registerExecute_EmptyConfirmPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_confirm_password_missing_error)));
    }

    @Test
    public void registerExecute_BadConfirmPassword() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.registerErrorTextView)).check(matches(withText(R.string.register_confirm_password_length_error)));
    }

    @Test
    public void registerExecute_ConfirmPasswordNotMatching() {
        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.lastNameRegister)).perform(replaceText("TITI"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("123456"));
        onView(withId(R.id.signUpButton)).perform(click());
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
                .setResponseCode(201)
                .setBody("{}");

        mockServer.enqueue(response);
        mockServer.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);

        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.lastNameRegister)).perform(replaceText("tata"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST /register HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("{\"firstname\":\"toto\",\"lastname\":\"tata\",\"email\":\"toto@gmail.com\",\"password\":\"12345\"}", request.getBody().readUtf8());
        Thread.sleep(3000);
        assertTrue(registerActivityTestRule.getActivity().isDestroyed());
    }

    @Test
    public void registerExecute_BadCredentials() throws Exception {
        MockWebServer mockServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(409)
                .setBody("{\"error\":\"Unauthorized\"}");

        mockServer.enqueue(response);
        mockServer.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);

        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.lastNameRegister)).perform(replaceText("tata"));
        onView(withId(R.id.nameRegister)).perform(replaceText("toto"));
        onView(withId(R.id.emailRegister)).perform(replaceText("toto@gmail.com"));
        onView(withId(R.id.passwordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.confirmPasswordRegister)).perform(replaceText("12345"));
        onView(withId(R.id.signUpButton)).perform(click());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST /register HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("{\"firstname\":\"toto\",\"lastname\":\"tata\",\"email\":\"toto@gmail.com\",\"password\":\"12345\"}", request.getBody().readUtf8());

        onView(withId(R.id.registerErrorTextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        mockServer.close();
    }

    @Test
    public void registerExecute() throws Exception {
        onView(withId(R.id.signin)).perform(click());
        Thread.sleep(2000);
        assertTrue(registerActivityTestRule.getActivity().isDestroyed());
    }

}
