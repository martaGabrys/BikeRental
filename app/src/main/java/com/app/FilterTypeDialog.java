package com.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class FilterTypeDialog extends DialogFragment {

    private String title;
    private String[] itemArray;
    private String filterItem;
    private FilterTypeDialogListener listener;

    public FilterTypeDialog(String title, String[] itemArray) {
        this.title = title;
        this.itemArray = itemArray;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title)
                .setItems(itemArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        filterItem = itemArray[i];
                        listener.onDialogItemClick(FilterTypeDialog.this, title, filterItem);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FilterTypeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "" +
                    "must implement FilterTypeDialogInterface");
        }
    }

    public interface FilterTypeDialogListener {
        void onDialogItemClick(DialogFragment dialog, String title, String item);
    }
}
