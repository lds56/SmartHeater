package whatever.smartheater.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import whatever.smartheater.R;

/**
 * Created by lds on 3/17/15.
 */
public class BluetoothDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.bluetooth_list_dailog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] itemList = new String[3];
        itemList[0] = "item1"; itemList[1] = "item2"; itemList[2] = "item3";

        //builder.setView(dialoglayout);
        builder.setTitle(R.string.dialog_title)
            /*.setPositiveButton(R.string.connect, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            }) */
            .setItems(itemList, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // The 'which' argument contains the index position
                    // of the selected item
                }
            });
        return builder.create();
    }
}