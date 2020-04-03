package fr.info.pl2020.activity;

import android.view.View;
import android.widget.ListView;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.InetAddress;

import fr.info.pl2020.BuildConfig;
import fr.info.pl2020.R;
import fr.info.pl2020.model.Semester;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class SemesterActivityTest {

    @Rule
    public ActivityTestRule<SemestersListActivity> mActivityTestRule = new ActivityTestRule<>(SemestersListActivity.class);

    private MockWebServer server;

    private final String defaultResponse = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"year\": 1,\n" +
            "    \"name\": \"Semestre 1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"year\": 1,\n" +
            "    \"name\": \"Semestre 2\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 3,\n" +
            "    \"year\": 2,\n" +
            "    \"name\": \"Semestre 3\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 4,\n" +
            "    \"year\": 2,\n" +
            "    \"name\": \"Semestre 4\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 5,\n" +
            "    \"year\": 3,\n" +
            "    \"name\": \"Semestre 5\"\n" +
            "  }\n" +
            "]";

    @Before
    public void setup() throws IOException {
        this.server = new MockWebServer();
        this.server.start(InetAddress.getByName(BuildConfig.SERVER_HOSTNAME), BuildConfig.SERVER_PORT);
        MockitoAnnotations.initMocks(this);
        Intents.init();
    }

    @After
    public void tearDown() throws IOException {
        this.server.close();
        Intents.release();
    }

    @Test
    public void displaySemesterList_OK() throws Exception {
        //AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        //when(authenticationManager.getToken()).thenReturn("Bearer leToken");
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(defaultResponse);

        this.server.enqueue(response);

        RecordedRequest request = this.server.takeRequest();
        assertEquals("GET /semester HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        //*
        assertEquals("Bearer", request.getHeader("Authorization"));
        /*/
        assertEquals("Bearer leToken", request.getHeader("Authorization"));
        // */

        // Le test est plus rapide que la construction de la ListView
        Thread.sleep(500);

        onView(withId(R.id.semesterListView)).check(matches(isDisplayed()));
        onView(withId(R.id.semesterListView)).check(matches(withListSize(5)));

        onData(is(instanceOf(Semester.class))).inAdapterView(withId(R.id.semesterListView)).atPosition(0).perform(click());

        intended(hasComponent(TeachingUnitListActivity.class.getName()));
    }

    @Test
    public void displaySemesterRedirectLogin() throws Exception {
        //AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        //when(authenticationManager.getToken()).thenReturn("Bearer leToken");
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(401)
                .setBody("{\"status\":401,\"error\":\"Unauthorized\"}");

        this.server.enqueue(response);

        RecordedRequest request = this.server.takeRequest();
        assertEquals("GET /semester HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        //*
        assertEquals("Bearer", request.getHeader("Authorization"));
        /*/
        assertEquals("Bearer leToken", request.getHeader("Authorization"));
        // */

        intended(hasComponent(LoginActivity.class.getName()));
    }

    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                int currentCount = ((ListView) view).getCount();
                return currentCount == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items");
            }
        };
    }

    public static Matcher<Object> withSemesterId(final int id) {
        return new BoundedMatcher<Object, Semester>(Semester.class) {
            @Override
            protected boolean matchesSafely(Semester semester) {
                return semester.getId() == id;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has value " + id);
            }
        };
    }
}
