package com.app.agendaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.agendaandroid.api.IAgendaAPI;
import com.app.agendaandroid.helper.DateHelper;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.util.Util;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEventActivity extends AppCompatActivity implements DateHelper, View.OnClickListener {
    private Button btnAddEvent, btnCancel;
    private Spinner spinnerActivity;
    private EditText etDate, etTime;
    private ImageButton ibDate, ibTime;

    private Event createEvent;

    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Bundle extras = getIntent().getExtras();

        init();

        String uid = extras.getString( "uid" );

        createEvent = new Event(  "","", "", uid, false );

        ibDate.setOnClickListener( this );
        ibTime.setOnClickListener( this );
        btnAddEvent.setOnClickListener( this );
        btnCancel.setOnClickListener( this );

    }

    private void init() {
        spinnerActivity = findViewById( R.id.spinnerActivity );
        etDate = findViewById( R.id.etDate );
        etTime = findViewById( R.id.etTime);

        ibDate = findViewById( R.id.ibDate );
        ibTime = findViewById( R.id.ibTime);

        btnAddEvent = findViewById( R.id.btnAddEvent );
        btnCancel = findViewById( R.id.btnCancel );

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);

        spinnerActivity.setAdapter(adapter);
    }

    private void openDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                etDate.setText(date);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, YEAR, MONTH - 1, DAY);
        datePickerDialog.show();
    }

    private void openHour() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etTime.setText( String.format(Locale.getDefault(), "%02d:%02d", hour, minute) );
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog( this, onTimeSetListener, HOUR, MINUTE, true);
        timePickerDialog.show();
    }

    private void cancel() {
        finish();
    }

    private void addQuote() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        IAgendaAPI iAgendaAPI = retrofit.create( IAgendaAPI.class );

        Call<Event> call = iAgendaAPI.addEvent( createEvent );

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( AddEventActivity.this, "¡Ups!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AddEventActivity.this, "Guardado", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText( AddEventActivity.this, "¡Ocurrio un Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm () {
        boolean isEmpty = true;

        etDate.setError( null );
        etTime.setError( null );

        String activity = spinnerActivity.getSelectedItem().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();

        // Update Date
        date = date.trim();

        // not is Empty
        if ( date.isEmpty() ) {
            etDate.setError("¡Seleccione una fecha!");
            etDate.requestFocus();
            isEmpty = false;
        }

        // Update Hour
        time = time.trim();

        // not is Empty
        if ( time.isEmpty() ) {
            etTime.setError("¡Seleccione una hora!");
            etTime.requestFocus();
            isEmpty = false;
        }

        createEvent.setDate( date );
        createEvent.setTime( time );
        createEvent.setActivity( activity );

        return isEmpty;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.ibDate:
                openDate();
                break;
            case R.id.ibTime:
                openHour();
                break;
            case R.id.btnCancel:
                cancel();
                break;
            case R.id.btnAddEvent:
                if ( validateForm() ) addQuote();
                break;
        }
    }
}