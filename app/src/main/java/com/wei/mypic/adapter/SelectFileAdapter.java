package com.wei.mypic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.mypic.utils.ImageUtils;
import com.wei.mypic.R;
import com.wei.mypic.bean.FileBean;

import java.util.List;

public class SelectFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FileBean> fileBeans;

    public SelectFileAdapter(Context context, List<FileBean> fileBeans) {
        this.context = context;
        this.fileBeans = fileBeans;
    }

    public void setNewData(List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThisViewHolder) {
            ImageUtils.loadLocalVerySmallPic(context, fileBeans.get(position).getImgPath(), ((ThisViewHolder) holder).iv_img);
            if (position==0){
                ((ThisViewHolder) holder).tv_file_name.setText(fileBeans.get(position).getFileName());
            }else {
                ((ThisViewHolder) holder).tv_file_name.setText(fileBeans.get(position).getFileName()+"("+fileBeans.get(position).getSize() +")");
            }
            if (fileBeans.get(position).isSelected()){
                ((ThisViewHolder) holder).iv_select.setVisibility(View.VISIBLE);
            }else {
                ((ThisViewHolder) holder).iv_select.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (fileBeans==null){
            return 0;
        }
        return fileBeans.size();
    }

    private class ThisViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_img;
        private ImageView iv_select;
        private TextView tv_file_name;

        private ThisViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_select = itemView.findViewById(R.id.iv_select);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
        }
    }

    private RecyclerView.ViewHolder createHolder(ViewGroup parent) {
        final ThisViewHolder holder = new ThisViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_file, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mOnItemClickListener!=null){
                   mOnItemClickListener.onItemClick(holder.getAdapterPosition());
               }
            }
        });
        return holder;

    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}
