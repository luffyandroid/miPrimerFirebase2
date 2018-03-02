package com.example.didact.miprimerfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EjercicioParte1Activity extends AppCompatActivity {

    TextView tvNombre, tvDorsal, tvPosicion, tvSueldo;
    Spinner spJugador;

    private DatabaseReference dbRef;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_parte1);

        tvNombre = (TextView)findViewById(R.id.tvNombreEje1);
        tvDorsal = (TextView)findViewById(R.id.tvDorsaleje1);
        tvPosicion = (TextView)findViewById(R.id.tvPosicionEje1);
        tvSueldo = (TextView)findViewById(R.id.tvSueldoEje1);
        spJugador = (Spinner)findViewById(R.id.spJugador);
        String[] tipos={"j1","j2","j3","j4"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
                tipos);
        spJugador.setAdapter(adaptador);

    }

    public void verJugador(View view){

        String jugadorSeleccionado = spJugador.getSelectedItem().toString();

        dbRef = FirebaseDatabase.getInstance().getReference().child("jugadores/"+jugadorSeleccionado);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CJugador jug = dataSnapshot.getValue(CJugador.class);
                tvNombre.setText("Nombre "+jug.getNombre());
                tvDorsal.setText("Dorsal "+jug.getDorsal());
                tvPosicion.setText("Posicion "+jug.getPosicion());
                tvSueldo.setText("sueldo "+jug.getSueldo()+" €");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte1","DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);

    }

}
