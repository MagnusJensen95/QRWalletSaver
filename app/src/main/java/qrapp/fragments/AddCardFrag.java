package qrapp.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

import qrapp.activities.DrawerMain;
import qrapp.activities.ZxingScanner;
import qrapp.qrapp.data.CardData;
import qrapp.qrapp.data.DBHelper;

import qrapp.activities.R;


public class AddCardFrag extends MasterFrag implements AdapterView.OnItemSelectedListener {

    private DBHelper helper;
    private EditText medlemskab;
    private Spinner spinner;
    private TextView introtext;
    private TextView enterText;
    private EditText enterEdit;
    private Button addButton;
    private CardData cardData;
    private boolean qr, bar, type;
    private String entryData;
    private ZxingScanner scanner;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);

        helper = new DBHelper(getActivity().getApplicationContext());


        introtext = (TextView) view.findViewById(R.id.addCardMedlemskabText);
        medlemskab = (EditText) view.findViewById(R.id.medlemskabAddEdit);
        enterText = (TextView)view.findViewById(R.id.indtastText);
        enterText.setVisibility(View.INVISIBLE);
        enterEdit = (EditText) view.findViewById(R.id.indtastEdit);
        enterEdit.setVisibility(View.INVISIBLE);
        spinner = (Spinner) view.findViewById(R.id.optionsSpinner);
        spinner.setOnItemSelectedListener(this);
        scanner = new ZxingScanner(getActivity());


        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addButton:

                if (qr){
                    scanner.onScan();
                    try {
                        getFragmentManager().beginTransaction().replace(R.id.container_main, new MainPageFrag()).commit();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                else if(bar){
                    scanner.onScan();
                    try {
                        getFragmentManager().beginTransaction().replace(R.id.container_main, new MainPageFrag()).commit();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }

                }
                else if (type){

                    addEntry(enterEdit.getText().toString());
                    try {
                        getFragmentManager().beginTransaction().replace(R.id.container_main, new MainPageFrag()).commit();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }

                }

                break;
        }
    }

  @Override
  public void addEntry(String data){
      entryData = data;
      if(helper.addCardData(new CardData(medlemskab.getText().toString(), entryData))){
          Toast.makeText(getActivity().getApplicationContext(),
                  "Du Har nu gemt " + medlemskab.getText().toString() + " i dine kort",
                  Toast.LENGTH_SHORT).show();
      }
      else {
          Toast.makeText(getActivity().getApplicationContext(),
                  "Du har allerede gemt " + medlemskab.getText().toString() + " i dine kort",
                  Toast.LENGTH_SHORT).show();
      }


  }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        switch (item){
            case "Scan QR-Kode":
               qr = true;
                bar = false;
                type = false;
                enterText.setVisibility(View.INVISIBLE);
                enterEdit.setVisibility(View.INVISIBLE);

                break;
            case "Scan Stregkode":
                qr = false;
                bar = true;
                type = false;
                enterText.setVisibility(View.INVISIBLE);
                enterEdit.setVisibility(View.INVISIBLE);

                break;
            case "Indtast oplysninger manuelt":
                qr = false;
                bar = false;
                type = true;


                enterEdit.setVisibility(View.VISIBLE);
                enterText.setVisibility(View.VISIBLE);


                break;
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
