package com.faanggang.wisetrack;

import android.graphics.Bitmap;

import com.faanggang.wisetrack.controllers.QRCodeManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

/**
 Only test if QRCodeManager.stringToBitmap() works
 */
public class QRCodeManagerTest {

    private static FirebaseFirestore mockDb;
    private static QRCodeManager qrCodeManager;
    private static String string1;
    private static String string2;
    private static int width;
    private static int height;

    @BeforeClass
    public static void initializeObject() {
        mockDb = Mockito.mock(FirebaseFirestore.class);

        qrCodeManager = new QRCodeManager(mockDb);
        string1 = "asdfkjld12312830981";
        string2 = "nnbxzcvkjkdfslfj98989a";
        width = 50;
        height = 50;
    }

    @Test
    public void bitmapInitialization(){
        Bitmap bitmap1 = Mockito.mock(qrCodeManager.stringToBitmap(string1, width, height).getClass());

        assertNotNull(bitmap1);  // make sure that a bitmap is generated

        assertEquals(width, bitmap1.getWidth());
        assertEquals(height, bitmap1.getHeight());
    }

/*
    @Test
    public void bitmapConsistency(){
        Bitmap bitmap1 = qrCodeManager.stringToBitmap(string1, width, height);
        Bitmap bitmap2 = qrCodeManager.stringToBitmap(string1, width, height);

        // make sure that the same bitmap is generated for the same string
        assertEquals(bitmap1, bitmap2);
    }

    @Test
    public void bitmapUnique(){
        Bitmap bitmap1 = qrCodeManager.stringToBitmap(string1, width, height);
        Bitmap bitmap2 = qrCodeManager.stringToBitmap(string2, width, height);

        // for two different strings, make sure that the bitmaps are not the same
        assertNotEquals(bitmap1, bitmap2);
    }
*/


}
