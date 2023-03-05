package com.example.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class responsible for creating and presenting the dialogs the application
 */
public class AppDialog extends DialogFragment {
    public static final String DIALOG_ID = "Id";
    public static final String DIALOG_TITLE = "Title";
    public static final String DIALOG_CONTENT = "Content";
    public static final String DIALOG_POS = "Positive rid";
    public static final String DIALOG_NEG = "Negative rid";

    // Dialog interface callback in order to notify about the user selected result
    public interface DialogEvent {
        void onPositiveClick(int dialogId, Bundle args);
        void onNegativeClick(int dialogId, Bundle args);
    }

    private DialogEvent mDialogEvent;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Activities containing this fragment, must implement his callbacks
        if (!(context instanceof DialogEvent)) {
            throw new ClassCastException(context + " must implement AppDialog.DialogEvent");
        }

        mDialogEvent = (DialogEvent) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mDialogEvent = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_view, null, false);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setView(view);

        TextView title = view.findViewById(R.id.dialog_title);
        TextView content = view.findViewById(R.id.dialog_content);
        TextView pos = view.findViewById(R.id.dialog_pos);
        TextView neg = view.findViewById(R.id.dialog_neg);

        final Bundle arguments = getArguments();
        int id;
        String contentString, posString, negString, titleString;

        // Get the dialog parameters
        if (arguments != null) {

            id = arguments.getInt(DIALOG_ID);
            contentString = arguments.getString(DIALOG_CONTENT);
            titleString = arguments.getString(DIALOG_TITLE);

            // Check the the parameters are valid
            if ((id == 0) || (contentString == null) || (titleString == null)) {
                throw new IllegalArgumentException("Must pass id, content and title!");
            }

            posString = arguments.getString(DIALOG_POS);
            negString = arguments.getString(DIALOG_NEG);
        } else {
            throw new IllegalArgumentException("Must pass dialog details");
        }

        // Display dialog parameters
        title.setText(titleString);
        content.setText(contentString);

        if ((posString == null) || (posString.length() == 0)) {
            //Hide positive btn
            pos.setVisibility(View.GONE);
        } else {
            //Display positive btn
            pos.setVisibility(View.VISIBLE);
            pos.setText(posString);
        }

        if ((negString == null) || (negString.length() == 0)) {
            //Hide negative btn
            neg.setVisibility(View.GONE);
        } else {
            //Display negative btn
            neg.setVisibility(View.VISIBLE);
            neg.setText(negString);
        }

        pos.setOnClickListener(view1 -> {
            mDialogEvent.onPositiveClick(id, arguments);
            dialog.dismiss();
        });
        neg.setOnClickListener(view1 -> {
            mDialogEvent.onNegativeClick(id, arguments);
            dialog.dismiss();
        });

        return dialog;
    }
}
