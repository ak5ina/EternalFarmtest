package com.example.myeatup.firebasedata;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class Storage {


    public void upload(String pathChild, Bitmap photo) {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = mStorageRef.child(pathChild);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        photo.recycle();

        fileRef.putBytes(byteArray);
    }

    public void download(String pathChild, String URL) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        StorageReference pathReference = storageRef.child(pathChild);

        StorageReference gsReference = storage.getReferenceFromUrl(URL);
       // StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");


    }
}
