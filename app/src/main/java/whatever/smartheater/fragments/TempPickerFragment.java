package whatever.smartheater.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import whatever.smartheater.R;

/**
 * Created by lds on 3/19/15.
 */
public class TempPickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View npView = inflater.inflate(R.layout.temp_picker_layout, null);
        NumberPicker np = (NumberPicker) npView.findViewById(R.id.number_picker);
        np.setMaxValue(100);
        np.setMinValue(0);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Please Choose a suitable temperature :)")
                .setView(npView)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DialogFragment newFragment = new TimePickerFragment();
                                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                            }
                        })
                .setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }
}
