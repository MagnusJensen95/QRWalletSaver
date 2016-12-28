package qrapp.fragments;

import android.app.Fragment;
import android.view.View;

/**
 * Created by Magnus on 27-12-2016.
 */

public abstract class MasterFrag extends Fragment implements View.OnClickListener {

    public abstract void addEntry(String data);
}
