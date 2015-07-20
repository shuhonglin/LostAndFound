package com.moonlight.nasa.lostandfound.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moonlight.nasa.lostandfound.R;
import com.moonlight.nasa.lostandfound.UI.GoodItemGridView;
import com.moonlight.nasa.lostandfound.Util.Constants;
import com.moonlight.nasa.lostandfound.Util.GridViewUtils;
import com.moonlight.nasa.lostandfound.interfaces.OnItemClickListener;
import com.moonlight.nasa.lostandfound.interfaces.OnItemLongClickListener;

import java.util.List;
import java.util.Map;

/**
 * Created by NaSa on 2015/7/17.
 */
public class GoodItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Map.Entry<String,Object>> goodItemDataSet;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public GoodItemRecyclerViewAdapter(Context context, List<Map.Entry<String,Object>> goodItemDataSet) {
        this.context = context;
        this.goodItemDataSet = goodItemDataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View v;
        if(viewType == Constants.NORMAL_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_item_with_text_content,parent,false);
            viewHolder = new NormalTextViewHolder(v,onItemClickListener,onItemLongClickListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalTextViewHolder) {
            NormalTextViewHolder nth = (NormalTextViewHolder) holder;
            nth.userIconIV.setImageResource(R.drawable.icon_user);
            nth.nickNameTV.setText("林树宏");
            nth.goodDateTV.setText("3分钟前");
            nth.goodPlatformTV.setText("android客户端");
            nth.goodLocationImageIV.setImageResource(R.drawable.eye);
            nth.goodLocationTV.setText("大学城 广州大学");
            nth.goodDescriptionTV.setText("Map.Entry是Map声明的一个内部接口，此接口为泛型，定义为Entry<K,V>。" +
                    "它表示Map中的一个实体（一个key-value对）。接口中有getKey(),getValue方法。");
            nth.goodImageUrlsGV.setAdapter(new GoodItemGridViewAdapter(context,1));
            // 计算GridView宽度, 设置默认为numColumns为3.
            GridViewUtils.updateGridViewLayoutParams(nth.goodImageUrlsGV,3);

            nth.goodItemTransmitImage.setImageResource(R.drawable.share);
            nth.goodItemTransmitText.setText("3");

            nth.goodItemLeaveWordImage.setImageResource(R.drawable.mail);
            nth.goodItemLeaveWordText.setText("留言");

            nth.goodItemLikeImage.setImageResource(R.drawable.heart);
            nth.goodItemLikeText.setText("9");

        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(goodItemDataSet.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return goodItemDataSet.size();
    }

    public void add(Map.Entry<String,Object> item) {
        goodItemDataSet.add(item);
        notifyItemInserted(goodItemDataSet.size()-1);
    }

    public void addAll(List<Map.Entry<String,Object>> itemList) {
        goodItemDataSet.addAll(itemList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        goodItemDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public class NormalTextViewHolder extends RecyclerView.ViewHolder {

        public ImageView userIconIV;
        public TextView nickNameTV;
        public TextView goodPlatformTV;
        public TextView goodDateTV;
        public TextView goodDescriptionTV;
        public GoodItemGridView goodImageUrlsGV;
        public ImageView goodLocationImageIV;
        public TextView goodLocationTV;

        public LinearLayout goodItemLinearTransmit;
        public ImageView goodItemTransmitImage;
        public TextView goodItemTransmitText;

        public LinearLayout goodItemLinearLeaveWord;
        public ImageView goodItemLeaveWordImage;
        public TextView goodItemLeaveWordText;

        public LinearLayout goodItemLinearLike;
        public ImageView goodItemLikeImage;
        public TextView goodItemLikeText;

        private OnItemClickListener itemClickListener;
        private OnItemLongClickListener itemLongClickListener;

        public NormalTextViewHolder(View itemView, final OnItemClickListener mItemClickListener, final OnItemLongClickListener mItemLongClickListener) {
            super(itemView);

            this.itemClickListener = mItemClickListener;
            this.itemLongClickListener = mItemLongClickListener;

            userIconIV = (ImageView) itemView.findViewById(R.id.good_item_user_icon);
            nickNameTV = (TextView) itemView.findViewById(R.id.good_item_user_name);
            goodPlatformTV = (TextView) itemView.findViewById(R.id.good_item_platform);
            goodDateTV = (TextView) itemView.findViewById(R.id.good_item_date);
            goodDescriptionTV = (TextView) itemView.findViewById(R.id.good_item_description);
            goodImageUrlsGV = (GoodItemGridView) itemView.findViewById(R.id.good_item_gridview);
            goodLocationImageIV = (ImageView) itemView.findViewById(R.id.good_item_location_image);
            goodLocationTV = (TextView) itemView.findViewById(R.id.good_item_location);

            goodItemLinearTransmit = (LinearLayout) itemView.findViewById(R.id.good_item_linear_transmit_btn);
            goodItemTransmitImage = (ImageView) itemView.findViewById(R.id.good_item_transmit_image);
            goodItemTransmitText = (TextView) itemView.findViewById(R.id.good_item_transmit_text);

            goodItemLinearLeaveWord = (LinearLayout) itemView.findViewById(R.id.good_item_linear_leave_word_btn);
            goodItemLeaveWordImage = (ImageView) itemView.findViewById(R.id.good_item_leave_word_image);
            goodItemLeaveWordText = (TextView) itemView.findViewById(R.id.good_item_leave_word_text);

            goodItemLinearLike = (LinearLayout) itemView.findViewById(R.id.good_item_linear_like_btn);
            goodItemLikeImage = (ImageView) itemView.findViewById(R.id.good_item_like_image);
            goodItemLikeText = (TextView) itemView.findViewById(R.id.good_item_like_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        itemClickListener.onItemClick(v,getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemLongClickListener != null) {
                        itemLongClickListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }
            });


            userIconIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "userId"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });

            goodItemLinearLeaveWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "留言", Toast.LENGTH_SHORT).show();
                }
            });

            goodItemLinearLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点赞", Toast.LENGTH_SHORT).show();
                }
            });

            goodItemLinearTransmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "转发", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
