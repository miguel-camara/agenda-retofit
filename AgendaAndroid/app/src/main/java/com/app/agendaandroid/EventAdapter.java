package com.app.agendaandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.agendaandroid.api.IAgendaAPI;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> eventList;
    private final Context context;
    private final String uid;

    public EventAdapter(List<Event> eventList, Context context, String uid) {
        this.eventList = eventList;
        this.context = context;
        this.uid = uid;
    }

    public void setEventList( List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View eventRow = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.event, viewGroup, false );
        return new MyViewHolder( eventRow );
    }

    private int setImage( String category ) {
        switch (category) {
            case "Actividad Física":
                return R.drawable.ic_baseline_directions_run;
            case "Trabajo":
                return R.drawable.ic_baseline_work;
            case "Compras":
                return  R.drawable.ic_baseline_shopping_cart;
            case "Recreativo":
                return R.drawable.ic_baseline_directions_bike;
            default:
                return R.drawable.ic_baseline_event;
        }
    }


    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int  i ) {
        Event event = eventList.get( i );

        String activity = event.getActivity();
        String date = event.getDate();
        String time = event.getTime();
        boolean status = event.isStatus();

        myViewHolder.category.setText( activity );
        myViewHolder.date.setText( date );
        myViewHolder.time.setText( time );
        myViewHolder.ivIcon.setImageResource( setImage( activity ) );

        myViewHolder.ivFavorite.setImageResource( status ? R.drawable.ic_baseline_favorite :  R.drawable.ic_baseline_favorite_border );

        myViewHolder.ivFavorite.setOnClickListener( view ->  {

            if( event.isStatus() )myViewHolder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite_border );
            else myViewHolder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite );

            event.setStatus( !event.isStatus() );

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Util.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            IAgendaAPI iAgendaAPI = retrofit.create( IAgendaAPI.class );

            Call<Event> call = iAgendaAPI.updateEvent( event.getId(), event );

            call.enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText( EventAdapter.this.context, "¡Ups!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    Toast.makeText( EventAdapter.this.context, "¡Ocurrio un Error!", Toast.LENGTH_SHORT).show();
                }
            });
        } );

        myViewHolder.ivEdit.setOnClickListener( view ->  {
            Intent intent = new Intent( context, EditEventActivity.class );
            intent.putExtra("id", event.getId() );
            intent.putExtra( "activity", event.getActivity() );
            intent.putExtra( "date", event.getDate() );
            intent.putExtra( "time", event.getTime() );
            intent.putExtra( "uid", event.getUid() );
            intent.putExtra( "status", event.isStatus() );

            context.startActivity( intent );
        } );
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category, date, time;
        ImageView ivFavorite, ivIcon, ivEdit;

        MyViewHolder( View itemView ) {
            super(itemView);

            this.category = itemView.findViewById( R.id.txtViewCategory );
            this.date = itemView.findViewById( R.id.txtViewDate );
            this.time = itemView.findViewById( R.id.txtViewTime );
            this.ivFavorite = itemView.findViewById( R.id.ivFavorite );
            this.ivIcon = itemView.findViewById( R.id.ivIcon );
            this.ivEdit = itemView.findViewById( R.id.ivEdit );

        }
    }
}