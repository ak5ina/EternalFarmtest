package com.scrippy2.myeatup.firebasedata;

import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;


public class Storage {


    public void upload(String pathName, String pathChild) {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(pathName));
        StorageReference fileRef = mStorageRef.child(pathChild);
        fileRef.putFile(file);


    }

    public void download(String pathChild, String URL) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        StorageReference pathReference = storageRef.child(pathChild);

        StorageReference gsReference = storage.getReferenceFromUrl(URL);
       // StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");



    }

}
