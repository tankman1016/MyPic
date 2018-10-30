package com.wei.mypic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.mypic.R;
import com.wei.mypic.bean.LocalPicBean;
import com.wei.mypic.utils.ImageUtils;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<LocalPicBean> localPicBeans;

    public MainAdapter(Context context, List<LocalPicBean> localPicBeans) {
        this.context = context;
        this.localPicBeans = localPicBeans;
    }

    public void setData(List<LocalPicBean> localPicBeans) {
        this.localPicBeans = localPicBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThisViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThisViewHolder) {
            ImageUtils.loadLocalSmallPic(context, localPicBeans.get(position).getImgPath(), ((ThisViewHolder) holder).iv_img);
            ((ThisViewHolder) holder).tv_select.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        if (localPicBeans == null) {
            return 0;
        }
        return localPicBeans.size();
    }


    private class ThisViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img;
        private TextView tv_select;

        private ThisViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_select = itemView.findViewById(R.id.tv_select);
        }
    }
}
