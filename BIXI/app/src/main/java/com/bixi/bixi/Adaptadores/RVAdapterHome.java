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
import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by telynet on 1/5/2017.
 */

public class RVAdapterHome extends RecyclerView.Adapter<RVAdapterHome.OfertaViewHolder> {

    List<ResultProductsJson> oferta;
    Context context;
    LayoutInflater inflater;
    private static RecyclerViewClickListenerHome itemListener;
    private List<String> arrayImgs = new ArrayList<String>();

    public RVAdapterHome(List<ResultProductsJson> oferta, Activity context, RecyclerViewClickListenerHome itemListener)
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

    private void loadImage(ImageView img, String url)
    {
        Picasso.with(context)
                .load(url)
                .fit()
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(img);
    }

    @Override
    public void onBindViewHolder(final RVAdapterHome.OfertaViewHolder holder, final int position) {

        ResultProductsJson obj = oferta.get(position);

        if(obj!= null)
        {

          //  holder.precio.setText(oferta.get(position).getPrecio());

            if(obj.getCommerceAddress() != null)
                holder.descripcion.setText(obj.getCommerceAddress());

            if(obj.getCommerceName() != null)
                holder.titulo.setText(obj.getCommerceName());

            String url = oferta.get(position).getProducts().get(0).get(0).getImages().get(0);
            oferta.get(position).setOferDisplay(0);
            loadImage(holder.imageView,url);

            final int posi = position;

            holder.imgGoRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(oferta.get(position).getOferDisplay()< oferta.get(position).getMaxquantityOffers() - 1)
                    {
                        int currentPosition = oferta.get(position).getOferDisplay();
                        currentPosition ++;
                        loadImage(holder.imageView,oferta.get(posi).getProducts().get(0).get(currentPosition).getImages().get(0));
                        oferta.get(position).setOferDisplay(currentPosition);
                    }

                }
            });

            holder.imgGoLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(oferta.get(position).getOferDisplay() > 0)
                    {
                        int currentPosition = oferta.get(position).getOferDisplay();
                        currentPosition --;
                        loadImage(holder.imageView,oferta.get(posi).getProducts().get(0).get(currentPosition).getImages().get(0));
                        oferta.get(position).setOferDisplay(currentPosition);
                    }
                }
            });
        }
        holder.bind(oferta.get(position));

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

        @BindView(R.id.imageView3)
        ImageView imgGoRight;

        @BindView(R.id.imageView4)
        ImageView imgGoLeft;

        private ResultProductsJson resultProductsJson;


        @BindView(R.id.tvTitulo)
        TextView titulo;
        public OfertaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        public void bind(ResultProductsJson item) {
            resultProductsJson = item;
            // Update the ViewHolder to the item's specifications.
        }


        @Override
        public void onClick(View v) {

            itemListener.recyclerViewListClicked(v,this.getLayoutPosition(),resultProductsJson);
        }
    }

}

