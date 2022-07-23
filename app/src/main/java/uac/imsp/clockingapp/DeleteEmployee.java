package uac.imsp.clockingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteEmployee extends AppCompatActivity  implements View.OnClickListener {
private Button Delete;
private     EmployeeManager datasource;
private Employee employee;
   private  EditText Lastname,Firstname,Gender;
   private ImageView Picture;
   private int Number=1;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        String [] infos= null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        Picture.findViewById(R.id.delete_picture);
        Lastname.findViewById(R.id.delete_lastname);
        Firstname.findViewById(R.id.delete_firstname);
        Gender.findViewById(R.id.delete_gender);
        Delete=findViewById(R.id.delete);
        Delete.setOnClickListener(this);
        employee=new Employee(Number);
        datasource=new EmployeeManager(this);
datasource.open();
  infos=datasource.getInformations(employee);
  Lastname.setText(infos[0]);
  Firstname.setText(infos[1]);

datasource.close();
    }

    @Override
    public void onClick(View v) {


    }
}