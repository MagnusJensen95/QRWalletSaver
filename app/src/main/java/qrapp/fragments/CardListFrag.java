package qrapp.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qrapp.activities.R;
import qrapp.qrapp.data.CardData;
import qrapp.qrapp.data.CustomListAdapter;
import qrapp.qrapp.data.DBHelper;


public class CardListFrag extends Fragment {

    private CustomListAdapter adapter;
    private DBHelper helper;
    private ListView list;
    List<CardData> cardList;
    String[] cardNames;
    Integer[] cardIconAddress;
    ArrayList<String> cardNames1;
    ArrayList<Integer> cardImages1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = (ListView) getActivity().findViewById(R.id.list);
        helper = new DBHelper(this.getActivity().getApplicationContext());
        cardList = helper.getData();
        cardNames = new String[]{"jensen", "nusnus"};
        cardNames1 = new ArrayList<String>();
        cardImages1 = new ArrayList<Integer>();

        for (int i = 0; i < cardList.size(); i++) {

            cardNames1.add(cardList.get(i).getMedlemskab());
            cardImages1.add(R.drawable.ic_menu_camera);

        }


        adapter = new CustomListAdapter(this.getActivity(),
                cardNames1, cardImages1);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyCardsFrag frag = new MyCardsFrag();

                String passName = cardList.get(position).getMedlemskab();
                String passCode = cardList.get(position).getKortData();

                Bundle bundle = new Bundle();

                bundle.putString("passedCardName", passName);
                bundle.putString("passedCode", passCode);
                frag.setArguments(bundle);

                try {

                    getFragmentManager().beginTransaction().replace(R.id.container_main, frag).addToBackStack("CardList").commit();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }


            }


        });



/*
    private class CustomListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> itemname;
        private final ArrayList<Integer> imgid;

        public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid) {
            super(context, R.layout.row, itemname);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.itemname=itemname;
            this.imgid=imgid;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.row, null,true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

            txtTitle.setText(itemname.get(position));
            imageView.setImageResource(imgid.get(position));
            extratxt.setText("Description "+itemname.get(position));
            return rowView;

        };
    }
*/
    }
}