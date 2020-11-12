package com.example.myeatup.firebasedata;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;


public class Storage {


    public void upload(String pathName, String pathChild, Bitmap photo) {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference fileRef = mStorageRef.child(pathChild);
        //fileRef.putBytes(photo);


    }

    public void download(String pathChild, String URL) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        StorageReference pathReference = storageRef.child(pathChild);

        StorageReference gsReference = storage.getReferenceFromUrl(URL);
       // StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");



    }

}
