package com.moonlight.nasa.lostandfound;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.moonlight.nasa.lostandfound.Util.Constants;
import com.moonlight.nasa.lostandfound.Util.LAFApplication;
import com.moonlight.nasa.lostandfound.adapter.CardItemAdapter;
import com.special.ResideMenu.ResideMenu;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by NaSa on 2015/7/12.
 */
public class SecondFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    private RecyclerView mRecyclerView;
    private CardItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<JSONObject> cardDataSet = new ArrayList<>();

    private boolean isLoading = false;

    private ProgressBar progressBar;

    private PtrClassicFrameLayout mPtrFrame;

    public static int pageNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("SecondFragment____onCreateView");
        if(parentView == null) {
            parentView = inflater.inflate(R.layout.second_fragment, container, false);
            setUpViews();
        }
        ViewGroup parent = (ViewGroup)parentView.getParent();
        if(parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }

    private void setUpViews() {

        pageNumber = 1;

        progressBar = (ProgressBar) parentView.findViewById(R.id.refresh_progressBar);
        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CardItemAdapter(getActivity(),cardDataSet,R.layout.card_item);
        mRecyclerView.setAdapter(mAdapter);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.TYPE,Constants.HEADER_VIEW);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.add(jsonObject);

        List<JSONObject> tmp = new ArrayList<>();
        for(int i=0;i<20;i++) {
            JSONObject tempJsonObject = new JSONObject();
            try {
                jsonObject.put(Constants.TYPE, Constants.NORMAL_VIEW);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tmp.add(tempJsonObject);
        }
        mAdapter.addAll(tmp);


        mPtrFrame = (PtrClassicFrameLayout) parentView.findViewById(R.id.rotate_header_recycler_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.autoRefresh(false);
//        mPtrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrame.autoRefresh();
//            }
//        }, 100);

        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isLoading && ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition() == cardDataSet.size() - 1) {
                    isLoading = true;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(Constants.TYPE,Constants.FOOTER_VIEW);
                        mAdapter.add(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadMore();
                }
            }
        });
    }

    public void loadMore() {
        UpdateTextTask updateTextTask = new UpdateTextTask(getActivity());
        updateTextTask.execute();
    }

    protected void updateData() {
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.refreshComplete();
                for (int i = 0; i < 3; i++) {
                    try {
                        cardDataSet.add(i + 1, new JSONObject().put(Constants.TYPE, Constants.NORMAL_VIEW));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    public class UpdateTextTask extends AsyncTask<Void,Integer,List<JSONObject>> {
        private Context context;
        UpdateTextTask(Context context) {
            this.context = context;
        }
        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            Toast.makeText(context,"开始执行",Toast.LENGTH_SHORT).show();
        }
        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected List<JSONObject> doInBackground(Void... params) {
            publishProgress(1);
            List<JSONObject> tmp = new ArrayList<>();
            for(int i=0;i<3;i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Constants.TYPE,Constants.NORMAL_VIEW);
                    tmp.add(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return tmp;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行         */
        @Override
        protected void onPostExecute(List<JSONObject> result) {
            isLoading = false;
            mAdapter.remove(mAdapter.getItemCount() - 1);
            mAdapter.addAll(result);
            ++pageNumber;
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            Toast.makeText(context,""+values[0],Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("SecondFragment____onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("SecondFragment____onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("SecondFragment____onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("SecondFragment____onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("SecondFragment____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("SecondFragment____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("SecondFragment____onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("SecondFragment____onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("SecondFragment____onDestroy");
        parentView = null;
        //检测内存泄露
        RefWatcher refWatcher = LAFApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("SecondFragment____onDetach");
    }

}
