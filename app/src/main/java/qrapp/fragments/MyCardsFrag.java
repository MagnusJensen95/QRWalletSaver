package qrapp.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.util.ArrayList;
import java.util.List;

import qrapp.qrapp.data.CardData;
import qrapp.qrapp.data.CustomListAdapter;
import qrapp.qrapp.data.DBHelper;

import qrapp.activities.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCardsFrag extends MasterFrag {


    private DBHelper helper;
    private ArrayList<CardData> cards;
    private List<String> cardNames;
    private  Spinner cardSpinner;
    private  ArrayAdapter<String> adapter;
    private MultiFormatWriter writer;
    private ImageView qrImage;
    private  ImageView barcodeImage;
    private boolean firstSelection = false;
    private TextView numberText;
    private Bundle bundle;
    private String name;
    private String code;
    private ListView selectionView;
    private CustomListAdapter customAdapter;
    private ArrayList<String> currentName;
    private ArrayList<Integer> currentImage;
    private Spinner editOptionsSpinner;


    public MyCardsFrag () {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cards, container, false);

        helper = new DBHelper(getActivity().getApplicationContext());
        cardNames = new ArrayList<String>();


        qrImage = (ImageView) view.findViewById(R.id.QRImage);
        barcodeImage = (ImageView) view.findViewById(R.id.barcodeImage);
        numberText = (TextView)view.findViewById(R.id.numberText);
        selectionView = (ListView)view.findViewById(R.id.currrentSelectionList);


        bundle = this.getArguments();
        if (bundle != null){

            name = bundle.getString("passedCardName");
            code = bundle.getString("passedCode");

        }
        else {
            name = "Ingen Data";
            code = "Ingen Data";

        }

        currentName = new ArrayList<String>();
        currentName.add(name);
        currentImage = new ArrayList<Integer>();
        currentImage.add(R.drawable.ic_menu_camera);

        editOptionsSpinner = (Spinner)view.findViewById(R.id.editSpinner);
        editOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String itemToDelete = currentName.get(0).toString();
                if(editOptionsSpinner.getItemAtPosition(position).toString().equals("Slet Kort")){

                    AlertDialog dialog =  new AlertDialog.Builder(MyCardsFrag.this.getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Slet Kort")
                            .setMessage("Er du sikker på at du vil fjerne " + itemToDelete + " fra dit medlemskartotek?")
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    helper.removeItem(itemToDelete);
                                    Toast.makeText(getActivity().getApplicationContext(), itemToDelete + " er nu fjernet fra dit medlemskartotek", Toast.LENGTH_SHORT).show();

                                    try {
                                        getFragmentManager().beginTransaction().replace(R.id.container_main, new CardListFrag()).commit();
                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                }

                            })
                            .setNegativeButton("Nej", null)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        adapter = new CustomListAdapter(this.getActivity(),
                currentName, currentImage);

        selectionView.setAdapter(adapter);

        setImages();


        /*
        //This is the previous version
        cards = helper.getData();

        if(!firstSelection) {
            cardNames.add("Pick a card");
        }

        for (int i = 0; i < cards.size(); i++) {

            cardNames.add(cards.get(i).getKortData());

        }


        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.spinner, cardNames);
        cardSpinner = (Spinner) view.findViewById(R.id.cardsSpinner);
        cardSpinner.setAdapter(adapter);

        */



        return view;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void addEntry(String data) {

    }


    public void setImages() {


         writer = new MultiFormatWriter();
         try {
             BitMatrix bitMatrixQR = writer.encode(code, BarcodeFormat.QR_CODE, 500, 500);
             int width = bitMatrixQR.getWidth();
             int height = bitMatrixQR.getHeight();
             Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
             for (int x = 0; x < width; x++) {
                 for (int y = 0; y < height; y++) {
                     bmp.setPixel(x, y, bitMatrixQR.get(x, y) ? Color.BLACK : Color.WHITE);
                 }
             }
             qrImage.setImageBitmap(bmp);

         } catch (WriterException e) {
             e.printStackTrace();
         }

         try {

             BitMatrix bitMatrixBar = writer.encode(code, BarcodeFormat.CODE_128, 1000, 250);
             Bitmap bar = Bitmap.createBitmap(1000, 250, Bitmap.Config.ARGB_8888);
             int width = bitMatrixBar.getWidth();
             int height = bitMatrixBar.getHeight();

             for (int x = 0; x < 1000; x++) {
                 for (int y = 0; y < 250; y++) {
                     bar.setPixel(x, y, bitMatrixBar.get(x, y) ? Color.BLACK : Color.WHITE);
                 }
             }


             barcodeImage.setImageBitmap(bar);
             numberText.setText(code);



         } catch (Exception e) {
         }



     }

    }
