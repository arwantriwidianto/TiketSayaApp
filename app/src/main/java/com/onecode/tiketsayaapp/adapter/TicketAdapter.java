package com.onecode.tiketsayaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onecode.tiketsayaapp.MyTicketDetailActivity;
import com.onecode.tiketsayaapp.R;
import com.onecode.tiketsayaapp.model.MyTicket;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTicket> myTicket;

    public TicketAdapter(Context c, ArrayList<MyTicket> p){
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.xnama_wisata.setText(myTicket.get(position).getNama_wisata());
        holder.xlokasi.setText(myTicket.get(position).getLokasi());
        holder.xjumlah_tiket.setText(myTicket.get(position).getJumlah_tiket() + " Tickets");

        final String getNamaWisata = myTicket.get(position).getNama_wisata();
        final String getJumlahTiket = myTicket.get(position).getJumlah_tiket();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototicketdetail = new Intent(context, MyTicketDetailActivity.class);
                gototicketdetail.putExtra("nama_wisata", getNamaWisata);
                gototicketdetail.putExtra("jumlah_tiket", getJumlahTiket);

                context.startActivity(gototicketdetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_wisata, xjumlah_tiket, xlokasi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
            xlokasi = itemView.findViewById(R.id.xlokasi);
        }
    }
}
