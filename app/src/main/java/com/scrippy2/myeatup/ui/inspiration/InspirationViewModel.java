package com.scrippy2.myeatup.ui.inspiration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InspirationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InspirationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}