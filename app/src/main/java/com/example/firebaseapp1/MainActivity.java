package com.example.firebaseapp1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView lblSaludo;
    private Button btnCambiar;
    private int contador;
    private ImageView Foco1, Foco2, Foco3;
    private Button btnPrender1, btnPrender2, btnPrender3;
    private Button btnApagar1, btnApagar2, btnApagar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Conectar a la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Utilizar la referencia saludo
        final DatabaseReference myRef = database.getReference("saludo");
        final DatabaseReference myRef2 = database.getReference("leds");

        Foco1=findViewById(R.id.Foco1);
        Foco2=findViewById(R.id.Foco2);
        Foco3=findViewById(R.id.Foco3);
        btnPrender1=findViewById(R.id.btnPrender1);
        btnPrender2=findViewById(R.id.btnPrender2);
        btnPrender3=findViewById(R.id.btnPrender3);
        btnApagar1=findViewById(R.id.btnApagar1);
        btnApagar2=findViewById(R.id.btnApagar2);
        btnApagar3=findViewById(R.id.btnApagar3);

        btnPrender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               myRef2.child("0").child("estado").setValue("1");
            }
        });

        btnPrender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("1").child("estado").setValue("1");
            }
        });

        btnPrender3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("2").child("estado").setValue("1");
            }
        });

        btnApagar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("0").child("estado").setValue("0");
            }
        });

        btnApagar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("1").child("estado").setValue("0");
            }
        });

        btnApagar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("2").child("estado").setValue("0");
            }
        });


        contador=1;
        lblSaludo = (TextView)findViewById(R.id.lblSaludo);
        btnCambiar = (Button)findViewById(R.id.btnCambiar);
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = "Cambio " + String.valueOf(contador);
                myRef.setValue(texto);
                contador++;
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Capturando el valor de la referencia "Saludo"
                String value = dataSnapshot.getValue(String.class);
                lblSaludo.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("ERROR", error.getMessage());

            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val1 = (String) dataSnapshot.child("0").child("estado").getValue();
                if (val1 != null) {
                    if(val1.equals("0"))
                        Foco1.setImageResource(R.drawable.foco_off);
                    else
                        Foco1.setImageResource(R.drawable.foco_prendido);
                }

                String val2 = (String) dataSnapshot.child("1").child("estado").getValue();
                if (val2 != null) {
                    if(val2.equals("0"))
                        Foco2.setImageResource(R.drawable.foco_off);
                    else
                        Foco2.setImageResource(R.drawable.foco_prendido);
                }

                String val3 = (String) dataSnapshot.child("2").child("estado").getValue();
                if (val3 != null) {
                    if(val3.equals("0"))
                        Foco3.setImageResource(R.drawable.foco_off);
                    else
                        Foco3.setImageResource(R.drawable.foco_prendido);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
