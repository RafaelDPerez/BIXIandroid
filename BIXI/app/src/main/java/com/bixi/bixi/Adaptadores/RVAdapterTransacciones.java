package com.bixi.bixi.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.Pojos.ResultHistoricalPojo;
import com.bixi.bixi.R;
import com.google.android.gms.vision.text.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class RVAdapterTransacciones extends  RecyclerView.Adapter<RVAdapterTransacciones.TransaccionesViewHolder> {

    List<ResultHistoricalPojo> historico;
    Context context;
    LayoutInflater inflater;

    public RVAdapterTransacciones(List<ResultHistoricalPojo> historico, Activity context)
    {
        this.historico = historico;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public TransaccionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transacciones_rv_layout,parent,false);
        TransaccionesViewHolder tvh = new TransaccionesViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TransaccionesViewHolder holder, int position) {
        ResultHistoricalPojo obj = historico.get(position);
        if(obj != null)
        {
            if(obj.getCdate() != null)
                holder.tvDate.setText(obj.getCdate());
            if(obj.getDescription() != null)
                holder.tvDescripcion.setText(obj.getDescription());
            if(obj.getCommerceName() != null)
                holder.tvName.setText(obj.getCommerceName());
            if(obj.getPoints() != null)
                holder.tvPoints.setText(obj.getPoints());
        }
    }

    @Override
    public int getItemCount() {
        return historico.size();
    }

    public static class TransaccionesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.tvDate_transacciones)
        TextView tvDate;

        @BindView(R.id.tvDescripcion_transacciones)
        TextView tvDescripcion;

        @BindView(R.id.tvNombre_transacciones)
        TextView tvName;

        @BindView(R.id.tvPoints_transacciones)
        TextView tvPoints;

        public TransaccionesViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

        }
    }
}
