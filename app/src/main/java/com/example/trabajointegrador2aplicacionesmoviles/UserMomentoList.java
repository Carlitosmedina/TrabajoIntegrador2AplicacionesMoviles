package com.example.trabajointegrador2aplicacionesmoviles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.example.trabajointegrador2aplicacionesmoviles.entidades.Momento;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.trabajointegrador2aplicacionesmoviles.MomentoDetail.getImageFromBLOB;


public class UserMomentoList extends AppCompatActivity {

    ListView gridView;
    ArrayList<Momento> list;
    MomentoListAdapter adapter = null;
    int cont = 0;
    ImageView imgFragment;
    Locale locale;
    Configuration config = new Configuration();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_momento_list_activity);

        gridView = (ListView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new MomentoListAdapter(this, R.layout.momento_items, list);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM MOMENTO");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String descripcion = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            String fecha = cursor.getString(3);
            String ubicacion = cursor.getString(4);
            String universidad = cursor.getString(5);
            String urlEncuentro = cursor.getString(6);
            String lugar = cursor.getString(7);
            String aula = cursor.getString(8);
            String fechaEncuentro = cursor.getString(9);
            String horaEncuentro = cursor.getString(10);
            String categoria = cursor.getString(11);

            list.add(new Momento(descripcion, image, id, fecha, ubicacion, universidad, urlEncuentro, lugar, aula, fechaEncuentro, horaEncuentro, categoria));
        }


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Guardar en SD"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserMomentoList.this);
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {

                            Toast.makeText(getApplicationContext(), "Se muestra mensaje, pero no esta hecha la funcionalidad para guardar", Toast.LENGTH_SHORT).show();

                            // GUARDAR IMAGEN SD
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM MOMENTO");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            guardarImagenSD(arrID.get(position));

                        }

                    }
                });
                dialog.show();
                return true;
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), MomentoDetail.class);

                view.findViewById(R.id.txtDescripcion);

                view.getTag();

                intent.putExtra("Position", position);

                startActivity(intent);
            }
        });
    }


    //  ############################################# BUSCAR POR CATEGORIA ################################

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();

                Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM MOMENTO WHERE categoria ='" + query + "'");
                list.clear();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String descripcion = cursor.getString(1);
                    byte[] image = cursor.getBlob(2);
                    String fecha = cursor.getString(3);
                    String ubicacion = cursor.getString(4);
                    String universidad = cursor.getString(5);
                    String urlEncuentro = cursor.getString(6);
                    String lugar = cursor.getString(7);
                    String aula = cursor.getString(8);
                    String fechaEncuentro = cursor.getString(9);
                    String horaEncuentro = cursor.getString(10);
                    String categoria = cursor.getString(11);

                    list.add(new Momento(descripcion, image, id, fecha, ubicacion, universidad, urlEncuentro, lugar, aula, fechaEncuentro, horaEncuentro, categoria));
                }

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")) {
                    Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM MOMENTO");
                    list.clear();
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String descripcion = cursor.getString(1);
                        byte[] image = cursor.getBlob(2);
                        String fecha = cursor.getString(3);
                        String ubicacion = cursor.getString(4);
                        String universidad = cursor.getString(5);
                        String urlEncuentro = cursor.getString(6);
                        String lugar = cursor.getString(7);
                        String aula = cursor.getString(8);
                        String fechaEncuentro = cursor.getString(9);
                        String horaEncuentro = cursor.getString(10);
                        String categoria = cursor.getString(11);

                        list.add(new Momento(descripcion, image, id, fecha, ubicacion, universidad, urlEncuentro, lugar, aula, fechaEncuentro, horaEncuentro, categoria));
                    }
                }

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    //Metodo para agregar las acciones de los botones

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.opcion3) {
            Intent miIntent;
            miIntent = new Intent(UserMomentoList.this, ItemListActivity.class);
            startActivity(miIntent);
        }

        if (id == R.id.opcion4) {
            Intent miIntent;
            miIntent = new Intent(UserMomentoList.this, MainActivity.class);
            startActivity(miIntent);
        }

        if (id == R.id.opcion5) {


            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle(getResources().getString(R.string.opcion5));
            //obtiene los idiomas del array de string.xml
            String[] types = getResources().getStringArray(R.array.languages);
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    switch (which) {
                        case 0:
                            locale = new Locale("es");
                            config.locale = locale;
                            break;
                        case 1:
                            locale = new Locale("en");
                            config.locale = locale;
                            break;
                        case 2:
                            locale = new Locale("pt");
                            config.locale = locale;
                            break;
                    }
                    getResources().updateConfiguration(config, null);
                    finish();
                    startActivity(getIntent());

                }

            });

            b.show();


        }

        return super.onOptionsItemSelected(item);

    }




    private void guardarImagenSD(int position) {

        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM MOMENTO WHERE Id=" + position);
        cursor.moveToPosition(position);

        //aca es donde obtiene la imagen. capaz hay que adaptarlo o ponerlo de otra forma
        imgFragment.setImageBitmap(getImageFromBLOB(cursor.getBlob(cursor.getColumnIndex("image"))));
        ;

        //codigo para insertar en sd
        //...


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(getApplicationContext(), "No tenes permisos para acceder a la ubicación del archivo!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (cont == 0) {
            Toast.makeText(getApplicationContext(), "Presiona una vez más para salir", Toast.LENGTH_SHORT).show();
            cont++;
        } else {
            super.onBackPressed();
            finishAffinity();

        }
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                cont = 0;

            }
        }.start();

    }
}


