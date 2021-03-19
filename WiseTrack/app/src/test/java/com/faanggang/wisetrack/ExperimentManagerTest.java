package com.faanggang.wisetrack;
import com.faanggang.wisetrack.experiment.Experiment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class ExperimentManagerTest {
    private static MockSearcher mockSearcher;
    private static FirebaseFirestore mockDb;
    private static CollectionReference mockCollectionRef;
    private static Query mockQuery;
    private static String userID;
    private static Task mockTask;

    @BeforeClass
    public static void initializeObject() {
        mockDb = Mockito.mock(FirebaseFirestore.class);
        mockCollectionRef = Mockito.mock(CollectionReference.class);
        mockQuery = Mockito.mock(Query.class);
        mockTask = Mockito.mock(Task.class);
        mockSearcher = new MockSearcher(mockDb);
        userID = "2GYSfI70SLOeUV1vVRpA2bv9suj1";
        Mockito.when(mockDb.collection("Experiments"))
                .thenReturn(mockCollectionRef);
        Mockito.when(mockCollectionRef.whereEqualTo("uID", userID))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.get())
                .thenReturn(mockTask);
        Mockito.when(mockTask.addOnCompleteListener(Mockito.any()))
                .thenReturn(mockTask);
        Mockito.when(mockTask.addOnFailureListener(Mockito.any()))
                .thenReturn(mockTask);
    }

    @Before
    public void cleanUp() {
        Mockito.reset();
    }

    @Test
    public void testQueryForUserExperiments() {
        mockSearcher = new MockSearcher(mockDb);
        mockSearcher.userExperimentQueryRequest("2GYSfI70SLOeUV1vVRpA2bv9suj1");

        Mockito.verify(mockDb, Mockito.times(1))
                .collection("Experiments");
        Mockito.verify(mockCollectionRef, Mockito.times(1))
                .whereEqualTo("uID", userID);
    }
}
