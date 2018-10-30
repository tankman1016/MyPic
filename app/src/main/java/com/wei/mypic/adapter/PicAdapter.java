package com.wei.mypic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.mypic.aty.LookImgAty;
import com.wei.mypic.utils.ImageUtils;
import com.wei.mypic.R;
import com.wei.mypic.bean.LocalPicBean;

import java.util.ArrayList;
import java.util.List;

public class PicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<LocalPicBean> localPicBeans;
    private int selectImgNumber;

   public PicAdapter(Context context, List<LocalPicBean> localPicBeans,int selectImgNumber){
       this.context=context;
       this.localPicBeans=localPicBeans;
       this.selectImgNumber=selectImgNumber;
   }

    public List<LocalPicBean> getLocalPicBeans() {
        return localPicBeans;
    }

    public void setData(List<LocalPicBean> localPicBeans){
       this.localPicBeans=localPicBeans;
       notifyDataSetChanged();
   }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThisViewHolder){
             ImageUtils.loadLocalSmallPic(context,localPicBeans.get(position).getImgPath(),((ThisViewHolder) holder).iv_img);
             if (localPicBeans.get(position).isSelected()){
                 ((ThisViewHolder) holder).tv_select.setBackgroundResource(R.drawable.bg_selected);
                 ((ThisViewHolder) holder).tv_select.setText(localPicBeans.get(position).getSelectedSign()+"");
             }else {
                 ((ThisViewHolder) holder).tv_select.setBackgroundResource(R.drawable.bg_select);
                 ((ThisViewHolder) holder).tv_select.setText("");
             }
        }
    }

    @Override
    public int getItemCount() {
        return localPicBeans.size();
    }

    private class ThisViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_img;
        private TextView tv_select;
        private ThisViewHolder(View itemView) {
            super(itemView);
            iv_img=itemView.findViewById(R.id.iv_img);
            tv_select=itemView.findViewById(R.id.tv_select);
        }
    }

    private OnItemChildClickListener mOnItemChildClickListener;

    public interface OnItemChildClickListener {
        void onItemChildClick(int position);
    }
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    private RecyclerView.ViewHolder createHolder(ViewGroup parent){
        final ThisViewHolder holder=new ThisViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pic, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String imgSize=new BigDecimal(localPicBeans.get(holder.getAdapterPosition()).getSize())
//                        .divide(new BigDecimal(1048576),2, RoundingMode.HALF_UP).toString()+"M";
//                String info="标题："+localPicBeans.get(holder.getAdapterPosition()).getTitle()+"\n路径："+
//                        localPicBeans.get(holder.getAdapterPosition()).getImgPath()+"\n大小："+
//                        imgSize;
//                context.startActivity(ImageInfoAty.getStartIntent(context,info));

                ((Activity)context).startActivityForResult((LookImgAty.getStartIntent(context,holder.getAdapterPosition(),selectImgNumber,
                        (ArrayList<LocalPicBean>) localPicBeans)),1002);

            }
        });
        holder.tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemChildClickListener!=null){
                    mOnItemChildClickListener.onItemChildClick(holder.getAdapterPosition());
                }

            }
        });

        return holder;
    }

}
