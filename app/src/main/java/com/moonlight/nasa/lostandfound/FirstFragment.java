package com.moonlight.nasa.lostandfound;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.moonlight.nasa.lostandfound.Util.LAFApplication;
import com.special.ResideMenu.ResideMenu;
import com.squareup.leakcanary.RefWatcher;


import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.list.ViewHolderBase;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by NaSa on 2015/7/12.
 */
public class FirstFragment extends Fragment {

    private View       contentView;
    private ResideMenu resideMenu;

    private ListViewDataAdapter<JsonData> mAdapter;
    private ImageLoader mImageLoader;

    private PtrClassicFrameLayout mPtrFrame;
    private DefaultImageLoadHandler defaultImageLoadHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("FirstFragment____onCreateView");
        if(contentView == null) {
            contentView = inflater.inflate(R.layout.first_fragment, container, false);
            setUpViews();
        }
        return contentView;
    }
    private void setUpViews() {
        //setHeaderTitle(R.string.ptr_demo_block_list_view);
        defaultImageLoadHandler = new DefaultImageLoadHandler(getActivity());
        defaultImageLoadHandler.setImageRounded(true,150.0f);


        mImageLoader = ImageLoaderFactory.create(getActivity(),defaultImageLoadHandler);

        final ListView listView = (ListView) contentView.findViewById(R.id.rotate_header_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    final String url = mAdapter.getItem(position).optString("pic");
                    if (!TextUtils.isEmpty(url)) {
                        //getContext().pushFragmentToBackStack(MaterialStyleFragment.class, url);
                        Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mAdapter = new ListViewDataAdapter<JsonData>();

        mAdapter.setViewHolderClass(this, ViewHolder.class);
        listView.setAdapter(mAdapter);
        mPtrFrame = (PtrClassicFrameLayout) contentView.findViewById(R.id.rotate_header_list_view_frame);
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
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);

    }

    protected void updateData() {

        DemoRequestData.getImageList(new RequestFinishHandler<JsonData>() {
            @Override
            public void onRequestFinish(final JsonData data) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.getDataList().clear();
                        mAdapter.getDataList().addAll(data.optJson("data").optJson("list").toArrayList());
                        mPtrFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }

    private class ViewHolder extends ViewHolderBase<JsonData> {

        private CubeImageView mImageView;

        @Override
        public View createView(LayoutInflater inflater) {
            View v = inflater.inflate(R.layout.list_view_item, null);
            mImageView = (CubeImageView) v.findViewById(R.id.list_view_item_image_view);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return v;
        }

        @Override
        public void showData(int position, JsonData itemData) {
            mImageView.loadImage(mImageLoader, itemData.optString("pic"));
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        System.out.println("FirstFragment____onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在配置变化的时候将这个fragment保存下来
        setRetainInstance(true);
        System.out.println("FirstFragment____onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("FirstFragment____onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("FirstFragment____onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("FirstFragment____onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("FirstFragment____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("FirstFragment____onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) {
            parent.removeView(contentView);
        }
        System.out.println("FirstFragment____onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("FirstFragment____onDestroy");
        contentView = null;
        //检测内存泄露
        RefWatcher refWatcher = LAFApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("FirstFragment____onDetach");
    }
}
