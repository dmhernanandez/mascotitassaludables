package hn.healthypets.proyecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ModalDialogoRaza extends AppCompatDialogFragment {
    EditText editTextRaza;
    ModalDialogoRazaListener listenerRaza;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_raza, null);

        builder.setView(view)
                .setTitle("Agregar la raza de tu mascota")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String razaMascota = editTextRaza.getText().toString();
                        listenerRaza.applyTextRaza(razaMascota);
                    }
                });
        editTextRaza = view.findViewById(R.id.editRazaMascota);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenerRaza = (ModalDialogoRazaListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debe implementar ModalDialogoRazaListener");
        }
    }

    public interface ModalDialogoRazaListener{
        void applyTextRaza(String razaMascota);
    }
}