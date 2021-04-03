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

public class ModalDialogoEspecie extends AppCompatDialogFragment {
    EditText editTextEspecie;
    ModalDialogoEspecieListener listenerEspecie;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_especie, null);

        builder.setView(view)
                .setTitle("Agregar la Especie de tu Mascota")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String especieMascota = editTextEspecie.getText().toString();
                        listenerEspecie.applyTextEspecie(especieMascota);
                    }
                });
        editTextEspecie = view.findViewById(R.id.editEspecieMascota);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenerEspecie = (ModalDialogoEspecieListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debe implementar ModalDialogoEspecieListener");
        }
    }

    public interface ModalDialogoEspecieListener {
        void applyTextEspecie(String especieMascota);
    }
}