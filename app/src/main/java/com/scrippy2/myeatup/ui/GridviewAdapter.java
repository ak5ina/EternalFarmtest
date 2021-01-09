package com.scrippy2.myeatup.ui;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import com.scrippy2.myeatup.R;
        import com.scrippy2.myeatup.firebasedata.IngredientDTO;

        import java.util.ArrayList;

public class GridviewAdapter extends ArrayAdapter<IngredientDTO> {
    private  ArrayList<IngredientDTO> ingredientList;
    private Context con;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_single_object, parent, false);
        }

        final TextView ingredientName = convertView.findViewById(R.id.gridview_name);
        ingredientName.setText(ingredientList.get(position).getName());

        convertView.isClickable();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                    intent.putExtra("ingredientID", ingredientList.get(position).getID());
                intent.putExtra("ingredientID", Integer.parseInt(ingredientList.get(position).getID()));
                ((Activity)getContext()).setResult(Activity.RESULT_OK, intent);
                ((Activity)getContext()).finish();

            }
        });

//        ImageView ingredientImage = convertView.findViewById(R.id.inspiration_listview_object_pic);
//        ImageView closeBtn = convertView.findViewById(R.id.inspiration_listview_object_closebtn);
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ingredientList.remove(position);
//                notifyDataSetChanged();
//            }
//        });

//        ingredientImage.setImageResource(ingredientList.get(position).getImageID());


        return convertView;
    }

    public GridviewAdapter(@NonNull Context context, int resource, ArrayList<IngredientDTO> ingredientList) {
        super(context, resource, ingredientList);
        this.con = context;
        this.ingredientList = ingredientList;

    }
}
