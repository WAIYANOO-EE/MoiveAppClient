package com.ardam.clientappsub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ardam.clientappsub.models.RequestModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    EditText etName, imageLink;
    Button send, cancel;


    public RequestFragment() {
        // Required empty public constructor
    }

    FirebaseFirestore db;
    CollectionReference ref;
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_request, container, false);
        GoogleAdMob adMob = new GoogleAdMob();
        adMob.loadBanner(myView, getContext(), getActivity());
        etName = myView.findViewById(R.id.etName);
        imageLink = myView.findViewById(R.id.imageLink);
        send = myView.findViewById(R.id.send);
        cancel = myView.findViewById(R.id.cancel);
        db = FirebaseFirestore.getInstance();
        ref = db.collection(getString(R.string.requests));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().trim().equals(""))
                {
                    Toasty.error(getContext(),"Please Fill Data!",Toasty.LENGTH_LONG).show();
                }
                else
                    {
                        RequestModel model = new RequestModel();
                        model.setTitle(etName.getText().toString().trim());
                        model.setImageLink(imageLink.getText().toString().trim());
                        ref.add(model);
                        etName.setText("");
                        imageLink.setText("");

                        Toasty.success(getContext(), "Send OK", Toasty.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setText("");
                imageLink.setText("");
            }
        });
        return myView;
    }
}
