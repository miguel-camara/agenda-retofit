package com.app.agendaandroid;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.agendaandroid.api.IAgendaAPI;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.util.Util;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ExtendedFloatingActionButton efabAddQuote;
    private String uid;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Bundle extras = getIntent().getExtras();

        uid = extras.getString( "uid" );

        recyclerView = findViewById( R.id.recyclerViewUser );
        efabAddQuote = findViewById( R.id.fabAddUser );

        eventList = new ArrayList<>();

        eventAdapter = new EventAdapter( eventList, this, extras.getString( "id" ) );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( mLayoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( eventAdapter );

        refreshEventList();

        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position ) { }

            @Override
            public void onLongClick( View view, int position ) {
                Event deleteEvent = eventList.get( position );

                AlertDialog dialog = new AlertDialog
                        .Builder( MainActivity.this )
                        .setPositiveButton( "Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                                IAgendaAPI iAgendaAPI = retrofit.create( IAgendaAPI.class );

                                Call<Event> call = iAgendaAPI.deleteUser( deleteEvent.getId() );

                                call.enqueue(new Callback<Event>() {
                                    @Override
                                    public void onResponse(Call<Event> call, Response<Event> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "¡Ups!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        refreshEventList();
                                    }

                                    @Override
                                    public void onFailure(Call<Event> call, Throwable t) {
                                        Toast.makeText( MainActivity.this, "¡Ocurrio un Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle( "Confirmar" )
                        .setMessage( "¿Eliminar " + deleteEvent.getActivity() + "?")
                        .create();
                dialog.show();
            }
        }));

        efabAddQuote.setOnClickListener(view ->  {
                Intent intent = new Intent( MainActivity.this, AddEventActivity.class );
                intent.putExtra( "uid", uid );
                startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshEventList();
    }

    public void refreshEventList() {
        if ( eventAdapter == null ) return;

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        IAgendaAPI iAgendaAPI = retrofit.create( IAgendaAPI.class );

        Call<List<Event>> call = iAgendaAPI.getEventsId( this.uid );

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( MainActivity.this, "¡Ups!", Toast.LENGTH_SHORT).show();
                    return;
                }
                eventList = response.body();
                eventAdapter.setEventList(eventList);
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText( MainActivity.this, "¡Ocurrio un Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}