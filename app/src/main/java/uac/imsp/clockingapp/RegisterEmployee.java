package uac.imsp.clockingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class RegisterEmployee extends AppCompatActivity
        implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener ,
        AdapterView.OnItemSelectedListener{
    private TextView Lastname, Firstname, Email, Username, Password, PasswordConfirm;
    private EditText Number, Birthdate;
    private String Birth;
    DatePickerDialog picker;
    private ImageView PreviewImage;
    private byte[] Picture;
    private byte[] QRCode;
    private char gend;
    private ServiceManager serviceManager;
    private String[] services;
    private EmployeeManager datasource;
    private String SelectedService;
    private Service service;
    private  final Spinner spinnerServices = findViewById(R.id.register_service);
    private Planning planning;
    private int Start=6,End=18;
    private  TextView selectedStartTime,selectedEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);
        //Planning = new Planning();
        serviceManager = new ServiceManager(this);
        serviceManager.open();
        services = serviceManager.getAllServices();
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);

        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);

        spinnerServices.setOnItemSelectedListener(this);


        datasource = new EmployeeManager(this);
        datasource.open();
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);*/
        selectedStartTime=findViewById(R.id.register_planning_start);
        selectedEndTime=findViewById(R.id.register_planning_end);
        Number = findViewById(R.id.register_number);
        Lastname = findViewById(R.id.register_lastname);
        Firstname = findViewById(R.id.register_firstname);
        RadioGroup gender = findViewById(R.id.register_gender);
        Birthdate = findViewById(R.id.register_birthdate);
        Email = findViewById(R.id.register_email);
        Username = findViewById(R.id.register_username);
        Password = findViewById(R.id.register_password);
        PasswordConfirm = findViewById(R.id.register_password_confirm);
        PreviewImage = findViewById(R.id.register_preview_image);
        Button register = findViewById(R.id.register_button);
        Button reset = findViewById(R.id.register_reset_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        gender.setOnCheckedChangeListener(this);
        Birthdate.setOnClickListener(this);
        register.setOnClickListener(this);
        reset.setOnClickListener(this);
        selectPicture.setOnClickListener(this);
        selectedStartTime.setOnClickListener(this);
        selectedStartTime.setOnClickListener(this);

        datasource.close();


    }


    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                            Picture = getBytesFromBitmap(selectedImageBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        PreviewImage.setImageBitmap(
                                selectedImageBitmap);

                    }
                }
            });

    @SuppressLint("DefaultLocale")
        @Override
    public void onClick(View v) {
        if(v.getId()==R.id.register_planning_start)
        {
            if(Start<11)
                Start++;
            else if (Start==11)
                Start=8;
        }
        else if(v.getId()==R.id.register_planning_end)
        {
            if(End<18)
                Start++;
            else if (End==18)
                End=13;
        }
        else if (v.getId() == R.id.register_picture_button)
            imageChooser();
        else if (v.getId() == R.id.register_button && checkInput()) {

            Employee employee = new Employee(
                    Integer.parseInt(Number.getText().toString()),
                    Lastname.getText().toString(),
                    Firstname.getText().toString(),
                    gend,
                    Birth,
                    Email.getText().toString(),
                    Picture,
                    generateQRCode(Lastname.getText().toString()),
                    Username.getText().toString(),
                    Password.getText().toString()

            );
            if(datasource.exists((employee)))
                Toast.makeText(RegisterEmployee.this, "Le matricule existe déjà", Toast.LENGTH_LONG).show();
            else {
                datasource.create(employee);
                datasource.update(employee, service);

                Toast.makeText(RegisterEmployee.this, "Fine", Toast.LENGTH_LONG).show();
            }

        }
        else if (v.getId() == R.id.register_reset_button)

            resetInput();
        else if (v.getId() == R.id.register_birthdate) {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            //picker = new DatePickerDialog(this);
            //picker.setOnDateSetListener(this);
            // date picker dialog
            picker = new DatePickerDialog(RegisterEmployee.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {

                        Birthdate.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year1));
                        Birth = "" + year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    },
                    year, month, day);
            picker.show();
        }

    }

    private boolean isNotValidNumber(@NonNull String number) {
        String pattern = "[0-9]+";
        return !number.matches(pattern);
    }

    //needed for login
    public static boolean isNotValidUsername(@NonNull String username) {
        String pattern = "[A-Z][A-Za-z0-9]{5,29}";
        //String pattern="A";
        return !username.matches(pattern);
    }

    public boolean isNotValidName(@NonNull String name) {
        String pattern = "Ezechiel";

        return !name.matches(pattern);
    }

    public boolean isNotValidEmail(@NonNull String mail) {
        String pattern = "^[A-Za-z]+\\.?\\w+@[A-Za-z0-9_-]+\\.\\w+$";
        // String pattern="A";
        return !mail.matches(pattern);

    }

    //Static for we'll nedd it for login
    public static boolean isNotValidPassword(@NonNull String password) {
        String pattern = "\\w{5,}";
        //String pattern="A";
        return !password.matches(pattern);
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public boolean checkInput() {
        String toast_message;
        toast_message = "";

        if (Number.getText().toString().equals(""))
            toast_message = "Le matricule est requis !";
        else if (isNotValidNumber(Number.getText().toString())) {
            toast_message = "Le matricule est invalide !";
            //verifier si existence
        } else if (Lastname.getText().toString().equals(""))
            toast_message = "Le nom est requis !";

        else if (isNotValidName(Lastname.getText().toString()))
            toast_message = "Le nom est invalide !";
        else if (Firstname.getText().toString().equals(""))
            toast_message = "Le prénom est requis !";
        else if (isNotValidName(Firstname.getText().toString()))
            toast_message = "Le prénom est invalide !";
        else if (Email.getText().toString().equals(""))
            toast_message = "L'email est requis !";
        else if (isNotValidEmail(Email.getText().toString()))
            toast_message = "L'email est invalide !";
        else if (Username.getText().toString().equals(""))
            toast_message = "Le username est requis !";

        else if (isNotValidUsername(Username.getText().toString()))
            toast_message = "Le username est invalide !";
        else if (Password.getText().toString().equals(""))
            toast_message = "Le mot de passe est requis !";
        else if (isNotValidPassword(Password.getText().toString()))
            toast_message = "Le mot de passe est invalide !";
        else if (!Password.getText().toString().equals(PasswordConfirm.getText().toString()))
            toast_message = "Vérifiez le mot de passe et reessayez !";
        if (toast_message.equals(""))
            return true;
        Toast.makeText(RegisterEmployee.this, toast_message, Toast.LENGTH_LONG).show();
        return false;
    }

    public void resetInput() {
        Number.setText("");
        Lastname.setText("");
        Firstname.setText("");
        Email.setText("");
        Username.setText("");
        Password.setText("");
        PasswordConfirm.setText("");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        gend = (checkedId == R.id.register_boy) ? 'M' : 'F';

    }

    public byte[] generateQRCode(String myText) {
        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code

            return getBytesFromBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
            return  null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SelectedService = String.valueOf(spinnerServices.getSelectedItem());

        // Toast.makeText(RegisterEmployee.this, myRegion, Toast.LENGTH_SHORT).show();
        service = new Service(serviceManager.searchService(SelectedService));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}