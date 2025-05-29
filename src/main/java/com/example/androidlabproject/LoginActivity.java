package com.example.androidlabproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import java.util.ArrayList;
import java.util.List;

import com.example.myproject.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxRememberMe;
    private SharedPrefManager sharedPrefManager;

    private List<SharedPrefManager.User> userList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        editTextEmail.setOnClickListener(v -> editTextEmail.showDropDown());

        sharedPrefManager = SharedPrefManager.getInstance(this);

        userList = sharedPrefManager.getUserList();
        List<String> emailList = new ArrayList<>();
        for (SharedPrefManager.User user : userList) {
            if (user.remembered) {
                emailList.add(user.email);
            }
        }
        //for dropdown (list)
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, emailList);
        editTextEmail.setAdapter(adapter);

        editTextEmail.setOnItemClickListener((parent, view, position, id) -> {
            editTextPassword.setText(""); // clear password if user selects from dropdown
        });


        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                return;
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            //Always check if user already exists
            boolean exists = false;
            for (SharedPrefManager.User user : userList) {
                if (user.email.equals(email)) {
                    exists = true;

                    // If user previously existed without "Remember Me" but now checked it,
                    // update their remembered flag to true and save the updated list
                    if (checkBoxRememberMe.isChecked() && !user.remembered) {
                        user.remembered = true;
                        sharedPrefManager.saveUserList(userList);
                    }
                    break;
                }
            }
            // If user doesn't exist at all, save them with the remembered flag accordingly
            if (!exists) {
                userList.add(new SharedPrefManager.User(email, password, checkBoxRememberMe.isChecked()));
                sharedPrefManager.saveUserList(userList);
            }



            adapter.clear();
            for (SharedPrefManager.User user : userList) {
                if (user.remembered) {
                    adapter.add(user.email);
                }
            }
            adapter.notifyDataSetChanged();



            boolean loginSuccess = false;
            for (SharedPrefManager.User user : userList) {
                if (user.email.equals(email) && user.password.equals(password)) {
                    loginSuccess = true;
                    break;
                }
            }

            if (loginSuccess) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                // Navigate to next screen here
            } else {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
            // Example:
            // startActivity(new Intent(this, HomeActivity.class));
        });
    }
}