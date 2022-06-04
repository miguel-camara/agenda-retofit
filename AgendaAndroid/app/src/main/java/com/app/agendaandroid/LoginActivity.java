package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agendaandroid.api.IAgendaAPI;
import com.app.agendaandroid.model.User;
import com.app.agendaandroid.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegister;
    EditText etLoginEmail;
    EditText etLoginPassword;

    User user;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        btnLogin = findViewById( R.id.btnLogin );
        tvRegister = findViewById( R.id.tvRegister );
        etLoginEmail = findViewById( R.id.etLoginEmail );
        etLoginPassword = findViewById( R.id.etLoginPassword );

        btnLogin.setOnClickListener( view -> {
            if ( validateForm() ) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                IAgendaAPI iAgendaAPI = retrofit.create( IAgendaAPI.class );

                Call<User> call = iAgendaAPI.login( user );

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText( LoginActivity.this, "¡Correo o Contraseña Incorrecta!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        goToMain( response.body().getId()  );
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText( LoginActivity.this, "¡Ocurrio un Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tvRegister.setOnClickListener( view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class );
            startActivity( intent );
            finish();
        });
    }

    public void goToMain( String uid ){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class );
        intent.putExtra("uid", uid );
        startActivity( intent );

        finish();
    }

    public boolean validateForm() {
        etLoginPassword.setError( null );
        etLoginEmail.setError( null );

        boolean isEmpty = true;
        String password = etLoginPassword.getText().toString();
        String email = etLoginEmail.getText().toString();

        password = password.trim();

        if ( password.isEmpty() ) {
            etLoginPassword.setError( getString( R.string.field ) );
            etLoginPassword.requestFocus();
            isEmpty = false;
        }

        email = email.trim();

        if (email.isEmpty()) {
            etLoginEmail.setError( getString( R.string.field ) );
            etLoginEmail.requestFocus();
            isEmpty = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            etLoginEmail.setError( "¡Correo NO válido!" );
            etLoginEmail.requestFocus();
            isEmpty = false;
        }

        user = new User( "", "",  "", email, password );

        return isEmpty;
    }
}