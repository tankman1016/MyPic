package com.wei.mypic.aty;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wei.mypic.AppInit;
import com.wei.mypic.bean.FileBean;
import com.wei.mypic.bean.LocalPicBean;
import com.wei.mypic.adapter.PicAdapter;
import com.wei.mypic.R;
import com.wei.mypic.adapter.SelectFileAdapter;
import com.wei.mypic.utils.SizeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片列表页
 */
public class PicAty extends AppCompatActivity {

    private TextView tv_img_size;
    private RecyclerView recyclerView;
    private PicAdapter picAdapter;
    private TextView tv_file_name;
    private TextView tv_look;
    private View view_line;
    private View popupView;
    private PopupWindow popupWindow;
    private boolean isShowPopupWindow = false;
    private RecyclerView rvFileName;
    private SelectFileAdapter selectFileAdapter;

    private List<LocalPicBean> imgList = new ArrayList<>();
    private List<FileBean> fileBeans = new ArrayList<>();
    private int selectImgNumber;


    /**
     * @param selectImgNumber 需要选择的个数
     * @return
     */
    public static Intent getStartIntent(Context context, int selectImgNumber) {
        Intent intent = new Intent(context, PicAty.class);
        intent.putExtra("selectImgNumber", selectImgNumber);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pic);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectImgNumber = getIntent().getIntExtra("selectImgNumber", 1);
        tv_img_size = findViewById(R.id.tv_img_size);
        tv_img_size.setText("完成(0/" + selectImgNumber + ")");
        tv_img_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<LocalPicBean> lookLocalPicBeans = new ArrayList<>();
                for (int i = 0; i < picAdapter.getLocalPicBeans().size(); i++) {
                    if (picAdapter.getLocalPicBeans().get(i).isSelected()) {
                        lookLocalPicBeans.add(picAdapter.getLocalPicBeans().get(i));
                    }
                }
                if (lookLocalPicBeans.size() > 0) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("list", lookLocalPicBeans);
                    setResult(RESULT_OK, intent);
                }
                finish();

            }
        });

        view_line = findViewById(R.id.view_line);
        tv_file_name = (TextView) findViewById(R.id.tv_file_name);
        tv_file_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(view_line);
            }
        });
        tv_look = findViewById(R.id.tv_look);
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<LocalPicBean> lookLocalPicBeans = new ArrayList<>();
                for (int i = 0; i < picAdapter.getLocalPicBeans().size(); i++) {
                    if (picAdapter.getLocalPicBeans().get(i).isSelected()) {
                        lookLocalPicBeans.add(picAdapter.getLocalPicBeans().get(i));
                    }
                }
                if (lookLocalPicBeans.size() != 0) {
                    startActivityForResult((LookImgAty.getStartIntent(PicAty.this,
                            0, selectImgNumber, lookLocalPicBeans)), 1002);
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        picAdapter = new PicAdapter(this, null, selectImgNumber);

        picAdapter.setOnItemChildClickListener(new PicAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(int position) {

                List<LocalPicBean> localPicBeans = picAdapter.getLocalPicBeans();

                if (localPicBeans.get(position).isSelected()) {
                    //取消选中并改变已选中的标记 比当前大的标记-1 比当前小的标记不做处理
                    int selectNum = 0;
                    localPicBeans.get(position).setSelected(false);
                    for (int i = 0; i < localPicBeans.size(); i++) {
                        if (localPicBeans.get(i).isSelected()) {
                            selectNum = selectNum + 1;
                            if (localPicBeans.get(i).getSelectedSign() > localPicBeans.get(position).getSelectedSign()) {
                                localPicBeans.get(i).setSelectedSign(localPicBeans.get(i).getSelectedSign() - 1);
                            }
                        }
                    }
                    tv_img_size.setText("完成(" + selectNum + "/" + selectImgNumber + ")");

                } else {
                    //选中并设置标记 标记根据已选择的个数
                    int selectNum = 0;
                    for (int i = 0; i < localPicBeans.size(); i++) {
                        if (localPicBeans.get(i).isSelected()) {
                            selectNum = selectNum + 1;
                        }
                    }
                    if (selectNum < selectImgNumber) {
                        selectNum = selectNum + 1;
                        localPicBeans.get(position).setSelected(true);
                        localPicBeans.get(position).setSelectedSign(selectNum);
                    }
                    tv_img_size.setText("完成(" + selectNum + "/" + selectImgNumber + ")");
                }
                //最后数据更新
                picAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(picAdapter);

        popupView = LayoutInflater.from(this).inflate(R.layout.popup_pic_file, null);
        rvFileName = popupView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFileName.setLayoutManager(linearLayoutManager);
        selectFileAdapter = new SelectFileAdapter(this, null);
        selectFileAdapter.setOnItemClickListener(new SelectFileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    //选择了全部图片
                    tv_file_name.setText("全部图片");
                    picAdapter.setData(imgList);
                    popupWindow.dismiss();
                } else {
                    List<LocalPicBean> newImageList = new ArrayList<>();
                    for (int i = 0; i < imgList.size(); i++) {
                        if (imgList.get(i).getBelongFileName().equals(fileBeans.get(position).getFileName())) {
                            newImageList.add(imgList.get(i));
                        }
                    }
                    tv_file_name.setText(fileBeans.get(position).getFileName());
                    picAdapter.setData(newImageList);
                    popupWindow.dismiss();
                }
                //设置选择的文件夹
                for (int i = 0; i < fileBeans.size(); i++) {
                    fileBeans.get(i).setSelected(false);
                }
                fileBeans.get(position).setSelected(true);
                selectFileAdapter.notifyDataSetChanged();
            }
        });
        rvFileName.setAdapter(selectFileAdapter);
        set1pxDivider(rvFileName);

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.BottomPopupAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isShowPopupWindow = false;
            }
        });

        query();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == RESULT_OK) {
            if (data != null) {
                Log.v("Lin2", "pic收到的反馈:data!=null");
                if (data.getBooleanExtra("OnClickCompleted", false)) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("list", data.getParcelableArrayListExtra("list"));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    //刷新选中的状态
                    List<LocalPicBean> lookListSelected = data.getParcelableArrayListExtra("list");

                    for (int i = 0; i < picAdapter.getItemCount(); i++) {
                        //全部设置为未选中状态
                        picAdapter.getLocalPicBeans().get(i).setSelected(false);
                        for (int j = 0; j < lookListSelected.size(); j++) {
                            //路径相同则判断为同一图片
                            if (picAdapter.getLocalPicBeans().get(i).getImgPath().equals(lookListSelected.get(j).getImgPath())) {
                                picAdapter.getLocalPicBeans().get(i).setSelected(true);
                                picAdapter.getLocalPicBeans().get(i).setSelectedSign(lookListSelected.get(j).getSelectedSign());
                            }
                        }

                    }

                    picAdapter.notifyDataSetChanged();
                    tv_img_size.setText("完成(" + picAdapter.getItemCount() + "/" + selectImgNumber + ")");
                }
            } else {
                Log.v("Lin2", "pic收到的反馈:data=null");
            }
        }
    }

    //查询所有图片
    private void query() {

        Cursor cursor = AppInit.getContextObject().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //获取图片的名称
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //获取图片的最后修改日期
                long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                //获取图片的路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                //获取图片的大小
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));

                imgList.add(new LocalPicBean(name, time, path, size, getFileNameFromPath(path)));
                Log.v("Lin", "文件夹的名字：" + getFileNameFromPath(path));
                handlePath(fileBeans, path);
            }
            cursor.close();
        }

        //添加全部照片
        FileBean fileBean = new FileBean();
        fileBean.setImgPath(imgList.get(0).getImgPath());
        fileBean.setFileName("全部图片");
        fileBean.setSelected(true);
        fileBean.setSize(imgList.size());
        fileBeans.add(0, fileBean);

        selectFileAdapter.setNewData(fileBeans);
        picAdapter.setData(imgList);
    }

    private void showPopUp(View v) {
        Log.v("Lin", "显示Popup");
        if (isShowPopupWindow) {
            popupWindow.dismiss();
            return;
        }
        popupWindow.showAsDropDown(v, 0, -SizeUtil.dp2px(this, 200), Gravity.TOP);
        isShowPopupWindow = true;
    }

    /**
     * 这里获取如/aaaaa/bbbbbb/ccccc/XXXX/dddd.img路径中的XXXX的名字
     */
    private String getFileNameFromPath(String path) {

        // XXXX 前边的/的位置
        int nameStart = 0;
        // XXXX 后边的/的位置
        int nameEnd = 0;

        if (path.contains("/")) {
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == '/') {
                    nameStart = nameEnd;
                    nameEnd = i;
                }
            }
            return path.substring(nameStart + 1, nameEnd);
        }
        return "";
    }

    /**
     * 处理文件路径
     *
     * @param fileBeans
     * @param path
     */

    private void handlePath(List<FileBean> fileBeans, String path) {
        if (fileBeans.size() == 0) {
            //没有数据直接添加
            fileBeans.add(new FileBean(path, getFileNameFromPath(path)));
        } else {
            //有数据时，判断是不是含有要添加的文件夹含有直接+1 不含有直接添加
            boolean isHave = false;
            for (int i = 0; i < fileBeans.size(); i++) {
                if (fileBeans.get(i).getFileName().equals(getFileNameFromPath(path))) {
                    fileBeans.get(i).addSize();
                    isHave = true;
                }
            }
            if (!isHave) {
                fileBeans.add(new FileBean(path, getFileNameFromPath(path)));
            }
        }
    }

    //分割线(1px)
    public void set1pxDivider(RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_1px));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

//    //获取view的宽 高
//    public int[] getDisplayViewSize(View view) {
//        int size[] = new int[2];
//        int width = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        int height = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        view.measure(width, height);
//        size[0] = view.getMeasuredWidth();
//        size[1] = view.getMeasuredHeight();
//        return size;
//    }

}
