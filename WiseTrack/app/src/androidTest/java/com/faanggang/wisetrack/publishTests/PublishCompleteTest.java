package com.faanggang.wisetrack.publishTests;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.faanggang.wisetrack.MainActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.publish.PublishExperiment4_Complete;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for PublishExperiment4_Complete.
 * Given that PublishGeolocationTest tests all passed...
 * Check if clicking "PUBLISH" and "CANCEL" will take us back to the startup menu (MainActivity)
 */

public class PublishCompleteTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start(){
        Activity activity = rule.getActivity();
    }

    public void initialize(){
        solo.assertCurrentActivity("wrong Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.menu_button));

        solo.clickOnView(solo.getView(R.id.menuPublish_button));

        solo.enterText((EditText) solo.getView(R.id.name_input), "Coin");
        solo.enterText((EditText) solo.getView(R.id.description_input),
                "Coin test description.\nThis is my experiment!");
        solo.enterText((EditText) solo.getView(R.id.region_input), "Canada");
        solo.enterText((EditText) solo.getView(R.id.minTrials_input), "55");

        solo.clickOnView(solo.getView(R.id.choose_test_type_button));

        solo.clickOnView(solo.getView(R.id.counts_button));

        solo.clickOnView(solo.getView(R.id.publish3_yes_button));
    }

    @Test
    public void checkPublishButton() {
        initialize();
        solo.assertCurrentActivity("wrong Activity", PublishExperiment4_Complete.class);

        // Click button "Publish"
        solo.clickOnView(solo.getView(R.id.publish4_publish_button));

        solo.assertCurrentActivity("wrong Activity", MainActivity.class);
    }

    @Test
    public void checkCancelButton() {
        initialize();
        solo.assertCurrentActivity("wrong Activity", PublishExperiment4_Complete.class);

        // Click button "Cancel"
        solo.clickOnView(solo.getView(R.id.publish4_cancel_button));

        solo.assertCurrentActivity("wrong Activity", MainActivity.class);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
