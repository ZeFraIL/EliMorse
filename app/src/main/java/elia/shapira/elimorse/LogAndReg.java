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

public class LogAndReg extends AppCompatActivity {

    private LoginFragment loginFragment;
    private RegistryFragment registryFragment;
    private RadioButton rbLogin, rbRegister;
    private Button bConfirm;
    private Context context;
    private UserRepository userRepository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

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

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FL, fragment);
        ft.commit();
    }

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

    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                Toast.makeText(context, R.string.name_field_empty, Toast.LENGTH_SHORT).show(); // Simplified validation msg
                return false;
            }
        }
        return true;
    }

    private void proceedToDashboard(User user) {
        Intent intent = new Intent(context, DashBoard.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

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
