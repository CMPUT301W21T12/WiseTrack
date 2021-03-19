package com.faanggang.wisetrack;

import com.faanggang.wisetrack.model.experiment.Experiment;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ExperimentTest {

    private Experiment experiment;
    private String eName = "Coins";
    private String eDescription = "Flip some coins";
    private String eRegion = "Canada";
    private int eMinTrials = 5;
    private int eTrialType = 0;
    private boolean eGeolocation = true;
    private Date eDate = new Date();  // initialize date based on current time
    private String eUID = "user129381723897123";

    @Before
    public void initializeExperiment(){
        experiment = new Experiment(eName, eDescription, eRegion, eMinTrials, eTrialType,
                eGeolocation, eDate, eUID);
    }

    @Test
    public void checkName(){
        assertEquals(eName, experiment.getName());
    }

    @Test
    public void checkDescription(){
        assertEquals(eDescription, experiment.getDescription());
    }

    @Test
    public void checkRegion(){
        assertEquals(eRegion, experiment.getRegion());
    }

    @Test
    public void checkMinTrials(){
        assertEquals(eMinTrials, experiment.getMinTrials());
    }

    @Test
    public void checkTrialType(){
        assertEquals(eTrialType, experiment.getTrialType());
    }

    @Test
    public void checkGeolocation(){
        assertEquals(eGeolocation, experiment.getGeolocation());
    }

    @Test
    public void checkDate(){
        assertEquals(eDate, experiment.getDate());
    }

    @Test
    public void checkUID(){
        assertEquals(eUID, experiment.getOwnerID());
    }

    @Test
    public void checkOpen(){
        // experiment.open should be true upon creation
        assertEquals(true, experiment.isOpen());

        // now try changing it to false
        experiment.setOpen(false);

        assertEquals(false, experiment.isOpen());
    }

}
