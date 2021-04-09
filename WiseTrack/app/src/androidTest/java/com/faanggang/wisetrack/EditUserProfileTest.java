package com.faanggang.wisetrack;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.faanggang.wisetrack.view.MainActivity;
import com.faanggang.wisetrack.view.MainMenuActivity;
import com.faanggang.wisetrack.view.user.EditProfileActivity;
import com.faanggang.wisetrack.view.user.ViewSelfActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;


/**
 * Test class for View My Profile and edit profile
 * This test is to make sure that all edits to user's profile
 * are successfully saved and shown
 */
public class EditUserProfileTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() {
        Activity activity = rule.getActivity();
    }

    /**
     * Check if the correct views are being displayed
     */
    @Test
    public void initializationTest() {
        solo.clickOnView(solo.getView(R.id.menu_button));
        //check if current activity is the main menu activity
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);

        //click on "view my profile" button
        solo.clickOnView(solo.getView(R.id.menuProfile_Button));

        solo.assertCurrentActivity("Wrong activity", ViewSelfActivity.class);

        //click on "edit profile" button
        solo.clickOnView(solo.getView(R.id.view_editProfile));

        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);
    }

    /**
     * check if user information is updated after editing
     */
    @Test
    public void editProfileInfoTest() {
        solo.clickOnView(solo.getView(R.id.menu_button));
        solo.assertCurrentActivity("Wrong activity", MainMenuActivity.class);
        solo.clickOnView(solo.getView(R.id.menuProfile_Button));
        solo.clickOnView(solo.getView(R.id.view_editProfile));

        solo.clearEditText((EditText) solo.getView(R.id.editTextFirstName));
        solo.enterText((EditText) solo.getView(R.id.editTextFirstName), "Bob");

        solo.clearEditText((EditText) solo.getView(R.id.editTextLastName));
        solo.enterText((EditText) solo.getView(R.id.editTextLastName), "Jones");

        solo.clearEditText((EditText) solo.getView(R.id.editTextEmail));
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "testemail@email.com");

        solo.clearEditText((EditText) solo.getView(R.id.editTextPhoneNumber));
        solo.enterText((EditText) solo.getView(R.id.editTextPhoneNumber), "1234567890");

        //click confirm button
        solo.clickOnView(solo.getView(R.id.confirmEditButton));
        //check if activity returns to view my profile activity
        solo.assertCurrentActivity("Wrong activity", ViewSelfActivity.class);

        //check if first name has been updated
        TextView fNameView = (TextView) solo.getView(R.id.view_fName);
        String firstName = fNameView.getText().toString();
        Assert.assertEquals("Bob", firstName);
        //check if last name has been updated
        TextView lNameView = (TextView) solo.getView(R.id.view_lastName);
        String lastName = lNameView.getText().toString();
        Assert.assertEquals("Jones", lastName);
        //check if email has been updated
        TextView emailView = (TextView) solo.getView(R.id.view_Email);
        String email = emailView.getText().toString();
        Assert.assertEquals("testemail@email.com", email);
        //check if phone number has been updated
        TextView phoneNumView = (TextView) solo.getView(R.id.view_phoneNumber);
        String phoneNumber = phoneNumView.getText().toString();
        Assert.assertEquals("1234567890", phoneNumber);


    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
