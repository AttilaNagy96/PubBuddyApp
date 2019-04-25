package ie.wit.pubbuddy.realtimeDatabaseCRUD;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.activities.Base;

public class UserFavs extends Base {

    Button saveButton;
    EditText edtName;
    DatabaseReference databaseReference;
    ListView listViewFavourites;
    List<Model> models;
    public static String modelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favs);

        models = new ArrayList<Model>();
        databaseReference = FirebaseDatabase.getInstance().getReference("models");

        saveButton = (Button) findViewById(R.id.saveButton);
        edtName = (EditText) findViewById(R.id.edtName);
        listViewFavourites = (ListView) findViewById(R.id.listViewFavourites);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();

                if(TextUtils.isEmpty(modelId)){
                    String id = databaseReference.push().getKey();
                    Model model = new Model(id, name);
                    databaseReference.child(id).setValue(model);

                    Toast.makeText(UserFavs.this, "New beer added to your favourites list!", Toast.LENGTH_LONG).show();
                } else{
                    databaseReference.child(modelId).child("name").setValue(name);
                    Toast.makeText(UserFavs.this, "Favourites list updated successfully!", Toast.LENGTH_LONG).show();
                }
                edtName.setText(null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Model model = postSnapshot.getValue(Model.class);
                    models.add(model);
                }
                ModelList modelAdapter = new ModelList(UserFavs.this, models, databaseReference, edtName);
                listViewFavourites.setAdapter(modelAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
