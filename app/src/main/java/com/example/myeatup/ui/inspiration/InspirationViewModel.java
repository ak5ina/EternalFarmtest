package com.example.myeatup.ui.inspiration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InspirationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InspirationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}