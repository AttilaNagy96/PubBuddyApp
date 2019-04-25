package ie.wit.pubbuddy.realtimeDatabaseCRUD;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import ie.wit.pubbuddy.R;

public class ModelList extends ArrayAdapter<Model> {

    private Activity context;
    private List<Model> models;
    DatabaseReference databaseReference;
    EditText edtName;

    public ModelList(@NonNull Activity context, List<Model> models, DatabaseReference databaseReference, EditText edtName) {
        super(context, R.layout.layout_favourites_list, models);
        this.context = context;
        this.models = models;
        this.edtName = edtName;
        this.databaseReference = databaseReference;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_favourites_list, null, true);

        TextView txtName = (TextView) listViewItem.findViewById(R.id.txtName);
        ImageView btnDelete = (ImageView) listViewItem.findViewById(R.id.btnDelete);
        ImageView btnUpdate = (ImageView) listViewItem.findViewById(R.id.btnUpdate);

        final Model model = models.get(pos);
        txtName.setText(model.getName());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(model.getId()).removeValue();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText(model.getName());
                UserFavs.modelId = model.getId();
            }
        });

        return listViewItem;
    }
}
