package com.rage.clamber;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests Projects Page Functionality
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProjectsPageTest {
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
    public void testProjectsDisplayed(){
        onView(withText("Projects")).perform(click());
        onView(withId(R.id.projects_fragment_no_projects_text)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withText("5")).check(matches(isDisplayed()));
    }

    @Test
    public void testRecommendationsButton(){
        onView(withText("Projects")).perform(click());
        onView(withId(R.id.projects_fragment_recommendations_fragment)).perform(click());
        onView(withText("crimp")).check(matches(isDisplayed()));
    }

}
