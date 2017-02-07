package timberwolf.blackmarket.com.blackmarket;


import android.support.v7.app.AppCompatActivity;

public class Person extends AppCompatActivity{

    public String nam;
    public String n1;

    public Person(){

    }

    public Person(String nam, int n1) {
        this.nam = nam;
        this.n1 = String.valueOf(n1);
    }

}
