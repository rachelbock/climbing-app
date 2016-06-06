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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test for User Info page - checks update user information and history display.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserInfoPageTest {

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
    public void testUserInfoDisplay(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_info_fragment_about_me_button)).check(matches(isDisplayed()));
        onView(withText("testWithData")).check(matches(isDisplayed()));
        onView(withText("5' 6''")).check(matches(isDisplayed()));
        onView(withText("5")).check(matches(isDisplayed()));
    }
    @Test
    public void testUpdateUserValidData(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_info_fragment_about_me_button)).perform(click());
        onView(withId(R.id.update_user_dialog_fragment_height_ft_edit_text)).perform(typeText("6"));
        onView(withId(R.id.update_user_dialog_fragment_height_inches_edit_text)).perform(typeText("4"));
        onView(withId(R.id.update_user_dialog_fragment_skill_edit_text)).perform(typeText("B"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("testWithData")).check(matches(isDisplayed()));
        onView(withText("6' 4''")).check(matches(isDisplayed()));
        onView(withText("B")).check(matches(isDisplayed()));
        //reset rbock user in database
        onView(withId(R.id.user_info_fragment_about_me_button)).perform(click());
        onView(withId(R.id.update_user_dialog_fragment_height_ft_edit_text)).perform(typeText("5"));
        onView(withId(R.id.update_user_dialog_fragment_height_inches_edit_text)).perform(typeText("6"));
        onView(withId(R.id.update_user_dialog_fragment_skill_edit_text)).perform(typeText("5"));
        onView(withId(android.R.id.button1)).perform(click());
    }

    @Test
    public void testUpdateUserInvalidData(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_info_fragment_about_me_button)).perform(click());
        onView(withId(R.id.update_user_dialog_fragment_height_ft_edit_text)).perform(typeText("9"));
        onView(withId(R.id.update_user_dialog_fragment_height_inches_edit_text)).perform(typeText("30"));
        onView(withId(R.id.update_user_dialog_fragment_skill_edit_text)).perform(typeText("B"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("testWithData")).check(matches(isDisplayed()));
        onView(withText("5' 6''")).check(matches(isDisplayed()));
        onView(withText("5")).check(matches(isDisplayed()));
    }
    @Test
    public void testUpdateUserBlankData(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_info_fragment_about_me_button)).perform(click());
        onView(withId(R.id.update_user_dialog_fragment_height_ft_edit_text)).perform(typeText(""));
        onView(withId(R.id.update_user_dialog_fragment_height_inches_edit_text)).perform(typeText(""));
        onView(withId(R.id.update_user_dialog_fragment_skill_edit_text)).perform(typeText(""));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("testWithData")).check(matches(isDisplayed()));
        onView(withText("5' 6''")).check(matches(isDisplayed()));
        onView(withText("5")).check(matches(isDisplayed()));
    }

    @Test
    public void testOnPageLaunchedNoHistory(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_activity_recycler_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void testOnViewHistoryClicked(){
        onView(withText("User Info")).perform(click());
        onView(withId(R.id.user_fragment_history_button)).perform(click());
        onView(withId(R.id.user_activity_recycler_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withText("traverse")).check(matches(isDisplayed()));
    }

}
