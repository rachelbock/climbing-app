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
 * Test for projects and completed with user with no data
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserNoDataTest {

    @Rule
    public ActivityTestRule<HomePage> actionBarActivityTestRule = new ActivityTestRule<HomePage>(HomePage.class) {
        @Override
        protected Intent getActivityIntent() {
            User user = new User("testNoData", 66, 5);
            Intent intent = new Intent();
            intent.putExtra(LoginActivity.ARG_USER, user);
            return intent;
        }
    };

    @Test
    public void testNoHistoryData(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_fragment_history_button)).perform(click());
        onView(withText(R.string.no_completed_history_yet)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoProjectData(){
        onView(withText("Projects")).perform(click());
        onView(withText(R.string.you_have_not_marked_any_climbs_as_projects_yet_click_on_recommendations_if_you_need_any_suggestions)).check(matches(isDisplayed()));
    }

}
