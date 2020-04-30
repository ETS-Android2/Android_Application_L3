package fr.info.pl2020.activity;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import fr.info.pl2020.R;
import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.store.TeachingUnitListStore;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class TeachingUnitDetailsActivityTest {

    private TeachingUnit tu = new TeachingUnit(1, "Projet de Licence", "SLZI64", "La description", 6, "Informatique");

    @Rule
    public ActivityTestRule<TeachingUnitDetailActivity> mActivityRule =
            new ActivityTestRule<TeachingUnitDetailActivity>(TeachingUnitDetailActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TeachingUnitListStore.addItem(tu);
                    super.beforeActivityLaunched();
                }

                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, TeachingUnitDetailActivity.class);
                    result.putExtra(TeachingUnitDetailFragment.ARG_ITEM_ID, 1);
                    return result;
                }
            };


    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void displayTeachingUnit() {
        onView(withId(R.id.teaching_unit_title)).check(matches(isDisplayed()));
        onView(withId(R.id.teaching_unit_title)).check(matches(withText(tu.getName())));

        onView(withId(R.id.teaching_unit_code)).check(matches(isDisplayed()));
        onView(withId(R.id.teaching_unit_code)).check(matches(withText(tu.getCode())));

        onView(withId(R.id.teaching_unit_description)).check(matches(isDisplayed()));
        onView(withId(R.id.teaching_unit_description)).check(matches(withText(tu.getDescription())));

        onView(withId(R.id.button)).check(matches(isDisplayed()));

        onView(withId(R.id.button2)).check(matches(isDisplayed()));
    }
}
