package com.scrippy2.myeatup.firebasedata;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;


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

    public Bitmap download(String pathChild) {
        Bitmap photo = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        StorageReference pathReference = storageRef.child(pathChild);

        ByteBuffer buffer = ByteBuffer.wrap(pathReference.getBytes(10000).getResult());

        photo.copyPixelsFromBuffer(buffer);

        return photo;
    }

}
