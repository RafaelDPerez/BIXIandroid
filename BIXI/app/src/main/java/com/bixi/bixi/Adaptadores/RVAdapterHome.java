package com.bixi.bixi.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by telynet on 1/5/2017.
 */

public class RVAdapterHome extends RecyclerView.Adapter<RVAdapterHome.OfertaViewHolder> {

    List<Oferta> oferta;
    Context context;
    LayoutInflater inflater;
    private static RecyclerViewClickListener itemListener;

    public RVAdapterHome(List<Oferta> oferta, Activity context, RecyclerViewClickListener itemListener)
    {
        this.oferta = oferta;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RVAdapterHome.OfertaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_layout, parent, false);
        OfertaViewHolder ovh = new OfertaViewHolder(v);


        return ovh;
    }

    @Override
    public void onBindViewHolder(RVAdapterHome.OfertaViewHolder holder, int position) {
        holder.precio.setText(oferta.get(position).getPrecio());
        holder.descripcion.setText(oferta.get(position).getDescripcion());
        holder.titulo.setText(oferta.get(position).getTitulo());
        String url = oferta.get(position).getDireccionImagen();
        Picasso.with(context)
                .load(url)
                .fit()
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return oferta.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class OfertaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        @Nullable
        @BindView(R.id.tvPrecio)
        TextView precio;

        @BindView(R.id.ivOferta)
        ImageView imageView;

        @BindView(R.id.tvDescripcion)
        TextView descripcion;

        @BindView(R.id.tvTitulo)
        TextView titulo;
        public OfertaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }



        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v,this.getLayoutPosition());
        }
    }


}

