package qrapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by Magnus on 23-12-2016.
 */

public class ZxingScanner {
    private IntentIntegrator integrator;
    TextView text;
    ImageView image;
    QRCodeWriter writer;

        public ZxingScanner(Activity activity) {

        integrator = new IntentIntegrator(activity);


    }

    public void onScan(){

        integrator.setPrompt("Scan din kode.");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();



    }



}
