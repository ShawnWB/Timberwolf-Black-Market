package timberwolf.blackmarket.com.blackmarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NameActivity extends AppCompatActivity{

    public static String username;
    public String n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        setTitle("Timberwolf Black Market (BETA)");

        final EditText enterName = (EditText) findViewById(R.id.enterName);
        final Button continueb = (Button) findViewById(R.id.continueb);

        SharedPreferences prefs = getSharedPreferences("myPrefsKey", MODE_PRIVATE);
        n = prefs.getString("usr", username);
        if(n!=null){
            startActivity(new Intent(NameActivity.this, MainActivity.class));
        }


        continueb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterName.getText().toString().isEmpty()){
                    Toast.makeText(NameActivity.this, "just enter a name, dood",
                            Toast.LENGTH_LONG).show();
                }else{
                    setUsername(NameActivity.this,enterName.getText().toString());
                    startActivity(new Intent(NameActivity.this, MainActivity.class));
                }
            }
        });
    }

    public static void setUsername(Context context, String usern) {
        username = usern;
        SharedPreferences prefs = context.getSharedPreferences("myAppPackage", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", usern);
        editor.commit();
    }
}
