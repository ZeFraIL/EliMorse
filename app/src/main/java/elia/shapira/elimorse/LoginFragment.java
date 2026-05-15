package elia.shapira.elimorse;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Fragment containing the UI for user login.
 * Provides methods to retrieve the entered name and password.
 */
public class LoginFragment extends Fragment {

    /** EditText for entering the username. */
    private EditText etNameL;
    /** EditText for entering the password. */
    private EditText etPasswordL;

    /**
     * Inflates the login fragment layout and initializes UI components.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        etNameL=view.findViewById(R.id.etNameL);
        etPasswordL=view.findViewById(R.id.etPasswordL);
        return view;
    }

    /**
     * Gets the name entered in the login form.
     * @return The username string.
     */
    public String getName() {
        return etNameL.getText().toString();
    }

    /**
     * Gets the password entered in the login form.
     * @return The password string.
     */
    public String getPassword() {
        return etPasswordL.getText().toString();
    }
}
