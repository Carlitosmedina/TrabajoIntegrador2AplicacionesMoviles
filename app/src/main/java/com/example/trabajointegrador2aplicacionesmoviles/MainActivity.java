package com.example.trabajointegrador2aplicacionesmoviles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    Button b1;

    public static ConexionSQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.campoId2);
        e2=(EditText)findViewById(R.id.campoPassword2);
        b1=(Button) findViewById(R.id.btnLogin);
        String rol =null;

        cargarPreferencias();

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        sqLiteHelper = new ConexionSQLiteHelper(this, "MomentoDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MOMENTO(Id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion VARCHAR, image BLOB, fecha VARCHAR, ubicacion VARCHAR,universidad VARCHAR, urlEncuentro VARCHAR, lugar VARCHAR, aula VARCHAR, fechaEncuentro VARCHAR, horaEncuentro VARCHAR, categoria VARCHAR)");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni =e1.getText().toString();
                String pass=e2.getText().toString();
                Boolean chkdnipassword= conn.dniPassword(dni,pass);
                if (chkdnipassword==true){
                    guardarPreferencias();
                    String rol = conn.checkRol(dni,pass);

                    //ir a estudiante
                    if (rol.equals("Estudiante")){
                        Intent intent = new Intent(MainActivity.this, UserMomentoList.class);
                        startActivity(intent);

                    }
                    //ir a Administrativo
                    else if (rol.equals("Administrativo")) {

                        Intent intent = new Intent(MainActivity.this, MomentoList.class);
                        startActivity(intent);

                    }

                }



//                    Boolean chkAdmin =conn.chequeoAdmin(dni);
//                    if (chkAdmin==true){
//
//                        Intent intent = new Intent(MainActivity.this, MomentoList.class);
//                        startActivity(intent);
//
//                    }else {
//                        Toast.makeText(getApplicationContext(),"algo fallo",Toast.LENGTH_SHORT).show();
//                    }



            }
        });
    }

    private void cargarPreferencias() {
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("user","");
        String pass = preferences.getString("pass","");

        e1.setText(user);
        e2.setText(pass);

    }

    private void guardarPreferencias() {
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String usuario =e1.getText().toString();
        String pass=e2.getText().toString();

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("user",usuario);
        editor.putString("pass",pass);
        editor.commit();
    }


    public void onClick(View v) {
        Intent miIntent=null;
        switch (v.getId()){
            case R.id.btnOpcionRegistro:
                miIntent=new Intent(MainActivity.this,RegistroUsuariosActivity.class);
                break;
        }
        if (miIntent!=null){
            startActivity(miIntent);
        }

    }
}