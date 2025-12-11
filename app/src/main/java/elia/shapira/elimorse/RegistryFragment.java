package elia.shapira.elimorse;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RegistryFragment extends Fragment {

    private EditText etName, etPassword, etMail, etPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registry, container, false);
        etName=view.findViewById(R.id.etName);
        etPassword=view.findViewById(R.id.etPassword);
        etMail=view.findViewById(R.id.etEmail);
        etPhone=view.findViewById(R.id.etPhone);
        return view;
    }

    public String getName() {
        return etName.getText().toString();
    }

    public String getPassword() {
        return etPassword.getText().toString();
    }

    public String getMail() {
        return etMail.getText().toString();
    }

    public String getPhone() {
        return etPhone.getText().toString();
    }
}