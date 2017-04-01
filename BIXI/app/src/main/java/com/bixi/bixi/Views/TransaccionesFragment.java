package com.bixi.bixi.Views;


import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bixi.bixi.Adaptadores.RVAdapterTransacciones;
import com.bixi.bixi.Interfaces.TransaccionesFragmentView;
import com.bixi.bixi.Interfaces.TransaccionesPresenter;
import com.bixi.bixi.Pojos.HistorialPojo;
import com.bixi.bixi.Pojos.ResultHistoricalPojo;
import com.bixi.bixi.Presenter.TransaccionesPresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bixi.bixi.R.id.swipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransaccionesFragment extends Fragment implements TransaccionesFragmentView {

    @BindView(R.id.rvTransacciones)
    RecyclerView rv;

    @BindView(R.id.progressBarHome)
    ProgressBar bar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static TransaccionesFragment instance;
    private View view;
    private String token;
    private TransaccionesPresenter presenter;

    public TransaccionesFragment() {
        // Required empty public constructor
    }

    public static TransaccionesFragment getInstance()
    {
        if(instance == null)
            instance =  new TransaccionesFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadView(inflater,container);
        ButterKnife.bind(this,view);
        presenter = new TransaccionesPresenterImpl(this);
        return view;
    }

    private void loadView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_transacciones, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            token = extras.getString(Constants.extraToken);
        }

        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.BLACK)
                        .build());

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);


        presenter.getHistoricalFromServer(token);
        swipeRefreshRV();

    }

    private void swipeRefreshRV()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    private void refreshItems() {
        presenter.getHistoricalFromServer(token);
    }

    @Override
    public void error() {
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void success(HistorialPojo pojo) {
        List<ResultHistoricalPojo> obj = pojo.getResult();
        if(obj != null && obj.size() > 0)
        {
            RVAdapterTransacciones adapter = new RVAdapterTransacciones(obj,getInstance().getActivity());
            rv.setAdapter(adapter);
        }

        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showPB() {
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePB() {
        bar.setVisibility(View.INVISIBLE);
    }
}
