package uac.imsp.clockingapp;


import static uac.imsp.clockingapp.RegisterEmployee.isNotValidPassword;
import static uac.imsp.clockingapp.RegisterEmployee.isNotValidUsername;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {



    private EditText Username,Password;
    private EmployeeManager datasource;
    public static int CurrentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        datasource= new EmployeeManager(this);
        datasource.open();
        Username=findViewById(R.id.login_username);
        Password=findViewById((R.id.login_password));
        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(this);
        datasource.close();

    }
    public boolean checkInput(){
        String toast_message="";
        if(Username.getText().toString().equals(""))
            toast_message="Username requis";
        else if(isNotValidUsername(Username.getText().toString()))
            toast_message="Username invalide !";
        else if (Password.getText().toString().equals(""))
            toast_message="Mot de passe requis !";
        else if (isNotValidPassword(Password.getText().toString()))
            toast_message="Mot de passe invalide";
        if(toast_message.equals(""))
            return  true;
        Toast.makeText(this,toast_message,Toast.LENGTH_LONG).show();
        return  false;
    }

    @Override
    public void onClick(View v) {
        CurrentNumber=0;
        String toast_message="Authentification réussie !";
        Intent intent = new Intent(this, Menu.class);
        CurrentNumber = datasource.connectUser(Username.getText().toString(),Password.getText().toString());
        if(checkInput() && CurrentNumber!=0) {
            Toast.makeText(this,toast_message,Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        else if(CurrentNumber==0)
            toast_message="Username ou mot de passe incorrect";
            Toast.makeText(this,toast_message,Toast.LENGTH_LONG).show();

    }

}
