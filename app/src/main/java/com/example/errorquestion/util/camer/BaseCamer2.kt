package com.example.errorquestion.util.camer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper.getMainLooper
import android.util.SparseIntArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import com.example.errorquestion.MyApplication
import com.example.errorquestion.R
import com.example.errorquestion.activity.EnterErrorActivity
import com.example.errorquestion.util.showToast
import kotlinx.android.synthetic.main.activity_enter_error.*
import java.nio.ByteBuffer
import java.util.*


@SuppressLint("StaticFieldLeak")
object BaseCamer2 {
    /**
     * 摄像头创建监听
     */
    private var mCameraDevice: CameraDevice? = null
    private var childHandler: Handler? = null
    private var mainHandler: Handler? = null
    private var mCameraID //摄像头Id 0 为后  1 为前
            : String = CameraCharacteristics.LENS_FACING_BACK.toString()
    lateinit var mImageReader: ImageReader
    private var mCameraManager //摄像头管理器
            : CameraManager? = null
    lateinit var mSurfaceHolder: SurfaceHolder
    lateinit var mCameraCaptureSession: CameraCaptureSession
    private lateinit var surfaveView: SurfaceView
    private val ORIENTATIONS = SparseIntArray()
    private lateinit var content: EnterErrorActivity

    fun initSurface(surfaceView1: EnterErrorActivity, surfaceView: SurfaceView) {
        surfaveView = surfaceView
        content = surfaceView1
        mSurfaceHolder = surfaceView.holder
        mSurfaceHolder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
//                cameraManager.openCamera(mCameraID, stateCallback, null);
                initCamera2()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })
    }

    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) { //打开摄像头
            mCameraDevice = camera
            //开启预览
            takePreview()
        }

        override fun onDisconnected(camera: CameraDevice) { //关闭摄像头
            if (null != mCameraDevice) {
                mCameraDevice!!.close()
                mCameraDevice = null
            }
        }

        override fun onError(camera: CameraDevice, error: Int) { //发生错误
            MyApplication.context.resources.getString(R.string.camer_get_error)
                .showToast(MyApplication.context)
        }
    }

    /**
     * 初始化Camera2
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun initCamera2() {
        val handlerThread = HandlerThread("Camera2")
        handlerThread.start()
        childHandler = Handler(handlerThread.looper)
        mainHandler = Handler(getMainLooper())
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT //后摄像头

        //获取摄像头管理
        mCameraManager =
            MyApplication.context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        mImageReader =
            ImageReader.newInstance(surfaveView.height, surfaveView.width, ImageFormat.JPEG, 1)
//        mImageReader.setOnImageAvailableListener({ reader ->
//            //可以在这里处理拍照得到的临时照片 例如，写入本地
//            //mCameraDevice.close();
//            // 拿到拍照照片数据
//            val image: Image = reader.acquireNextImage()
//            val buffer: ByteBuffer = image.planes[0].buffer
//            val bytes = ByteArray(buffer.remaining())
//            buffer.get(bytes) //由缓冲区存入字节数组
//            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
////            if (bitmap != null) {
////                iv_show.setImageBitmap(bitmap)
////            }
//        }, mainHandler)
        //打开摄像头
        mCameraManager!!.openCamera(mCameraID!!, stateCallback, mainHandler)
    }

    /**
     * 开始预览
     */
    private fun takePreview() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            val previewRequestBuilder =
                mCameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder?.addTarget(mSurfaceHolder.getSurface())
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice?.createCaptureSession(
                listOf(
                    mSurfaceHolder.getSurface(),
                    mImageReader.surface
                ), object : CameraCaptureSession.StateCallback( // ③
                ) {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (null == mCameraDevice) return
                        // 当摄像头已经准备好时，开始显示预览
                        mCameraCaptureSession = cameraCaptureSession
                        try {
                            // 自动对焦
                            previewRequestBuilder?.set(
                                CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                            )
                            // 打开闪光灯
                            previewRequestBuilder?.set(
                                CaptureRequest.CONTROL_AE_MODE,
                                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                            )
                            // 显示预览
                            val previewRequest = previewRequestBuilder?.build()
                            if (previewRequest != null) {
                                mCameraCaptureSession.setRepeatingRequest(
                                    previewRequest,
                                    null,
                                    childHandler
                                )
                            }
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        "配置失败".showToast(MyApplication.context)
                    }
                }, childHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * 拍照
     */
    fun takePicture(param: BitmapBack) {
        if (mCameraDevice == null) return
        // 创建拍照需要的CaptureRequest.Builder
        val captureRequestBuilder: CaptureRequest.Builder
        try {
            captureRequestBuilder =
                mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(this.mImageReader.surface)
            // 自动对焦
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
            )
            // 自动曝光
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
            )
            // 获取手机方向
            val rotation: Int = content.windowManager.getDefaultDisplay().rotation
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90)
            //拍照
            val mCaptureRequest = captureRequestBuilder.build()
            mCameraCaptureSession.capture(mCaptureRequest, null, childHandler)
            mImageReader.setOnImageAvailableListener({ reader ->
                //可以在这里处理拍照得到的临时照片 例如，写入本地
                //mCameraDevice.close();
                // 拿到拍照照片数据
                val image: Image = reader.acquireNextImage()
                val buffer: ByteBuffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes) //由缓冲区存入字节数组
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                if (bitmap != null) {
                    param.bitmapBack(bitmap)
                }
            }, mainHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}