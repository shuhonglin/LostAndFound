package com.moonlight.nasa.lostandfound;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moonlight.nasa.lostandfound.Util.Constants;
import com.moonlight.nasa.lostandfound.Util.LAFApplication;
import com.moonlight.nasa.lostandfound.adapter.GoodItemRecyclerViewAdapter;
import com.moonlight.nasa.lostandfound.interfaces.OnItemClickListener;
import com.moonlight.nasa.lostandfound.interfaces.OnItemLongClickListener;
import com.special.ResideMenu.ResideMenu;
import com.squareup.leakcanary.RefWatcher;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by NaSa on 2015/7/12.
 */
public class ThirdFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private RecyclerView mRecyclerView;
    private GoodItemRecyclerViewAdapter goodItemRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Map.Entry<String,Object>> goodItemDataSet = new ArrayList<Map.Entry<String,Object>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(parentView == null) {
            parentView = inflater.inflate(R.layout.third_fragment, container, false);
            setUpViews();
        }
        ViewGroup parent = (ViewGroup)parentView.getParent();
        if(parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }

    private void setUpViews() {
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.good_item_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        goodItemRecyclerViewAdapter = new GoodItemRecyclerViewAdapter(getActivity(),goodItemDataSet);
        mRecyclerView.setAdapter(goodItemRecyclerViewAdapter);

        List<Map.Entry<String,Object>> tmp = new ArrayList<>();
        for(int i=0;i<10;i++) {
            tmp.add(new AbstractMap.SimpleEntry(String.valueOf(Constants.NORMAL_VIEW), i));
        }
        goodItemRecyclerViewAdapter.addAll(tmp);

        goodItemRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click on item " + position, Toast.LENGTH_SHORT).show();
            }
        });

        goodItemRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(getActivity(), "long click on item " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        parentView = null;
        //检测内存泄露
        RefWatcher refWatcher = LAFApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
