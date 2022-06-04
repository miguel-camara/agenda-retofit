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

import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditEventActivity extends AppCompatActivity  implements DateHelper, View.OnClickListener {

    private EditText etEditDate, etEditTime;
    private Spinner spinnerEditActivity;
    private Button btnSaveChanges, btnCancelEdition;
    private ImageButton ibEditDate, ibEditTime;

    private Event event;

    final String[] data = new String[]{"Actividad Física", "Trabajo", "Compras",
            "Recreativo", "Otros"};

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            finish();
            return;
        }

        init();

        String id = extras.getString("id");
        String activity = extras.getString("activity");
        String date = extras.getString("date");
        String time = extras.getString("time");
        boolean status = extras.getBoolean( "status" );
        String uid = extras.getString( "uid" );

        event = new Event( id, activity, date, time, uid, status );

        spinnerEditActivity.setSelection( getCategory( event.getActivity() ) );
        etEditDate.setText( event.getDate() );
        etEditTime.setText( event.getTime() );

        ibEditDate.setOnClickListener( this );
        ibEditTime.setOnClickListener(this);

        btnCancelEdition.setOnClickListener( this );
        btnSaveChanges.setOnClickListener( this );
    }

    private void init() {
        spinnerEditActivity = findViewById(R.id.spinnerEditActivity );
        etEditDate = findViewById(R.id.etEditDate);
        etEditTime = findViewById(R.id.etEditTime );

        spinnerEditActivity = findViewById(R.id.spinnerEditActivity);

        ibEditDate = findViewById(R.id.ibEditDate);
        ibEditTime = findViewById(R.id.ibEditTime);

        btnCancelEdition = findViewById(R.id.btnCancelEdition);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);

        spinnerEditActivity.setAdapter(adapter);
    }

    private int[] parseDate( String date ) {
        String [] result = date.split("/");

        return Arrays.stream( result ).mapToInt(Integer::parseInt).toArray();
    }

    private int[] parseHour( String hour ) {
        String [] result = hour.split(":");

        return Arrays.stream( result ).mapToInt(Integer::parseInt).toArray();
    }

    private void editDate() {
        int dates [] = parseDate( event.getDate() );

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                etEditDate.setText(date);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(EditEventActivity.this, onDateSetListener, dates[2], dates[1] - 1, dates[0]);
        datePickerDialog.show();
    }


    private void editHour () {

        int hours[] = parseHour( event.getTime() );

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                etEditTime.setText( String.format(Locale.getDefault(), "%02d:%02d", hour, minute) );
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog( this, onTimeSetListener, hours[0], hours[1], true);
        timePickerDialog.show();
    }

    private void cancelEdition() {
        finish();
    }

    private int getCategory(String category) {
        switch (category) {
            case "Actividad Física":
                return 0;
            case "Trabajo":
                return 1;
            case "Compras":
                return  2;
            case "Recreativo":
                return 3;
            case "Otros":
                return  4;
            default:
                return -1;
        }
    }

    private void saveChanges() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        IAgendaAPI iAgendaAPI = retrofit.create( IAgendaAPI.class );

        Call<Event> call = iAgendaAPI.updateEvent( event.getId(), event );

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( EditEventActivity.this, "¡Ups!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(EditEventActivity.this, "Actualizado", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText( EditEventActivity.this, "¡Ocurrio un Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateForm () {
        boolean isEmpty = true;

        etEditDate.setError(null);
        etEditTime.setError(null);

        String updateActivity = spinnerEditActivity.getSelectedItem().toString();
        String updateDate = etEditDate.getText().toString();
        String updateTime = etEditTime.getText().toString();

        // Update Date
        updateDate = updateDate.trim();

        // not is Empty
        if ( updateDate.isEmpty() ) {
            etEditDate.setError("¡Seleccione una fecha!");
            etEditDate.requestFocus();
            isEmpty = false;
        }

        // Update Hour
        updateTime = updateTime.trim();

        // not is Empty
        if ( updateTime.isEmpty() ) {
            etEditTime.setError("¡Seleccione una hora!");
            etEditTime.requestFocus();
            isEmpty = false;
        }

        event.setActivity( updateActivity );
        event.setDate( updateDate );
        event.setTime( updateTime );

        return isEmpty;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.ibEditDate:
                editDate();
                break;
            case R.id.ibEditTime:
                editHour();
                break;
            case R.id.btnCancelEdition:
                cancelEdition();
                break;
            case R.id.btnSaveChanges:
                if ( validateForm() ) saveChanges();
                break;
        }
    }
}