package com.bixi.bixi.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.SimpleMenuPojo;
import com.bixi.bixi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny Gil Mejia on 3/5/17.
 */

public class RVAdapterMenu extends RecyclerView.Adapter<RVAdapterMenu.MenuViewHolder> {

    List<SimpleMenuPojo> menu;
    Context context;
    LayoutInflater inflater;
    private static RecyclerViewClickListener itemListener;

    public RVAdapterMenu(ArrayList<SimpleMenuPojo> menu, Activity context, RecyclerViewClickListener itemListener)
    {
        this.menu = menu;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout,parent,false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(v);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        if(menu.get(position) != null)
        {
            SimpleMenuPojo menuPojo = menu.get(position);
            if(menuPojo.getLabel() != null)
                holder.label.setText(menuPojo.getLabel());

            if(menuPojo.getLabelIcon() != null)
            {
                if(menuPojo.getLabelIcon().equals("0"))
                    holder.labelIcon.setImageResource(R.drawable.home_outline);
                else if(menuPojo.getLabelIcon().equals("1"))
                    holder.labelIcon.setImageResource(R.drawable.account);
                else if(menuPojo.getLabelIcon().equals("2"))
                    holder.labelIcon.setImageResource(R.drawable.heart_outline_white);
                else if(menuPojo.getLabelIcon().equals("3"))
                    holder.labelIcon.setImageResource(R.drawable.google_maps);
                else if(menuPojo.getLabelIcon().equals("4"))
                    holder.labelIcon.setImageResource(R.drawable.table_edit);
                else if(menuPojo.getLabelIcon().equals("5"))
                    holder.labelIcon.setImageResource(R.drawable.exit_to_app);
                else
                {

                }

            }
        }
    }


    @Override
    public int getItemCount() {
        return menu.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.textViewLabel_Menu)
        TextView label;

        @BindView(R.id.imageViewLabelIcon_Menu)
        ImageView labelIcon;

        public MenuViewHolder(View itemView)
        {
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
