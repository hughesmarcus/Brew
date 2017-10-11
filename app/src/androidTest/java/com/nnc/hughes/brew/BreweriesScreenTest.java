package com.nnc.hughes.brew;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nnc.hughes.brew.ui.list.BreweriesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Marcus on 10/11/2017.
 */
@RunWith(AndroidJUnit4.class)
public class BreweriesScreenTest {


    @Rule
    public ActivityTestRule<BreweriesActivity> breweriesActivityTestRule =
            new ActivityTestRule<>(BreweriesActivity.class);

    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void setUp() throws Exception {
        IdlingRegistry.getInstance().register(
                breweriesActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void tearDown() throws Exception {
        IdlingRegistry.getInstance().unregister(
                breweriesActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void markTaskAsActive() {

        // onView(withRecyclerView(R.id.items_rv).atPosition(3))
        //       .check(matches(hasDescendant(withText("Some content"))));

        // onView(withRecyclerView(R.id.items_rv).atPosition(3)).perform(click());
        onView(withId(R.id.brewery_rv)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.brewery_rv)).perform(actionOnItemAtPosition(2, click()));
    }
}
