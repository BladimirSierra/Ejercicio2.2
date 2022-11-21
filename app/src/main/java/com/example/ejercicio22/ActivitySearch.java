package com.example.ejercicio22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ejercicio22.Interfaces.ApiUssers;
import com.example.ejercicio22.Models.Usuarios;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivitySearch extends AppCompatActivity {

    ListView listapersonas;
    ArrayList<String> titulos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        usuario = (EditText) findViewById(R.id.txt_idusuario);
        usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Obtenerlistadosusuario(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listapersonas = (ListView) findViewById(R.id.listado_users);
        listapersonas.setAdapter(arrayAdapter);
    }

    private void Obtenerlistadosusuario(String texto)
    {

        if (titulos.size()>0)
        {
            titulos.remove(0);
        }
        arrayAdapter.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiUssers usuariosApi = retrofit.create(ApiUssers.class);

        Call<Usuarios> calllista = usuariosApi.getUsuario(texto);
        calllista.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response)
            {
                Log.i(response.body().getTitle(), "onResponse");
                titulos.add(response.body().getTitle());

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });
    }
}