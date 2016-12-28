package qrapp.qrapp.data;

/**
 * Created by Magnus on 26-12-2016.
 */

public class CardData {


    private String medlemskab;
    private String kortData;



    public CardData (String medlemskab, String data){

        this.medlemskab = medlemskab;
        this.kortData = data;


    }




    public String getMedlemskab() {
        return medlemskab;
    }

    public void setMedlemskab(String medlemskab) {
        this.medlemskab = medlemskab;
    }

    public String getKortData() {
        return kortData;
    }

    public void setKortData(String kortData) {
        this.kortData = kortData;
    }
}
