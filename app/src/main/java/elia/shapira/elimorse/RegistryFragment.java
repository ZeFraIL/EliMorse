package elia.shapira.elimorse;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Fragment containing the UI for user registration.
 * Provides methods to retrieve registration details like name, password, email, and phone.
 */
public class RegistryFragment extends Fragment {

    /** EditText for entering the name. */
    private EditText etName;
    /** EditText for entering the password. */
    private EditText etPassword;
    /** EditText for entering the email. */
    private EditText etMail;
    /** EditText for entering the phone number. */
    private EditText etPhone;

    /**
     * Inflates the registration fragment layout and initializes UI components.
     *
     * @param inflater           The LayoutInflater object.
     * @param container          The parent view container.
     * @param savedInstanceState Previous saved state.
     * @return The View for the fragment's UI.
     */
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

    /**
     * Gets the name from the registration form.
     * @return The name string.
     */
    public String getName() {
        return etName.getText().toString();
    }

    /**
     * Gets the password from the registration form.
     * @return The password string.
     */
    public String getPassword() {
        return etPassword.getText().toString();
    }

    /**
     * Gets the email address from the registration form.
     * @return The email string.
     */
    public String getMail() {
        return etMail.getText().toString();
    }

    /**
     * Gets the phone number from the registration form.
     * @return The phone number string.
     */
    public String getPhone() {
        return etPhone.getText().toString();
    }
}
