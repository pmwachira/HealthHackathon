package mushirih.hackathon2015;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by p-tah on 21/11/2015.
 */
public class Register extends Activity{
    Button btnJoin;
    TextView txtName,txtNum;
    String name,number,attack;
   Context context;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        context=this;
        btnJoin=(Button)findViewById(R.id.btnJoin);
        txtName=(EditText)findViewById(R.id.name);
        txtNum=(EditText)findViewById(R.id.nextkin);

        radioGroup = (RadioGroup) findViewById(R.id.radioSex);



      try {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
              getActionBar().hide();
          }
      }catch (Exception e){

      }
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             txtNum.addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                 }

                 @Override
                 public void onTextChanged(CharSequence s, int start, int before, int count) {
                     btnJoin.setVisibility(View.VISIBLE);
                 }

                 @Override
                 public void afterTextChanged(Editable s) {

                 }
             });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getText().toString().trim().length() > 0 && txtNum.getText().toString().trim().length() > 0) {
                    // get selected radio button from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);

                   // Toast.makeText(getBaseContext(),
                     //       radioButton.getText(), Toast.LENGTH_SHORT).show();
                    name = txtName.getText().toString().trim();
                    number=txtNum.getText().toString().trim();
                    attack= (String) radioButton.getText();

                    SharedPreferences settings = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putString("NAME", name);
                    editor.putString("NUMBER",number);
                    editor.putString("ATTACK",attack);
                    editor.commit();

                    Intent intent = new Intent(getBaseContext(), Home.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Please fill out all details", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
