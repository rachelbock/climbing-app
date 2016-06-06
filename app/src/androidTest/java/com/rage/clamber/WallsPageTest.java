package com.rage.clamber;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.rage.clamber.Activities.HomePage;
import com.rage.clamber.Activities.LoginActivity;
import com.rage.clamber.Data.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests walls page display, wall sections and climbs
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class WallsPageTest {

    @Rule
    public ActivityTestRule<HomePage> actionBarActivityTestRule = new ActivityTestRule<HomePage>(HomePage.class) {
        @Override
        protected Intent getActivityIntent() {
            User user = new User("testWithData", 66, 5);
            Intent intent = new Intent();
            intent.putExtra(LoginActivity.ARG_USER, user);
            return intent;
        }
    };

    @Test
    public void onWallsButtonClickedtest() {
        onView(withText("Walls")).perform(click());
        onView(withId(R.id.wall_page_wall_1_image)).check(matches(isDisplayed()));
        onView(withId(R.id.wall_page_wall_2_image)).check(matches(isDisplayed()));
        onView(withId(R.id.wall_page_wall_3_image)).check(matches(isDisplayed()));
        onView(withId(R.id.wall_page_wall_4_image)).check(matches(isDisplayed()));
    }

    @Test
    public void wallSectionsTest() {
        onView(withText("Walls")).perform(click());
        onView(withId(R.id.wall_page_wall_4_image)).perform(click());
        onView(withId(R.id.walls_page_grid_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText("pocket")).check(matches(isDisplayed()));
    }

    @Test
    public void climbDisplayTest() {
        onView(withText("Walls")).perform(click());
        onView(withId(R.id.wall_page_wall_4_image)).perform(click());
        onView(withId(R.id.walls_page_grid_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

}
