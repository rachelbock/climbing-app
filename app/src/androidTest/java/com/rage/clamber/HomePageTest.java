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
 * Test that home page is displayed with new walls
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomePageTest {

    @Rule
    public ActivityTestRule<HomePage> actionBarActivityTestRule = new ActivityTestRule<HomePage>(HomePage.class) {
        @Override
        protected Intent getActivityIntent() {
            User user = new User("testWithData", 5, 6, 5);
            Intent intent = new Intent();
            intent.putExtra(LoginActivity.ARG_USER, user);
            return intent;
        }
    };

    @Test
    public void newWallTest() {
        onView(withText("Walls")).perform(click());
        onView(withText("Home")).perform(click());
        onView(withText("THE CIRCUIT")).check(matches(isDisplayed()));
        onView(withId(R.id.home_page_image_1_grid_view)).perform(click());
        onView(withId(R.id.climbs_fragment_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyRecyclerViewAction.clickChildViewWithId(R.id.climb_row_comments_button)));
        onView(withId(R.id.comments_fragment_gym_rating_text_view)).check(matches(isDisplayed()));

    }
}
