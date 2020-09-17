package com.nino.ibmsicred;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.nino.ibmsicred.views.EventsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EventsActivityTest {
    private String stringToBetyped;

    @Rule
    public ActivityTestRule<EventsActivity> activityRule = new ActivityTestRule<>(EventsActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        //onView(withId(R.id.editTextUserInput)).perform(typeText(stringToBetyped), closeSoftKeyboard());
        //onView(withId(R.id.changeTextBt)).perform(click());

        // Check that the text was changed.
        //onView(withId(R.id.textToBeChanged)).check(matches(withText(stringToBetyped)));
    }
}