package com.example.didact.miprimerfirebase;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityParte3 extends AppCompatActivity {

    EditText etNombre, etDorsal, etPosicion, etSueldo;
    Spinner spIndice;

    private DatabaseReference dbRef;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte3);

        etNombre = (EditText)findViewById(R.id.etNombre);
        etDorsal = (EditText)findViewById(R.id.etDorsal);
        etPosicion = (EditText)findViewById(R.id.etPosicion);
        etSueldo = (EditText)findViewById(R.id.etSueldo);
        spIndice = (Spinner)findViewById(R.id.spIndice);
        String[] tipos={"j1","j2","j3","j4"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
                tipos);
        spIndice.setAdapter(adaptador);



    }

    public void verIndice(View view){

        String jugadorSeleccionado = spIndice.getSelectedItem().toString();

        dbRef = FirebaseDatabase.getInstance().getReference().child("jugadores/"+jugadorSeleccionado);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CJugador jug = dataSnapshot.getValue(CJugador.class);

                if (jug != null) {
                    etNombre.setText(jug.getNombre());
                    etDorsal.setText(jug.getDorsal() + "");
                    etPosicion.setText(jug.getPosicion());
                    etSueldo.setText(jug.getSueldo() + "");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ActivityParte3","DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);

    }

    public void clickGuardar(View view){

        String nombre = etNombre.getText().toString();
        String strDorsal = etDorsal.getText().toString();
        String posicion = etPosicion.getText().toString();
        String strSueldo = etSueldo.getText().toString();

        if (etNombre.equals("") || etSueldo.equals("") || etPosicion.equals("") || etSueldo.equals("")) {

            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }else{

            int dorsal = Integer.parseInt(strDorsal);
            double sueldo = Double.parseDouble(strSueldo);
            CJugador nuevoJugador=new CJugador(nombre,dorsal,posicion,sueldo);
            dbRef = FirebaseDatabase.getInstance().getReference().child("jugadores");

            dbRef.child("j5").setValue(nuevoJugador, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null){

                        Toast.makeText(getApplicationContext(),"Insertado correctamente",Toast.LENGTH_LONG).show();
                        limpiarFormulario();

                    }else{

                        Toast.makeText(getApplicationContext(),"No se puede insertar el jugador",Toast.LENGTH_LONG).show();

                    }
                }
            });



        }

    }

    public void clickModificar(View view){

        String nombre = etNombre.getText().toString();
        String strDorsal = etDorsal.getText().toString();
        String posicion = etPosicion.getText().toString();
        String strSueldo = etSueldo.getText().toString();
        String indiceSeleccionado = spIndice.getSelectedItem().toString();

        if (etNombre.equals("") || etSueldo.equals("") || etPosicion.equals("") || etSueldo.equals("")) {

            Toast.makeText(getApplicationContext(), "Si modificar no puedes dejar ningun dato vacio", Toast.LENGTH_LONG).show();
        }else{

            int dorsal = Integer.parseInt(strDorsal);
            double sueldo = Double.parseDouble(strSueldo);
            CJugador nuevoJugador=new CJugador(nombre,dorsal,posicion,sueldo);
            dbRef = FirebaseDatabase.getInstance().getReference().child("jugadores");

            dbRef.child(indiceSeleccionado).setValue(nuevoJugador, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null){

                        Toast.makeText(getApplicationContext(),"Insertado correctamente",Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(getApplicationContext(),"No se puede insertar el jugador",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }


    }

    public void clickEliminar(View view){

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        DialogComprobacion dialogo = new DialogComprobacion();
        dialogo.show(fragmentManager, "dialogConfirmacino");*/
        dbRef = FirebaseDatabase.getInstance().getReference().child("jugadores");
        String indiceSeleccionado = spIndice.getSelectedItem().toString();

        dbRef.child(indiceSeleccionado).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    Toast.makeText(getApplicationContext(),"Eliminado correctamente", Toast.LENGTH_LONG).show();
                    limpiarFormulario();
                }else{
                    Toast.makeText(getApplicationContext(),"No se pudo eliminar", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void limpiarFormulario(){
        etNombre.setText("");
        etDorsal.setText("");
        etPosicion.setText("");
        etSueldo.setText("");
    }

}
