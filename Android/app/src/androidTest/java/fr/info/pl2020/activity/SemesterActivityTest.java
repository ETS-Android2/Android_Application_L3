package fr.info.pl2020.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.info.pl2020.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
@Ignore
public class SemesterActivityTest {
    /**
     * Lancez le server pour faire fonctionner l'ensemble des tests.
     */

    @Rule
    public ActivityTestRule<SemestersListActivity> mActivityTestRule = new ActivityTestRule<>(SemestersListActivity.class);


    /**
     * Permet de tester si on a bien la liste des semestres.
     */
    @Test
    public void semestersExists() {
        onView(withId(R.id.ListView)).check(matches(isDisplayed()));
    }

    /**
     * Permet de tester si on a bien lesemestre  1.
     */
    @Test
    public void semester_1ActivityTest() {

        ViewInteraction textView = onView(
                allOf(withId(R.id.semester), withText("Semestre n°1"),
                        positionFilsMatcher(
                                positionFilsMatcher(
                                        withId(R.id.ListView),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    /**
     * Permet de tester si on a bien lesemestre 2.
     */
    @Test
    public void semester_2ActivityTest() {

        ViewInteraction textView = onView(
                allOf(withId(R.id.semester), withText("Semestre n°2"),
                        positionFilsMatcher(
                                positionFilsMatcher(
                                        withId(R.id.ListView),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void semester_3ActivityTest() {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.semester), withText("Semestre n°3"),
                        positionFilsMatcher(
                                positionFilsMatcher(
                                        withId(R.id.ListView),
                                        2),
                                0),
                        isDisplayed()));
        textView2.check(

                matches(isDisplayed()));
    }

    @Test
    public void semester_4ActivityTest() {
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.semester), withText("Semestre n°4"),
                        positionFilsMatcher(
                                positionFilsMatcher(
                                        withId(R.id.ListView),
                                        3),
                                0),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));
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
