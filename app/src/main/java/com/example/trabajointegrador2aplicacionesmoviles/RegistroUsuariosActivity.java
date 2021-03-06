package com.example.trabajointegrador2aplicacionesmoviles;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajointegrador2aplicacionesmoviles.utilidades.Utilidades;

public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campoId, campoPassword, campoNombre, campoCorreo, campoConfirmPassword;
    TextView rol;
    Spinner comboRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        campoId = (EditText) findViewById(R.id.campoId);
        campoPassword = (EditText) findViewById(R.id.campoPassword);
        campoNombre = (EditText) findViewById(R.id.campoNombre);
        campoCorreo = (EditText) findViewById(R.id.campoCorreo);
        campoConfirmPassword = (EditText)findViewById(R.id.campoConfirmPassword);

        rol  = (TextView) findViewById(R.id.rolUsuario);
        comboRol = (Spinner) findViewById(R.id.spinnerRol);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.comboRoles, android.R.layout.simple_spinner_item);

        comboRol.setAdapter(adapter);
        comboRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    rol.setText(parent.getItemAtPosition(position).toString());
                }else{

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onClick(View view) {
        String s1 = campoId.getText().toString();
        String s2 = campoPassword.getText().toString();
        String s3 = campoNombre.getText().toString();
        String s4 = campoCorreo.getText().toString();
        String s5 = campoConfirmPassword.getText().toString();
        String s6 = rol.getText().toString();


        if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("")||s6.equals("")) {
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            if(s2.equals(s5)){
                if (!s4.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(s4).matches()) {
                    registrarUsuarios();
                } else {
                    Toast.makeText(this,"Email invalido",Toast.LENGTH_SHORT).show();
                }
        }else {
                Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            }
            }
        }

        private void registrarUsuarios(){

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_ID, campoId.getText().toString());
            values.put(Utilidades.CAMPO_PASSWORD, campoPassword.getText().toString());
            values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
            values.put(Utilidades.CAMPO_CORREO, campoCorreo.getText().toString());
            values.put(Utilidades.CAMPO_ROLES,rol.getText().toString());

            Long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID, values);

            if(idResultante == -1){

                Toast.makeText(getApplicationContext(), "El DNI o Correo ya están registrados " + idResultante, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Se ha registrado el usuario correctamente", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }
