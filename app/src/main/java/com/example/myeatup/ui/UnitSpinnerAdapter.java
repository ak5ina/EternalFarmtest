package com.example.myeatup.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myeatup.R;
import com.example.myeatup.ui.Steps;

import java.util.ArrayList;

public class UnitSpinnerAdapter extends ArrayAdapter {

    ArrayList<String> unit;

    public UnitSpinnerAdapter(@NonNull Context context, int resource, ArrayList<String> unit) {
        super(context, resource, unit);
        this.unit = unit;
    }


    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_steps, parent, false);
        }


        return convertView;
    }





    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
