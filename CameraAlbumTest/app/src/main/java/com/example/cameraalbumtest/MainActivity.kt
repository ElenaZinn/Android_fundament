package com.example.cameraalbumtest

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri:Uri
    lateinit var outputTmage:File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TakePhoto : Button = findViewById(R.id.takephoto)
        val FromAlbum : Button = findViewById(R.id.FromAlbum)

        TakePhoto.setOnClickListener{
            //创建file对象，用于存储拍照后的图片
            outputTmage = File(externalCacheDir,"output_image.jpg")

            if(outputTmage.exists()){
                outputTmage.delete()
            }

            outputTmage.createNewFile()

            imageUri = if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
                FileProvider.getUriForFile(this,"com.example.cameraalbumtest.fileprovider",outputTmage)

            }else{
                Uri.fromFile(outputTmage)
            }
            // 启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent,takePhoto )

        }

        //从相册选择图片
        FromAlbum.setOnClickListener{
            //打开文件选择器
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)  //指定action
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            //指定只显示图片
            intent.type = "image/*"
            startActivityForResult(intent,fromAlbum)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                //Activity.RESULT_OK,activity调用成功
                if(resultCode== Activity.RESULT_OK){
                    //将照片展示出来
                        //将output_image.jpg解析成Bitmap对象
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    val imageView:ImageView = findViewById(R.id.imageView)
                    imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }

            fromAlbum -> {
                if (resultCode==Activity.RESULT_OK && data !=null){
                    data.data?.let{
                        uri -> val bitmap = getBitmapFromUri(uri)
                        val imageView:ImageView = findViewById(R.id.imageView)
                        imageView.setImageBitmap(bitmap)
                    }
                }

            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap):Bitmap{
        val exif = ExifInterface(outputTmage.path)
        //TAG_ORIENTATION方向
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when ( orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap,270)

            else  -> bitmap

        }

    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0, bitmap.width,bitmap.height,matrix,true)
        // recycle 回收位图占用的内存空间，把位图标记为Dead
        bitmap.recycle()
        return  rotatedBitmap
    }

    private fun getBitmapFromUri(uri:Uri) = contentResolver.openFileDescriptor(uri,"r")?.use{
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
}