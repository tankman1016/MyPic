# MyPic
相册（有序的选择图片）

# 1.添加项目依赖
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
下图为详细的逻辑和过程：







