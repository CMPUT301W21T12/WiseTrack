package com.faanggang.wisetrack;

import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.controllers.PublishingManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class PublishingManagerTest {
    private static FirebaseFirestore mockDb;
    private static CollectionReference mockCollectionRef;
    private static Query mockQuery;
    private static ArrayList<String> keywords;
    private static Task mockTask;
    private PublishingManager publishingManager;
    private static Experiment testExperiment;

    @BeforeClass
    public static void initializeObject() {
        mockDb = Mockito.mock(FirebaseFirestore.class);
        mockCollectionRef = Mockito.mock(CollectionReference.class);
        mockQuery = Mockito.mock(Query.class);
        mockTask = Mockito.mock(Task.class);

        keywords = new ArrayList<>();
        keywords.add("TEST");
        keywords.add("EXPERIMENTS");



        testExperiment = new Experiment("Coin Flipping", "Just flip your coin" +
                "and tell me how many heads you've gotten!", "Canada", 5, 1,
                false, new Date(), "TEST_USER");

        Mockito.when(mockDb.collection("Experiments"))
                .thenReturn(mockCollectionRef);
        Mockito.when(mockCollectionRef.add(Mockito.anyMap()))
                .thenReturn(mockTask);
        Mockito.when(mockTask.addOnCompleteListener(Mockito.any(OnCompleteListener.class)))
                .thenReturn(mockTask);
        Mockito.when(mockTask.addOnFailureListener(Mockito.any(OnFailureListener.class)))
                .thenReturn(mockTask);
    }

    @Test
    public void testMapCreation() {
        publishingManager = new PublishingManager(mockDb);

        Map experimentMap = publishingManager.createExperimentMap(testExperiment, "TEST_USER");

        assertEquals(experimentMap.get("name"), "Coin Flipping");
        assertEquals(experimentMap.get("geolocation"), false);
    }

    @Before
    public void cleanUp() {
        Mockito.reset();
    }

    @Test
    public void testPublishingToMockDB() {
        publishingManager = new PublishingManager(mockDb);
        Map experimentMap = publishingManager.createExperimentMap(testExperiment, "TEST_USER");
        try {
            publishingManager.publishExperiment(experimentMap);
        } catch (Exception e) { }

        Mockito.verify(mockDb, Mockito.times(1))
                .collection("Experiments");
        Mockito.verify(mockCollectionRef, Mockito.times(1))
                .add(experimentMap);
    }

}
