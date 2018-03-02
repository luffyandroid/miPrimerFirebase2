package com.example.didact.miprimerfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityParte2 extends AppCompatActivity {

    ListView lvJugadores;
    TextView tvJugadores;
    ArrayList<CJugador> listaJugadores = new ArrayList<>();

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte2);

        //cargarDatos2();

        lvJugadores = (ListView)findViewById(R.id.lvJugadores);
        tvJugadores = (TextView)findViewById(R.id.tvJugadores);

        cargarDatosFirebase();

    }

    /*private void cargarDatos2() {
        listaJugadores.add(new CJugador("Pele", 6, "Delantero", 40000));
        listaJugadores.add(new CJugador("Messi", 5, "Central",30000));
        listaJugadores.add(new CJugador("Cristiano Ronaldo", 7, "Delantero",34000));
        listaJugadores.add(new CJugador("Casillas", 1, "Portero",40000));
        listaJugadores.add(new CJugador("Pepe", 3, "Defensa",32000));

    }*/

    private void cargarListView (DataSnapshot dataSnapshot){

        listaJugadores.add(dataSnapshot.getValue(CJugador.class));

        adaptadorJugador adaptadorJugador=new adaptadorJugador(this,listaJugadores);

        lvJugadores.setAdapter(adaptadorJugador);

        lvJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CJugador cJugador = ((CJugador)parent.getItemAtPosition(position));
                Toast.makeText(ActivityParte2.this, "El sueldo de "+cJugador.getNombre()+" es de "+cJugador.getSueldo(), Toast.LENGTH_SHORT).show();
            }
        });

        lvJugadores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    private void cargarDatosFirebase(){

        //hacemos referencia a la base de datos
        dbRef = FirebaseDatabase.getInstance().getReference().child("jugadores");

        //añadimos el evento que no va a devolver los valores
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaJugadores.clear();
                for (DataSnapshot jugadoresDataSnapshot: dataSnapshot.getChildren()){
                    cargarListView(jugadoresDataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte2", "DATABASE ERROR");
            }
        };
        //asignamos el evento para que sea a tiempo real
        dbRef.addValueEventListener(valueEventListener);
    }


}
