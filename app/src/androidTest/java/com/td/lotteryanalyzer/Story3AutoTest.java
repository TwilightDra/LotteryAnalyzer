package com.td.lotteryanalyzer;

import android.support.test.rule.ActivityTestRule;

import com.td.lotteryanalyzer.core.LottoAnalyzer;
import android.app.Instrumentation;
import android.content.Intent;
import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;
@RunWith(AndroidJUnit4.class)
public class Story3AutoTest {
    @Rule
    public ActivityTestRule<LottoAnalyzer> mActivityRule =
            new ActivityTestRule<>(LottoAnalyzer.class);
    @Test
    public void ensureStory3(){
        //Max
        onView(withId(R.id.txt_w1)).perform(typeText("04"), closeSoftKeyboard());
        onView(withId(R.id.txt_w2)).perform(typeText("06"), closeSoftKeyboard());
        onView(withId(R.id.txt_w3)).perform(typeText("25"), closeSoftKeyboard());
        onView(withId(R.id.txt_w4)).perform(typeText("33"), closeSoftKeyboard());
        onView(withId(R.id.txt_w5)).perform(typeText("38"), closeSoftKeyboard());
        onView(withId(R.id.txt_w6)).perform(typeText("44"), closeSoftKeyboard());
        onView(withId(R.id.txt_w7)).perform(typeText("47"), closeSoftKeyboard());
        onView(withId(R.id.btn_winSearch)).perform(click());
        //onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.txt_num1)).check(matches(withText(containsString("33"))));
        /*onView(withId(R.id.txt_n1)).check(matches(withText("03")));
        onView(withId(R.id.txt_n2)).check(matches(withText("15")));
        onView(withId(R.id.txt_nb)).check(matches(withText("06")));*/
        //649

        //onView(withId(R.id.textConditionDisp)).perform(typeText("2017-10-31"), closeSoftKeyboard());
        //onView(withId(R.id.btn_search)).perform(click());
        //onView(withId(R.id.imageView1)).check((R.id.imageView1)!=null)
    }
}
