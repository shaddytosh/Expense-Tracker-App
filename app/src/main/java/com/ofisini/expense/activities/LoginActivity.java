package com.ofisini.expense.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ofisini.expense.R;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginbutton;
    Spinner userspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_login);

        password= findViewById(R.id.password);
        username= findViewById(R.id.username);
        loginbutton=findViewById(R.id.loginbutton);
        userspinner=findViewById(R.id.userspinner);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.user_type,R.layout.support_simple_spinner_dropdown_item);

        userspinner.setAdapter(adapter);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = userspinner.getSelectedItem().toString();
                if (username.getText().toString().equals("") && password.getText().toString().equals("") && item.equals("Admin")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    LoginActivity.this.startActivity(intent);

                } else if (username.getText().toString().equals("") && password.getText().toString().equals("") && item.equals("User")) {
                    Intent intent = new Intent(LoginActivity.this, ExpenseActivity.class);
                    LoginActivity.this.startActivity(intent);

                } else {

                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}