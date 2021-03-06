package com.bixi.bixi.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.R;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.squareup.picasso.Callback;
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
    private boolean horizontal = false;
    private boolean showLikeButton = false;

    public RVAdapterHome(List<ResultProductsJson> oferta, Activity context, RecyclerViewClickListenerHome itemListener)
    {
        this.oferta = oferta;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);

    }

    public RVAdapterHome(List<ResultProductsJson> oferta, Activity context, RecyclerViewClickListenerHome itemListener, boolean horizontal)
    {
        this.oferta = oferta;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);
        this.horizontal = horizontal;

    }

    public RVAdapterHome(List<ResultProductsJson> oferta, Activity context, RecyclerViewClickListenerHome itemListener, boolean horizontal, boolean showLike)
    {
        this.oferta = oferta;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);
        this.horizontal = horizontal;
        this.showLikeButton = showLike;
    }

    @Override
    public RVAdapterHome.OfertaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourse = 0;
        if(!horizontal)
            resourse = R.layout.home_recycler_layout;
        else
            resourse = R.layout.home_recycler_layout_horizontal;

        View v = LayoutInflater.from(parent.getContext()).inflate(resourse, parent, false);
        OfertaViewHolder ovh = new OfertaViewHolder(v);

        ApplyCustomFont.applyFont(context,v.findViewById(R.id.home_recycler_id),"fonts/Corbel.ttf");

        return ovh;
    }

    private void loadImage(ImageView img, String url,final ProgressBar pbar)
    {
        if(url != null && !url.isEmpty())
        {
            Picasso.with(context)
                    .load(url)
                    .fit()
                    .placeholder(R.color.colorAccent)
                    .error(R.color.colorAccent)
                    .into(img, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    });
        }else
        {
            Picasso.with(context)
                    .load(R.color.colorAccent)
                    .fit()
                    .placeholder(R.color.colorAccent)
                    .error(R.color.colorAccent)
                    .into(img, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    });
        }

    }



    @Override
    public void onBindViewHolder(final RVAdapterHome.OfertaViewHolder holder, final int position) {

        ResultProductsJson obj = oferta.get(position);

        if(obj!= null)
        {

            if(showLikeButton)
                holder.imgLiket.setVisibility(View.VISIBLE);

          //  holder.precio.setText(oferta.get(position).getPrecio());

            if(obj.getCommerceAddress() != null)
                holder.descripcion.setText(obj.getProducts().get(0).getName());

            if(obj.getCommerceName() != null)
                holder.titulo.setText(obj.getCommerceName());

            String url = "";
            if(oferta.get(position).getProducts().get(0).getImages() != null && oferta.get(position).getProducts().get(0).getImages().size() > 0)
                url = oferta.get(position).getProducts().get(0).getImages().get(0);

            oferta.get(position).setOferDisplay(0);

            holder.pb.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.purpleBixi2), PorterDuff.Mode.MULTIPLY);
            holder.pb.setVisibility(View.VISIBLE);

            loadImage(holder.imageView,url,holder.pb);
            holder.tvBixiPoints.setText(oferta.get(position).getProducts().get(0).getPoints());

            if(oferta.get(position).getProducts().get(0).getIsFavorite())
            {
                setLikes(holder.imgLiket,true);
            }

            final int posi = position;

            if(oferta.get(posi).getMaxquantityOffers() == 1)
            {
                updateArrows(holder.imgGoLeft,false);
                updateArrows(holder.imgGoRight,false);
            }
            if(oferta.get(posi).getOferDisplay() == 0)
            {
                updateArrows(holder.imgGoLeft,false);
            }

            holder.imgGoRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(oferta.get(position).getOferDisplay()< oferta.get(position).getMaxquantityOffers() - 1)
                    {
                        int currentPosition = oferta.get(position).getOferDisplay();
                        currentPosition ++;
                        holder.pb.setVisibility(View.VISIBLE);
                        holder.descripcion.setText(oferta.get(posi).getProducts().get(currentPosition).getName());

                        if(oferta.get(posi).getProducts().get(currentPosition).getIsFavorite())
                            setLikes(holder.imgLiket,true);
                        else
                            setLikes(holder.imgLiket,false);

                        if(oferta.get(posi).getProducts().get(currentPosition).getImages() != null && oferta.get(posi).getProducts().get(currentPosition).getImages().size() > 0)
                            loadImage(holder.imageView,oferta.get(posi).getProducts().get(currentPosition).getImages().get(0),holder.pb);
                        else
                            loadImage(holder.imageView,null,holder.pb);

                        oferta.get(position).setOferDisplay(currentPosition);
                        holder.tvBixiPoints.setText(oferta.get(posi).getProducts().get(currentPosition).getPoints());

                        if(oferta.get(position).getOferDisplay() == oferta.get(position).getMaxquantityOffers() - 1)
                        {
                            updateArrows(holder.imgGoRight,false);
                            updateArrows(holder.imgGoLeft,true);
                        }else
                        {
                            updateArrows(holder.imgGoRight,true);
                            updateArrows(holder.imgGoLeft,true);
                        }
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
                        holder.pb.setVisibility(View.VISIBLE);
                        holder.descripcion.setText(oferta.get(posi).getProducts().get(currentPosition).getName());

                        if(oferta.get(posi).getProducts().get(currentPosition).getIsFavorite())
                            setLikes(holder.imgLiket,true);
                        else
                            setLikes(holder.imgLiket,false);

                        if(oferta.get(posi).getProducts().get(currentPosition).getImages() != null && oferta.get(posi).getProducts().get(currentPosition).getImages().size() > 0)
                            loadImage(holder.imageView,oferta.get(posi).getProducts().get(currentPosition).getImages().get(0),holder.pb);
                        else
                            loadImage(holder.imageView,null,holder.pb);

                        oferta.get(position).setOferDisplay(currentPosition);
                        holder.tvBixiPoints.setText(oferta.get(posi).getProducts().get(currentPosition).getPoints());

                        if(oferta.get(position).getOferDisplay() == 0)
                        {
                            updateArrows(holder.imgGoRight,true);
                            updateArrows(holder.imgGoLeft,false);
                        }else
                        {
                            updateArrows(holder.imgGoRight,true);
                            updateArrows(holder.imgGoLeft,true);
                        }
                    }
                }
            });



            holder.imgLiket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int supPosi = oferta.get(position).getOferDisplay();
                    if(oferta.get(posi).getProducts().get(supPosi).getIsFavorite())
                    {
                        oferta.get(posi).getProducts().get(supPosi).setIsFavorite("0");
                        setLikes(holder.imgLiket,false);
                        itemListener.recyclerViewLiketItem(v,position,oferta.get(position),false);
                    }else
                    {
                        oferta.get(posi).getProducts().get(supPosi).setIsFavorite("1");
                        setLikes(holder.imgLiket,true);
                        itemListener.recyclerViewLiketItem(v,position,oferta.get(position),true);
                    }


                }
            });

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.recyclerViewListClicked(v,position,oferta.get(posi));
                }
            });
        }
        holder.bind(oferta.get(position));

    }

    private void setLikes(ImageView imageView, boolean likeIt)
    {
        if(likeIt)
            imageView.setImageResource(R.mipmap.likeit);
        else
            imageView.setImageResource(R.drawable.heart_outline);
    }

    private void updateArrows(ImageView img, boolean show)
    {
        if(show)
            img.setVisibility(View.VISIBLE);
        else
            img.setVisibility(View.INVISIBLE);
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

        @BindView(R.id.imageView2)
        ImageView imgLiket;

        @BindView(R.id.tvBixiPoints)
        TextView tvBixiPoints;

        @BindView(R.id.progressBar3)
        ProgressBar pb;


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

