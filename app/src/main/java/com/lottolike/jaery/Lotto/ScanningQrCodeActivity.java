package com.lottolike.jaery.Lotto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScanningQrCodeActivity extends AppCompatActivity {

    SurfaceView binding;
    static int  REQUEST_CAMERA = 1729;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_qr_code);

        binding = findViewById(R.id.cameraPreview);

        binding.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
                    startCameraPreview(width,height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            }
        } else {
            // TODO: - Start live camera feed
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && requestCode == REQUEST_CAMERA) {

        }else
        {
            Toast.makeText(ScanningQrCodeActivity.this,"카메라 권한을 등록해주세요",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void startCameraPreview(final int width, final int height)
    {
        try {
            final Handler cameraBkgHandler = new Handler();

            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

            if(cameraManager != null) {
                String[] ids = cameraManager.getCameraIdList();

                String id = ids[0];
                for(int i=0 ;i<ids.length;i++)
                {
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(ids[i]);
                    Integer cameraDirection = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    if(cameraDirection != null&& cameraDirection == CameraCharacteristics.LENS_FACING_BACK)
                        id = ids[i];
                }


                final CameraDevice.StateCallback cameraStateCallback = new CameraDevice.StateCallback() {
                    @Override
                    public void onOpened(@NonNull final CameraDevice cameraDevice) {

                        final BarcodeDetector.Builder barcodebuilder = new BarcodeDetector.Builder(getApplicationContext());
                        barcodebuilder.setBarcodeFormats(Barcode.QR_CODE|Barcode.DATA_MATRIX);
                        final BarcodeDetector detector =barcodebuilder.build();

                        final ImageReader reader =ImageReader.newInstance(width,height, ImageFormat.JPEG,1);
                        reader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                            @Override
                            public void onImageAvailable(ImageReader imageReader) {
                                Image cameraImage =imageReader.acquireNextImage();

                                ByteBuffer buffer = cameraImage.getPlanes()[0].getBuffer();
                                byte[] bytes = new byte[buffer.capacity()];
                                buffer.get(bytes);


                              Bitmap bitmap=  BitmapFactory.decodeByteArray(bytes,0,bytes.length,null);
                               Frame frame = new  Frame.Builder().setBitmap(bitmap).build();
                                SparseArray<Barcode> barcodeResults =detector.detect(frame);

                                if(barcodeResults.size()>0)
                                {
                                    Log.d("scanQR",barcodeResults.valueAt(0).toString());
                                }else
                                {
                                    Log.d("scanQR","not found");
                                }

                                cameraImage.close();

                            }
                        },cameraBkgHandler);


                        CameraCaptureSession.StateCallback captureCallback =new CameraCaptureSession.StateCallback() {
                            @Override
                            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                                try {
                                  CaptureRequest.Builder builder =  cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                                  builder.addTarget(binding.getHolder().getSurface());
                                  builder.addTarget(reader.getSurface());
                                  cameraCaptureSession.setRepeatingRequest(builder.build(),null,null);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                                Log.d("scanQR","onConfigureFailed");
                            }
                        };
                        ArrayList<Surface> arrayList =new ArrayList<>();

                        arrayList.add(binding.getHolder().getSurface());
                        arrayList.add(reader.getSurface());
                        try {
                            cameraDevice.createCaptureSession(arrayList,captureCallback,cameraBkgHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                        Log.d("scanQR","onDisconnected");
                    }

                    @Override
                    public void onError(@NonNull CameraDevice cameraDevice, int i) {
                        Log.d("scanQR","onError");
                    }
                };


                cameraManager.openCamera(id,cameraStateCallback,cameraBkgHandler);
            }
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

}
