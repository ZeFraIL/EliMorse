package elia.shapira.elimorse;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private EditText etNameL, etPasswordL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        etNameL=view.findViewById(R.id.etNameL);
        etPasswordL=view.findViewById(R.id.etPasswordL);
        return view;
    }

    public String getName() {
        return etNameL.getText().toString();
    }

    public String getPassword() {
        return etPasswordL.getText().toString();
    }
}