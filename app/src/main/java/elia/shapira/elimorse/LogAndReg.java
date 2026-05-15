package elia.shapira.elimorse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Activity handling both User Login and Registration.
 * It uses fragments to switch between login and registration forms.
 * Database operations are performed on a background thread using an ExecutorService.
 */
public class LogAndReg extends AppCompatActivity {

    /** Fragment for the login form. */
    private LoginFragment loginFragment;
    /** Fragment for the registration form. */
    private RegistryFragment registryFragment;
    /** RadioButtons to switch between Login and Register modes. */
    private RadioButton rbLogin, rbRegister;
    /** Confirm button to submit the form (Login or Register). */
    private Button bConfirm;
    /** Activity context. */
    private Context context;
    /** Repository for user-related database operations. */
    private UserRepository userRepository;
    /** Executor for running database queries on a background thread. */
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    /** Handler to post results back to the main UI thread. */
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    /**
     * Initializes the activity, sets up fragments and click listeners.
     * @param savedInstanceState Saved state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_and_reg);

        initElements();

        rbLogin.setOnClickListener(view -> showFragment(loginFragment));
        rbRegister.setOnClickListener(view -> showFragment(registryFragment));

        bConfirm.setOnClickListener(view -> {
            if (rbRegister.isChecked()) {
                handleRegistration();
            } else if (rbLogin.isChecked()) {
                handleLogin();
            }
        });
    }

    /**
     * Replaces the content of the FragmentContainer with the specified fragment.
     * @param fragment The fragment to show.
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FL, fragment);
        ft.commit();
    }

    /**
     * Handles the user registration process.
     * Validates input, checks for existing users, and saves the new user to the database.
     */
    private void handleRegistration() {
        String name = registryFragment.getName();
        String password = registryFragment.getPassword();
        String mail = registryFragment.getMail();
        String phone = registryFragment.getPhone();

        if (!validateFields(name, password, mail, phone)) return;

        executorService.execute(() -> {
            User existingUser = userRepository.findUser(name, password);
            if (existingUser == null) {
                User newUser = new User(name, password, phone, mail);
                long id = userRepository.registerUser(newUser);
                newUser.setId((int) id);
                mainThreadHandler.post(() -> proceedToDashboard(newUser));
            } else {
                mainThreadHandler.post(() -> Toast.makeText(context, R.string.user_exists_error, Toast.LENGTH_LONG).show());
            }
        });
    }

    /**
     * Handles the user login process.
     * Validates input and checks credentials against the database.
     */
    private void handleLogin() {
        String name = loginFragment.getName();
        String password = loginFragment.getPassword();

        if (!validateFields(name, password)) return;

        executorService.execute(() -> {
            User user = userRepository.findUser(name, password);
            mainThreadHandler.post(() -> {
                if (user != null) {
                    proceedToDashboard(user);
                } else {
                    My_Toast.showToast(context, getString(R.string.user_not_found));
                }
            });
        });
    }

    /**
     * Validates that the provided fields are not empty.
     * @param fields Variable number of strings to validate.
     * @return true if all fields are valid, false otherwise.
     */
    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                Toast.makeText(context, R.string.name_field_empty, Toast.LENGTH_SHORT).show(); 
                return false;
            }
        }
        return true;
    }

    /**
     * Navigates to the Dashboard activity after successful authentication.
     * @param user The authenticated user object.
     */
    private void proceedToDashboard(User user) {
        Intent intent = new Intent(context, DashBoard.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    /**
     * Initializes UI components, repositories, and fragments.
     */
    private void initElements() {
        context = this;
        rbLogin = findViewById(R.id.rbLogin);
        rbRegister = findViewById(R.id.rbRegister);
        bConfirm = findViewById(R.id.bConfirm);
        userRepository = new UserRepository(this);
        
        loginFragment = new LoginFragment();
        registryFragment = new RegistryFragment();
        
        showFragment(loginFragment);
    }
}
