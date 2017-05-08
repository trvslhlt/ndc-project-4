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
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("public3"),
                        childAtPosition(
                                withId(android.R.id.list),
                                2),
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
        searchAutoComplete.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("public: public3"),
                        childAtPosition(
                                withId(R.id.listview),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_signin), withContentDescription("Sign In"), isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.google_sign_in_button), withText("Google Sign In"), isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3568374);
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
            Thread.sleep(3597742);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.navigation_photos), withContentDescription("Public Photos"), isDisplayed()));
        bottomNavigationItemView4.perform(click());

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.navigation_private_photos), withContentDescription("Private Photos"), isDisplayed()));
        bottomNavigationItemView5.perform(click());

        ViewInteraction bottomNavigationItemView6 = onView(
                allOf(withId(R.id.navigation_upload), withContentDescription("Upload"), isDisplayed()));
        bottomNavigationItemView6.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.take_picture_button), withText("Take Picture"), isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3590865);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.upload_image_button), withText("Upload Image"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radio_private), withText("Private"),
                        withParent(withId(R.id.radio_group)),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.upload_description_field), isDisplayed()));
        appCompatEditText.perform(replaceText("jj"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.upload_description_field), withText("jj"), isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.commit_upload_button), withText("Commit Upload"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.take_picture_button), withText("Take Picture"), isDisplayed()));
        appCompatButton5.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3581736);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.upload_image_button), withText("Upload Image"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.radio_public), withText("Public"),
                        withParent(withId(R.id.radio_group)),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.upload_description_field), isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.upload_description_field), isDisplayed()));
        appCompatEditText4.perform(replaceText("zz"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.upload_description_field), withText("zz"), isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.commit_upload_button), withText("Commit Upload"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction bottomNavigationItemView7 = onView(
                allOf(withId(R.id.navigation_private_photos), withContentDescription("Private Photos"), isDisplayed()));
        bottomNavigationItemView7.perform(click());

        ViewInteraction bottomNavigationItemView8 = onView(
                allOf(withId(R.id.navigation_photos), withContentDescription("Public Photos"), isDisplayed()));
        bottomNavigationItemView8.perform(click());

        ViewInteraction bottomNavigationItemView9 = onView(
                allOf(withId(R.id.navigation_private_photos), withContentDescription("Private Photos"), isDisplayed()));
        bottomNavigationItemView9.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("zz"),
                        childAtPosition(
                                withId(android.R.id.list),
                                1),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction bottomNavigationItemView10 = onView(
                allOf(withId(R.id.navigation_upload), withContentDescription("Upload"), isDisplayed()));
        bottomNavigationItemView10.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.take_picture_button), withText("Take Picture"), isDisplayed()));
        appCompatButton8.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3572893);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.upload_image_button), withText("Upload Image"), isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.radio_public), withText("Public"),
                        withParent(withId(R.id.radio_group)),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.upload_description_field), isDisplayed()));
        appCompatEditText6.perform(replaceText("ff"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.upload_description_field), withText("ff"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.commit_upload_button), withText("Commit Upload"), isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction bottomNavigationItemView11 = onView(
                allOf(withId(R.id.navigation_private_photos), withContentDescription("Private Photos"), isDisplayed()));
        bottomNavigationItemView11.perform(click());

        ViewInteraction bottomNavigationItemView12 = onView(
                allOf(withId(R.id.navigation_photos), withContentDescription("Public Photos"), isDisplayed()));
        bottomNavigationItemView12.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("ff"),
                        childAtPosition(
                                withId(android.R.id.list),
                                3),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction bottomNavigationItemView13 = onView(
                allOf(withId(R.id.navigation_search), withContentDescription("Search"), isDisplayed()));
        bottomNavigationItemView13.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        searchAutoComplete2.perform(replaceText("f"), closeSoftKeyboard());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("public: ff"),
                        childAtPosition(
                                withId(R.id.listview),
                                0),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageView")), withContentDescription("Clear query"),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete3 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        searchAutoComplete3.perform(replaceText("z"), closeSoftKeyboard());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.text1), withText("private: zz"),
                        childAtPosition(
                                withId(R.id.listview),
                                0),
                        isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageView")), withContentDescription("Clear query"),
                        withParent(allOf(withClassName(is("android.widget.LinearLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction bottomNavigationItemView14 = onView(
                allOf(withId(R.id.navigation_signin), withContentDescription("Sign In"), isDisplayed()));
        bottomNavigationItemView14.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.sign_in_or_out_button), withText("Sign Out"), isDisplayed()));
        appCompatButton11.perform(click());

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
