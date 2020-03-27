package com.uts.syehfi.ngopskuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.uts.syehfi.ngopskuy.models.Session;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        session = Application.getSession();
    }

    public void handleLogin(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        boolean success = session.validate(username, password);
        if (success) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(view, "Authentication Failed", Snackbar.LENGTH_SHORT).show();
        }
    }
}
