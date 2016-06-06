package com.rage.clamber;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.rage.clamber.Activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Test for logging into the application using the new user button or existing user button.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testNameExists() {
        onView(withId(R.id.login_activity_existing_user_button)).perform(click());
        onView(withId(R.id.existing_user_fragment_name_edit_text)).perform(typeText("rbock"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("THE CIRCUIT")).check(matches(isDisplayed()));
    }
    @Test
    public void testNameBlank(){
        onView(withId(R.id.login_activity_existing_user_button)).perform(click());
        onView(withId(R.id.existing_user_fragment_name_edit_text)).perform(typeText(""));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("New User?")).check(matches(isDisplayed()));
    }
    @Test
    public void testNameInvalid(){
        onView(withId(R.id.login_activity_existing_user_button)).perform(click());
        onView(withId(R.id.existing_user_fragment_name_edit_text)).perform(typeText("x"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("New User?")).check(matches(isDisplayed()));
    }

    @Test
    public void testNewUserValidData(){
        onView(withId(R.id.login_activity_new_user_button)).perform(click());
        onView(withId(R.id.user_info_dialog_fragment_name_edit_text)).perform(typeText("newTestUser1"));
        onView(withId(R.id.user_info_dialog_fragment_height_ft_edit_text)).perform(typeText("6"));
        onView(withId(R.id.user_info_dialog_fragment_height_inches_edit_text)).perform(typeText("3"));
        onView(withId(R.id.user_info_dialog_fragment_skill_edit_text)).perform(typeText("4"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("THE CIRCUIT")).check(matches(isDisplayed()));
    }

    @Test
    public void testNewUserInvalidNameData(){
        onView(withId(R.id.login_activity_new_user_button)).perform(click());
        onView(withId(R.id.user_info_dialog_fragment_name_edit_text)).perform(typeText(""));
        onView(withId(R.id.user_info_dialog_fragment_height_ft_edit_text)).perform(typeText("6"));
        onView(withId(R.id.user_info_dialog_fragment_height_inches_edit_text)).perform(typeText("3"));
        onView(withId(R.id.user_info_dialog_fragment_skill_edit_text)).perform(typeText("4"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("New User?")).check(matches(isDisplayed()));
    }


    @Test
    public void testNewUserInvalidHeightData(){
        onView(withId(R.id.login_activity_new_user_button)).perform(click());
        onView(withId(R.id.user_info_dialog_fragment_name_edit_text)).perform(typeText("newTestUser2"));
        onView(withId(R.id.user_info_dialog_fragment_height_ft_edit_text)).perform(typeText(""));
        onView(withId(R.id.user_info_dialog_fragment_height_inches_edit_text)).perform(typeText("3"));
        onView(withId(R.id.user_info_dialog_fragment_skill_edit_text)).perform(typeText("4"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("New User?")).check(matches(isDisplayed()));
    }


    @Test
    public void testNewUserInvalidSkillData(){
        onView(withId(R.id.login_activity_new_user_button)).perform(click());
        onView(withId(R.id.user_info_dialog_fragment_name_edit_text)).perform(typeText("newTestUser2"));
        onView(withId(R.id.user_info_dialog_fragment_height_ft_edit_text)).perform(typeText("6"));
        onView(withId(R.id.user_info_dialog_fragment_height_inches_edit_text)).perform(typeText("3"));
        onView(withId(R.id.user_info_dialog_fragment_skill_edit_text)).perform(typeText(""));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("New User?")).check(matches(isDisplayed()));
    }

    @Test
    public void testNewUserDuplicateName(){
        onView(withId(R.id.login_activity_new_user_button)).perform(click());
        onView(withId(R.id.user_info_dialog_fragment_name_edit_text)).perform(typeText("rbock"));
        onView(withId(R.id.user_info_dialog_fragment_height_ft_edit_text)).perform(typeText("6"));
        onView(withId(R.id.user_info_dialog_fragment_height_inches_edit_text)).perform(typeText("3"));
        onView(withId(R.id.user_info_dialog_fragment_skill_edit_text)).perform(typeText("4"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("New User?")).check(matches(isDisplayed()));
    }

}
