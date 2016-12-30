package qrapp.qrapp.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import qrapp.activities.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    //private final String[] itemname;
    //private final Integer[] imgid;
    ArrayList<String> cardNames1;
    ArrayList<Integer> cardImages1;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid) {
        super(context, R.layout.row, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.cardNames1 = itemname;
        this.cardImages1 = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(cardNames1.get(position));
        imageView.setImageResource(cardImages1.get(position));
        extratxt.setText("Description ");
        return rowView;

    }

    ;
}