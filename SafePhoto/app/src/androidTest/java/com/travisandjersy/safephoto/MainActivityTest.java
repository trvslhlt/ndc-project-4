package com.travisandjersy.safephoto;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("public1"),
                        childAtPosition(
                                withId(android.R.id.list),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_upload), withContentDescription("Upload"), isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_search), withContentDescription("Search"), isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("public: public2"),
                        childAtPosition(
                                withId(R.id.listview),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_signin), withContentDescription("Sign In"), isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_in_or_out_button), withText("Sign In"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.sign_in_password_field), withText("L921020zx!"),
                        withParent(withId(R.id.sign_in_input_container)),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("L921020zx!"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.sign_in_or_out_button), withText("Sign In"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.sign_in_email_field), withText("zl558@cornell.edu"),
                        withParent(withId(R.id.sign_in_input_container)),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("abc@abc.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.sign_in_password_field), withText("L921020zx!"),
                        withParent(withId(R.id.sign_in_input_container)),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("jjj"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.signup_button), withText("Sign Up"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.sign_in_password_field), withText("jjj"),
                        withParent(withId(R.id.sign_in_input_container)),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.sign_in_password_field), withText("jjj"),
                        withParent(withId(R.id.sign_in_input_container)),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("badpassword"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.signup_button), withText("Sign Up"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.navigation_photos), withContentDescription("Public Photos"), isDisplayed()));
        bottomNavigationItemView4.perform(click());

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.navigation_private_photos), withContentDescription("Private Photos"), isDisplayed()));
        bottomNavigationItemView5.perform(click());

        ViewInteraction bottomNavigationItemView6 = onView(
                allOf(withId(R.id.navigation_upload), withContentDescription("Upload"), isDisplayed()));
        bottomNavigationItemView6.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.take_picture_button), withText("Take Picture"), isDisplayed()));
        appCompatButton5.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3395415);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.upload_image_button), withText("Upload Image"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radio_private), withText("Private"),
                        withParent(withId(R.id.radio_group)),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.upload_description_field), isDisplayed()));
        appCompatEditText6.perform(replaceText("privat"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.upload_description_field), withText("privat"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.commit_upload_button), withText("Commit Upload"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction bottomNavigationItemView7 = onView(
                allOf(withId(R.id.navigation_private_photos), withContentDescription("Private Photos"), isDisplayed()));
        bottomNavigationItemView7.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("privat"),
                        childAtPosition(
                                withId(android.R.id.list),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction bottomNavigationItemView8 = onView(
                allOf(withId(R.id.navigation_signin), withContentDescription("Sign In"), isDisplayed()));
        bottomNavigationItemView8.perform(click());

        ViewInteraction bottomNavigationItemView9 = onView(
                allOf(withId(R.id.navigation_search), withContentDescription("Search"), isDisplayed()));
        bottomNavigationItemView9.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        searchAutoComplete2.perform(replaceText("pr"), closeSoftKeyboard());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("private: privat"),
                        childAtPosition(
                                withId(R.id.listview),
                                0),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction bottomNavigationItemView10 = onView(
                allOf(withId(R.id.navigation_signin), withContentDescription("Sign In"), isDisplayed()));
        bottomNavigationItemView10.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.sign_in_or_out_button), withText("Sign Out"), isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.google_sign_in_button), withText("Google Sign In"), isDisplayed()));
        appCompatButton9.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3543841);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction tj = onView(
                allOf(withText("Sign in"),
                        withParent(withId(R.id.sign_in_button)),
                        isDisplayed()));
        tj.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3596930);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.google_sign_in_button), withText("Google Sign In"), isDisplayed()));
        appCompatButton10.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3593524);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction tj2 = onView(
                allOf(withText("Sign in"),
                        withParent(withId(R.id.sign_in_button)),
                        isDisplayed()));
        tj2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3598031);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.google_sign_in_button), withText("Google Sign In"), isDisplayed()));
        appCompatButton11.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3591479);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction tj3 = onView(
                allOf(withText("Sign in"),
                        withParent(withId(R.id.sign_in_button)),
                        isDisplayed()));
        tj3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3596982);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView11 = onView(
                allOf(withId(R.id.navigation_photos), withContentDescription("Public Photos"), isDisplayed()));
        bottomNavigationItemView11.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
