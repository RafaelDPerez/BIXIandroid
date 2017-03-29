package com.bixi.bixi.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class RVAdapterHomeLikeIt extends RecyclerView.Adapter<RVAdapterHomeLikeIt.OfertasLikeItViewHolder>
{
    List<ResultProductsLikerItJson> oferta;
    Context context;
    LayoutInflater inflater;
    private static RecyclerViewClickListenerHome itemListener;
    private static boolean likeIt = false;

    public RVAdapterHomeLikeIt(List<ResultProductsLikerItJson> oferta, Activity context, RecyclerViewClickListenerHome itemListener)
    {
        this.likeIt = likeIt;
        this.oferta = oferta;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public OfertasLikeItViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_layout, parent, false);
        OfertasLikeItViewHolder ovh = new OfertasLikeItViewHolder(v);
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
    public void onBindViewHolder(OfertasLikeItViewHolder holder, final int position) {
        ResultProductsLikerItJson obj = oferta.get(position);
        if(obj != null)
        {
            if(obj.getDescription() != null)
                holder.descripcion.setText(obj.getDescription());
            if(obj.getName() != null)
                holder.titulo.setText(obj.getName());
            if(obj.getImages() != null && obj.getImages().size() > 0)
                loadImage(holder.imageView,obj.getImages().get(0));
            else
                loadImage(holder.imageView,"http://static.viagrupo.com/userupload/vargas01-04.png");
            holder.imgGoLeft.setVisibility(View.INVISIBLE);
            holder.imgGoRight.setVisibility(View.INVISIBLE);

            holder.imgLike.setImageResource(R.mipmap.likeit);

            holder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                itemListener.recyclerViewRemoveItem(v,position);
                }
            });
            if(obj.getPoints() != null)
                holder.tvBixiPoints.setText(obj.getPoints()+"B");

        }
    }

    @Override
    public int getItemCount() {
        return oferta.size();
    }

    public void removeItem(int position)
    {
        oferta.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,oferta.size());
    }


    public static class OfertasLikeItViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
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

        @BindView(R.id.imageView2)
        ImageView imgLike;

        @BindView(R.id.tvBixiPoints)
        TextView tvBixiPoints;

        private ResultProductsLikerItJson resultProductsJson;


        @BindView(R.id.tvTitulo)
        TextView titulo;
        public OfertasLikeItViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        public void bind(ResultProductsLikerItJson item) {
            resultProductsJson = item;
            // Update the ViewHolder to the item's specifications.
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            itemListener.recyclerViewClicked(v,this.getLayoutPosition());
        }
    }
}
