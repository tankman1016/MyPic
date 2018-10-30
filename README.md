# MyPic
相册（有序的选择图片）



# 1.需要添加的项目依赖 
```
 //recyclerview
 implementation 'com.android.support:recyclerview-v7:27.1.1'
 //glide v3
 implementation 'com.github.bumptech.glide:glide:3.7.0'
 //放大图片
 implementation 'com.github.chrisbanes:PhotoView:2.1.4'
```
# 2.相册入口MainActivity（只有两个按钮）

这里，初始化获取权限-->

1.0版本只支持相册，所以这里只获取 Manifest.permission.WRITE_EXTERNAL_STORAGE 

# 3.图片列表页（进入时，需要传选择图片的个数）

```
   /**
     * @param selectImgNumber 需要选择的个数
     * @return
     */
    public static Intent getStartIntent(Context context, int selectImgNumber) {
        Intent intent = new Intent(context, PicAty.class);
        intent.putExtra("selectImgNumber", selectImgNumber);
        return intent;
    }
    
```
a.从手机查询图片获取图片的路径、名字和所属文件夹
  从图片路径中获取所属文件是为了能够切换文件夹用来浏览不同文件夹的图片。
  核心代码如下：
  
```
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
    
```
b.进行图片选择，对图片进行标记，点击完成可返回携带数据返回上一级.

```
public class LocalPicBean implements Parcelable{

    private boolean isSelected = false;
    //选中的标记
    private int selectedSign=0;
    private String title;
    private long time;
    private String imgPath;
    private long size;
    //所属文件夹
    private String belongFileName;
    ···
}   

```

c.点击图片和预览可进入LookImgAty（图片预览页）

# 4.进入图片浏览页 LookImgAty 

```
/**
     * @param context
     * @param selectedPos     viewpager 当前要显示的位置
     * @param localPicBeans   需要预览的imgList
     * @param selectImgNumber 需要选择的图片的总数
     * @return
     */
    public static Intent getStartIntent(Context context, int selectedPos, int selectImgNumber,
                                        ArrayList<LocalPicBean> localPicBeans) {
        Intent intent = new Intent(context, LookImgAty.class);
        intent.putExtra("selectedPos", selectedPos);
        intent.putExtra("selectImgNumber", selectImgNumber);
        intent.putParcelableArrayListExtra("localPicBeans", localPicBeans);
        return intent;
    }
    
```

# 已知问题

a.点击图片可能闪烁
b.没有拍照




    
    
    
  
    
  









