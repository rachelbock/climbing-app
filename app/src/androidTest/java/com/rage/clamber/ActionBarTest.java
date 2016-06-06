package com.rage.clamber;

import android.content.Intent;
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
 * Test for 4 main page buttons to make sure they open the appropriate fragments.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActionBarTest {

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
    public void testOnActionButtonsClicked(){
        onView(withText("Walls")).perform(click());
        onView(withId(R.id.wall_page_wall_1_image)).check(matches(isDisplayed()));
        onView(withText("Projects")).perform(click());
        onView(withId(R.id.projects_fragment_recommendations_fragment)).check(matches(isDisplayed()));
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_fragment_history_button)).check(matches(isDisplayed()));
        onView(withText("Home")).perform(click());
        onView(withText("THE CIRCUIT")).check(matches(isDisplayed()));
    }

}
