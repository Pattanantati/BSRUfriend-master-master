package appewtc.masterung.bsrufriend;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit การประกาศตัวแปร
    private Button signInButton, signUpButton;
    private EditText userEditText, passEditText;
    private String userString, passString;
    private String[] loginString = new String[8];
    private static final String urlPHP = "http://swiftcodingthai.com/bsru/get_user_pattanan.php";
    private boolean aBoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget คือ การ Initial Var กับ View บน XML
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText);
        passEditText = (EditText) findViewById(R.id.editText2);

        //Button Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check spec and get value from edit text
                userString = userEditText.getText().toString().trim();
                passString = passEditText.getText().toString().trim();
                if (userString.equals("") || passString.equals("")) {
                    //have spec
                    MyAlert myAlert = new MyAlert(MainActivity.this);
                    myAlert.myDialog("มีช่องว่าง","กรุณากรอกทุกช่อง");
                } else {
                    //no space
                    checkUserPass();
                }
            } // onClick
        });
    }   // Main Method

    private void checkUserPass() {
        try {
            GetUser getUser = new GetUser(MainActivity.this);
            getUser.execute(urlPHP);
            String strJSON = getUser.get();
            Log.d("16febV1", "strJSON ==> +" + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i=0 ; i<jsonArray.length();i+=1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {

                    loginString[0] = jsonObject.getString("id");
                    loginString[1] = jsonObject.getString("Name");
                    loginString[2] = jsonObject.getString("User");
                    loginString[3] = jsonObject.getString("Password");
                    loginString[4] = jsonObject.getString("Image");
                    loginString[5] = jsonObject.getString("Avata");
                    loginString[6] = jsonObject.getString("Lat");
                    loginString[7] = jsonObject.getString("Lng");

                    aBoolean = false;

                } //if

            } //for

            if (aBoolean) {
                //User False
                MyAlert myAlert = new MyAlert(MainActivity.this);
                myAlert.myDialog("หา user ไม่เจอ","ไม่มี" + userString + "ในฐานข้อมูลของเรา");
            } else if (!passString.equals(loginString[3])) {
                //Password false
                MyAlert myAlert = new MyAlert(MainActivity.this);
                myAlert.myDialog("Password false","Please try again password false");
            } else {
                //password true
                Toast.makeText(MainActivity.this,"Welcome" + loginString[1],
                        Toast.LENGTH_SHORT).show();
                //inter to sevice
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                intent.putExtra("login", loginString);
                startActivity(intent);
                finish();
            }


        } catch (Exception e) {
            Log.d("16febV1" ,"e checkUserPass ==>" +e.toString());
        }
    }// checkUserPass

}   // Main Class นี่คือ คลาสหลัก
