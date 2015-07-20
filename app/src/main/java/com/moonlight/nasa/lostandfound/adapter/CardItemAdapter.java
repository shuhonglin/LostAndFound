package com.moonlight.nasa.lostandfound.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.moonlight.nasa.lostandfound.R;
import com.moonlight.nasa.lostandfound.Util.Constants;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by NaSa on 2015/7/13.
 */
public class CardItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<JSONObject> cardDataSet;
    private Context context;
    private int itemLayout;

    public CardItemAdapter(Context context, List<JSONObject> mCardDataSet,int itemLayout) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.cardDataSet = mCardDataSet;
        BlurLayout.setGlobalDefaultDuration(450);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v;
        if(viewType == Constants.HEADER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_header,parent,false);
            vh = new HeaderViewHolder(v);
        } else if(viewType == Constants.FOOTER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_footer,parent,false);
            vh = new FooterViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            vh = new NormalViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if(holder instanceof NormalViewHolder) {
                final JSONObject itemData = cardDataSet.get(position);
                NormalViewHolder nv = (NormalViewHolder)holder;
                nv.mTextView.setText("Info Text"+position);
                nv.mTextView2.setText("Info text2222"+position);
                if(position%2 == 1) {
                    nv.mTextView2.setVisibility(View.GONE);
                }
                nv.itemView.setTag(nv);

                nv.hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada)
                                .duration(550)
                                .playOn(v);
                    }
                });
                nv.hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Swing)
                                .duration(550)
                                .playOn(v);
                    }
                });
                nv.blurLayout.setHoverView(nv.hover);
                nv.blurLayout.setBlurDuration(550);
                nv.blurLayout.addChildAppearAnimator(nv.hover, R.id.heart, Techniques.FlipInX, 550, 0);
                nv.blurLayout.addChildAppearAnimator(nv.hover, R.id.share, Techniques.FlipInX, 550, 250);
                nv.blurLayout.addChildAppearAnimator(nv.hover, R.id.more, Techniques.FlipInX, 550, 500);

                nv.blurLayout.addChildDisappearAnimator(nv.hover, R.id.heart, Techniques.FlipOutX, 550, 500);
                nv.blurLayout.addChildDisappearAnimator(nv.hover, R.id.share, Techniques.FlipOutX, 550, 250);
                nv.blurLayout.addChildDisappearAnimator(nv.hover, R.id.more, Techniques.FlipOutX, 550, 0);

                nv.blurLayout.addChildAppearAnimator(nv.hover, R.id.description, Techniques.FadeInUp);
                nv.blurLayout.addChildDisappearAnimator(nv.hover, R.id.description, Techniques.FadeOutDown);

            } else if(holder instanceof FooterViewHolder) {
                FooterViewHolder fv = (FooterViewHolder) holder;
            } else if(holder instanceof HeaderViewHolder) {
                HeaderViewHolder hv = (HeaderViewHolder) holder;
                //hv.bindView(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(cardDataSet == null) {
            return 0;
        }
        return cardDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return cardDataSet.get(position).optInt(Constants.TYPE);
//        if(position == 0) {
//            return HEADER_VIEW;
//        }
//        return super.getItemViewType(position);
    }

    public void add(JSONObject cardData) {
        cardDataSet.add(cardData);
        notifyItemInserted(cardDataSet.size()-1);
    }

    public void addAll(List<JSONObject> cardList) {
        cardDataSet.addAll(cardList);
        notifyDataSetChanged();
    }

    public void remove(int i) {
        cardDataSet.remove(i);
        notifyItemRemoved(i);
    }


    public class HeaderViewHolder extends  RecyclerView.ViewHolder {

        public ImageView mImageView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.card_header_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Click header",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class NormalViewHolder extends  RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextView2;
        public CardView mCardView;
        public ImageView mUserIcon;
        public BlurLayout blurLayout;
        public View hover;
        public NormalViewHolder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.card_view);
            mTextView = (TextView) view.findViewById(R.id.info_text);
            mTextView2 = (TextView) view.findViewById(R.id.info_text_2);
            mUserIcon = (ImageView) view.findViewById(R.id.card_user_icon);
            blurLayout = (BlurLayout) view.findViewById(R.id.blur_layout);
            hover = LayoutInflater.from(context).inflate(R.layout.card_item_hover, null);
        }
    }

    public class FooterViewHolder extends  RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public FooterViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.card_footer_progressBar);
        }
    }
}
