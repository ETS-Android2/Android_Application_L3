package fr.info.pl2020.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
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
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class TeachingUnitListActivityTest {

    private MockWebServer server;

    @Rule
    public ActivityTestRule<TeachingUnitListActivity> mActivityRule =
            new ActivityTestRule<TeachingUnitListActivity>(TeachingUnitListActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, TeachingUnitListActivity.class);
                    result.putExtra(TeachingUnitListActivity.ARG_SEMESTER_ID, 1);
                    return result;
                }
            };

    private final String defaultTeachingUnitResponse = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Structure Microscopique de la Matière\",\n" +
            "    \"code\": \"SPUC10\",\n" +
            "    \"description\": \"Atome, ion, molécule, gaz, solide sont les constituants et les états de la matière que nous découvrons dans ce module.\",\n" +
            "    \"semester\": 1,\n" +
            "    \"category\": \"Chimie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 11,\n" +
            "    \"name\": \"Electronique Numérique - Bases\",\n" +
            "    \"code\": \"SPUE10\",\n" +
            "    \"description\": \"Ce cours d’introduction à l’électronique numérique est le premier d’une série de trois dédiés à l’apprentissage de la conception de circuits numériques.\",\n" +
            "    \"semester\": 1,\n" +
            "    \"category\": \"Electronique\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 21,\n" +
            "    \"name\": \"Decouverte I\",\n" +
            "    \"code\": \"SPUGDE10\",\n" +
            "    \"description\": \"Cette UE aborde l'histoire des statistiques, en présente les sources, puis elle souligne l'enjeu de la production, et de la critique de données pour le géographe. \",\n" +
            "    \"semester\": 1,\n" +
            "    \"category\": \"Géographie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 22,\n" +
            "    \"name\": \"Decouverte II\",\n" +
            "    \"code\": \"SPUGDC10\",\n" +
            "    \"description\": \"Cette UE approfondit les connaissances traitées en Decouverte I\",\n" +
            "    \"semester\": 1,\n" +
            "    \"category\": \"Géographie\"\n" +
            "  }]";

    private final String defaultCareerResponse = "[\n" +
            "  {\n" +
            "    \"id\": 21,\n" +
            "    \"name\": \"Decouverte I\",\n" +
            "    \"code\": \"SPUGDE10\",\n" +
            "    \"description\": \"Cette UE aborde l'histoire des statistiques, en présente les sources, puis elle souligne l'enjeu de la production, et de la critique de données pour le géographe. \",\n" +
            "    \"semester\": 1,\n" +
            "    \"category\": \"Géographie\"\n" +
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
    public void updateTeachingUnits_OK() throws Exception {
        MockResponse response1 = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(defaultTeachingUnitResponse);

        MockResponse response2 = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(defaultCareerResponse);

        this.server.enqueue(response1);
        this.server.enqueue(response2);

        // getTeachingUnit
        RecordedRequest request1 = this.server.takeRequest();
        assertEquals("GET /teachingUnit?semester=1 HTTP/1.1", request1.getRequestLine());
        assertEquals("application/json", request1.getHeader("Content-Type"));
        assertEquals("Bearer", request1.getHeader("Authorization"));

        // getCareer
        RecordedRequest request2 = this.server.takeRequest();
        assertEquals("GET /career/main?semester=1 HTTP/1.1", request2.getRequestLine());
        assertEquals("application/json", request2.getHeader("Content-Type"));
        assertEquals("Bearer", request2.getHeader("Authorization"));

        // Le test est plus rapide que la construction de la ListView
        Thread.sleep(500);

        onView(withId(R.id.teachingunit_list)).check(matches(isDisplayed()));
        onView(withId(R.id.teachingunit_list)).check(matches(withExpandableListSize(3)));
        onView(withId(R.id.teachingunit_list)).check(matches(withExpandableListChildrenSize(0, 1)));
        onView(withId(R.id.teachingunit_list)).check(matches(withExpandableListChildrenSize(2, 2)));

        // TODO tester le perform click pour vérifier la redirection vers TeachinUnitDetailActivity
    }

    @Test
    public void updateTeachingUnitsRedirectLogin() throws Exception {
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(401)
                .setBody("{\"status\":401,\"error\":\"Unauthorized\"}");

        this.server.enqueue(response);

        RecordedRequest request = this.server.takeRequest();
        assertEquals("GET /teachingUnit?semester=1 HTTP/1.1", request.getRequestLine());
        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("Bearer", request.getHeader("Authorization"));

        intended(hasComponent(LoginActivity.class.getName()));
    }

    public static Matcher<View> withExpandableListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                int currentCount = ((ExpandableListView) view).getExpandableListAdapter().getGroupCount();
                return currentCount == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ExpandableListView should have " + size + " items");
            }
        };
    }

    public static Matcher<Object> withChildName(final Matcher<String> name) {
        checkNotNull(name);
        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            protected boolean matchesSafely(String category) {
                return name.matches(category);
            }

            @Override
            public void describeTo(Description description) {
                name.describeTo(description);
            }
        };
    }

    public static Matcher<View> withExpandableListChildrenSize(final int group, final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                int currentCount = ((ExpandableListView) view).getExpandableListAdapter().getChildrenCount(group);
                return currentCount == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ExpandableListView should have " + size + " items");
            }
        };
    }

}
