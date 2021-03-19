package com.faanggang.wisetrack.publishTests;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.publish.PublishExperiment1_Initialization;
import com.faanggang.wisetrack.publish.PublishExperiment3_Geolocation;
import com.faanggang.wisetrack.publish.PublishExperiment4_Complete;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class for PublishExperiment3_Geolocation.
 * Given that PublishInitializationTest tests all passed...
 * 1. Check to see if the buttons on PublishExperiment3_Geolocation.class take us to the next
 * activity PublishExperiment4_Complete.class.
 * 2. Check the text description to see if the string contains the correct geolocation boolean
 * (ie, clicking "yes" will make the description contain "Geolocation required: true".
 * Clicking "no" will make the description contain "Geolocation required: false").
 */

public class PublishGeolocationTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<PublishExperiment1_Initialization> rule =
            new ActivityTestRule<>(PublishExperiment1_Initialization.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start(){
        Activity activity = rule.getActivity();
    }

    public void initialize(){
        solo.assertCurrentActivity("wrong Activity", PublishExperiment1_Initialization.class);

        solo.enterText((EditText) solo.getView(R.id.name_input), "Coin");
        solo.enterText((EditText) solo.getView(R.id.description_input),
                "Coin test description.\nThis is my experiment!");
        solo.enterText((EditText) solo.getView(R.id.region_input), "Canada");
        solo.enterText((EditText) solo.getView(R.id.minTrials_input), "55");

        solo.clickOnView(solo.getView(R.id.choose_test_type_button));

        solo.clickOnView(solo.getView(R.id.counts_button));
    }

    @Test
    public void checkYesButton() {
        initialize();
        solo.assertCurrentActivity("wrong Activity", PublishExperiment3_Geolocation.class);

        solo.clickOnView(solo.getView(R.id.publish3_yes_button));

        solo.assertCurrentActivity("wrong Activity", PublishExperiment4_Complete.class);
        TextView text = (TextView) solo.getView(R.id.publish4_description);
        assertTrue(text.getText().toString().contains("Geolocation required: true"));
    }

    @Test
    public void checkNoButton() {
        initialize();
        solo.assertCurrentActivity("wrong Activity", PublishExperiment3_Geolocation.class);

        solo.clickOnView(solo.getView(R.id.publish3_no_button));

        solo.assertCurrentActivity("wrong Activity", PublishExperiment4_Complete.class);
        TextView text = (TextView) solo.getView(R.id.publish4_description);
        assertTrue(text.getText().toString().contains("Geolocation required: false"));
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
