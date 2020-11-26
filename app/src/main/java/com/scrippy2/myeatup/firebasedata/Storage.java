package com.scrippy2.myeatup.firebasedata;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;


public class Storage {
    private Uri returnUri;
    private File localFile;


    public void upload(String pathChild, Bitmap photo) {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = mStorageRef.child(pathChild);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //photo.recycle();

        fileRef.putBytes(byteArray);
    }

    public void download(String pathChild, final ImageView imageView) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://eatupdatabase-cbe42.appspot.com" + "/" + pathChild);
        try {
            localFile = File.createTempFile("images", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                returnUri = Uri.fromFile(localFile);
                imageView.setImageURI(returnUri);
                imageView.setBackground(null);
            }
        });


        //return returnUri;
    }
}
