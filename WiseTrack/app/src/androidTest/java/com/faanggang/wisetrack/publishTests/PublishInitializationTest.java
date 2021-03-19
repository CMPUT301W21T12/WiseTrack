package com.faanggang.wisetrack.publishTests;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.faanggang.wisetrack.view.MainMenuActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.view.publish.PublishExperiment1_Initialization;
import com.faanggang.wisetrack.view.publish.PublishExperiment2_TrialType;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

 /** Test class for PublishExperiment1_Initialization.
  * 1. Test to see if MainMenuActivity takes us to PublishExperiment1_Initialization.
  * 2. Test for input and see if we can go to PublishExperiment2_TrialType.
 */

public class PublishInitializationTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainMenuActivity> rule =
            new ActivityTestRule<>(MainMenuActivity.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start(){
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkPublishInitializationStartup() {
        //Check if current activity is the main menu activity
        // message "wrong Activity" is displayed if the activity is not MainMenuActivity.class
        solo.assertCurrentActivity("wrong Activity", MainMenuActivity.class);

        // Click button "Publish Experiment"
        solo.clickOnView(solo.getView(R.id.menuPublish_button));

        solo.assertCurrentActivity("wrong Activity", PublishExperiment1_Initialization.class);
    }

    @Test
    public void checkPublishInitializationInput() {
        //Check if current activity is the main menu activity
        // message "wrong Activity" is displayed if the activity is not MainMenuActivity.class
        solo.assertCurrentActivity("wrong Activity", MainMenuActivity.class);

        solo.clickOnView(solo.getView(R.id.menuPublish_button));

        solo.enterText((EditText) solo.getView(R.id.name_input), "Coin");
        solo.enterText((EditText) solo.getView(R.id.description_input),
                "Coin test description.\nThis is my experiment!");
        solo.enterText((EditText) solo.getView(R.id.region_input), "Canada");
        solo.enterText((EditText) solo.getView(R.id.minTrials_input), "55");
        solo.clickOnView(solo.getView(R.id.choose_test_type_button));

        solo.assertCurrentActivity("wrong Activity", PublishExperiment2_TrialType.class);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }

}
