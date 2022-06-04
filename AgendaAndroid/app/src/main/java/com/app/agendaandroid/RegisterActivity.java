package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.agendaandroid.api.IAgendaAPI;
import com.app.agendaandroid.helper.ValidationsHelper;
import com.app.agendaandroid.model.User;
import com.app.agendaandroid.util.Util;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity  implements ValidationsHelper {

    Button btnRegister;
    EditText etRegisterName;
    EditText etRegisterPhone;
    EditText etRegisterEmail;
    EditText etRegisterPassword;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterEmail = findViewById( R.id.etRegisterEmail );
        etRegisterPassword = findViewById( R.id.etRegisterPassword );
        etRegisterName = findViewById( R.id.etRegisterName );
        etRegisterPhone = findViewById( R.id.etRegisterPhone );
        btnRegister = findViewById( R.id.btnRegister );

        btnRegister.setOnClickListener( view -> {
            if ( validateForm() ) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                IAgendaAPI iAgendaAPI = retrofit.create(IAgendaAPI.class);

                Call<User> call = iAgendaAPI.registerUser( user );

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText( RegisterActivity.this, "¡Correo Ya Registrado!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(  RegisterActivity.this, "Creado Correctamente", Toast.LENGTH_SHORT ).show();
                        goToLogin();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(  RegisterActivity.this, "Ocurrio un Error", Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        } );
    }

    public void goToLogin(){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity( i );
        finish();
    }

    public boolean validateForm() {
        etRegisterName.setError( null );
        etRegisterPassword.setError( null );
        etRegisterPhone.setError( null );
        etRegisterEmail.setError( null );

        boolean isEmpty = true;
        String phone = etRegisterPhone.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String email = etRegisterEmail.getText().toString();
        String name = etRegisterName.getText().toString();

        name = name.trim();

        if ( name.isEmpty() ) {
            etRegisterName.setError( getString( R.string.field ) );
            etRegisterName.requestFocus();
            isEmpty = false;
        } else if ( !name.matches( NAME ) ) {
            etRegisterName.setError( "Nombre Incorrecto" );
            etRegisterName.requestFocus();
            isEmpty = false;
        }

        if ( isEmpty ){
            name = name.toLowerCase( Locale.ROOT );
            name = Character.toUpperCase( name.charAt( 0 ) ) + name.substring( 1 );
        }

        phone = phone.trim();

        if ( phone.isEmpty() ) {
            etRegisterPhone.setError( getString( R.string.field ) );
            etRegisterPhone.requestFocus();
            isEmpty = false;
        } else if ( !phone.matches( NUMBER ) ) {
            etRegisterPhone.setError( "Numero Incorrecto" );
            etRegisterPhone.requestFocus();
            isEmpty = false;
        } else if ( phone.length() != 10 ) {
            etRegisterPhone.setError( "Escriba 10 digitos" );
            etRegisterPhone.requestFocus();
            isEmpty = false;
        }

        password = password.trim();

        if ( password.isEmpty() ) {
            etRegisterPassword.setError( getString( R.string.field ) );
            etRegisterPassword.requestFocus();
            isEmpty = false;
        } else if ( password.length() < 6 ) {
            etRegisterPassword.setError( "Minimo 6 caracteres" );
            etRegisterPassword.requestFocus();
            isEmpty = false;
        }

        email = email.trim();

        if (email.isEmpty()) {
            etRegisterEmail.setError( getString( R.string.field ) );
            etRegisterEmail.requestFocus();
            isEmpty = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            etRegisterEmail.setError( "¡Correo NO válido!" );
            etRegisterEmail.requestFocus();
            isEmpty = false;
        }

        user = new User( "", name, phone, email ,password );

        return isEmpty;
    }
}