package qrapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
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

public class ZxingScanner extends AppCompatActivity {
    private IntentIntegrator integrator;
    TextView text;
    ImageView image;
    QRCodeWriter writer;


    public void onScan(View v){

        // integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator = new IntentIntegrator(this);
        integrator.setPrompt("Jensen");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Scan videre spade", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(this, "Du scannede : " + result.getContents(), Toast.LENGTH_LONG).show();
                text.setText(result.getContents());

                writer = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(result.getContents(), BarcodeFormat.QR_CODE, 150, 150);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    image.setImageBitmap(bmp);

                    MediaStore.Images.Media.insertImage(this.getContentResolver(), image.getDrawingCache(true), "testerimage", null);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        }

        else {

            super.onActivityResult(requestCode, resultCode, data);

        }

    }
}
