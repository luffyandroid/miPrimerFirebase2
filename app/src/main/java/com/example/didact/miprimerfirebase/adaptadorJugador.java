package com.example.didact.miprimerfirebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DIDACT on 26/02/2018.
 */

public class adaptadorJugador extends ArrayAdapter<CJugador> {

    ArrayList<CJugador> jugadores;
    Context c;

    public adaptadorJugador(Context c, ArrayList<CJugador> personajes){
        super(c, R.layout.item_jugador, personajes);
        this.c = c;
        this.jugadores = personajes;



    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_jugador, null);

        TextView tv_nombre=(TextView)item.findViewById(R.id.tvEje2Nombre);
        tv_nombre.setText(jugadores.get(position).getNombre());


        TextView tv_posicion=(TextView)item.findViewById(R.id.tvEje2Posicion);
        tv_posicion.setText(jugadores.get(position).getPosicion());

        TextView tv_dorsal=(TextView)item.findViewById(R.id.tvEje2Dorsal);
        tv_dorsal.setText(jugadores.get(position).getDorsal()+"");


        return item;
    }
}
