package com.sapronov.todolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class DeleteTasksFragment extends DialogFragment {

    private ConfirmDeleteTasksListener callback;

    public interface ConfirmDeleteTasksListener {
        void onPositiveDialogClick(DialogFragment dialog);

        void onNegativeDialogClick(DialogFragment dialog);
    }

    public DeleteTasksFragment(Fragment fragment) {
        this.callback = (ConfirmDeleteTasksListener) fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Remove all?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onPositiveDialogClick(DeleteTasksFragment.this);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onNegativeDialogClick(DeleteTasksFragment.this);
                    }
                })
                .create();
        return dialog;
    }
}
