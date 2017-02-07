package timberwolf.blackmarket.com.blackmarket;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.PorterDuff;
        import android.net.Uri;
        import android.nfc.Tag;
        import android.os.CountDownTimer;
        import android.preference.PreferenceManager;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.text.Layout;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.ImageButton;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.ToggleButton;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.twitter.sdk.android.core.models.Card;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.List;

        import static android.R.attr.start;
        import static android.R.attr.value;
        import static timberwolf.blackmarket.com.blackmarket.NameActivity.username;

public class MainActivity extends AppCompatActivity{

    public String TAG = "MainActivity";
    public int scoreNum;
    public int tapperCost = 20;
    public int adder1 = 1;
    public int tapper;
    public int multiplier = 1;
    public int multiplierCost = 1000;
    public int adder2 = 100;
    public CountDownTimer countDownTimer;
    public CountDownTimer animTimer;
    public CountDownTimer dataTimer;
    public long currentTimePause;
    public long currentTimeResume;
    public int x=0;
    public boolean storeOut = false;
    public boolean isBuy = true;
    public int tapSellVal;
    public int upSellVal;
    public ArrayList<Integer> listTap = new ArrayList<Integer>();
    public ArrayList<Integer> listUp = new ArrayList<Integer>();
    private Adapter adapter;

    public String usr = username;
    public DatabaseReference database;

    public String p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Timberwolf Black Market (BETA)");


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        final RelativeLayout sl = (RelativeLayout) findViewById(R.id.sl);
        final ImageButton button = (ImageButton) findViewById(R.id.bananaButton);
        final TextView score = (TextView) findViewById(R.id.score);
        final Button store_button = (Button) findViewById(R.id.store_button);
        final Animation animIn = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);
        final Animation animOut = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_left);
        final Animation banIn = AnimationUtils.loadAnimation(this, R.anim.ban_slide_in);
        final Animation banOut = AnimationUtils.loadAnimation(this, R.anim.ban_slide_out);
        final ToggleButton buytoggle = (ToggleButton) findViewById(R.id.buytoggle);
        final Button ig = (Button) findViewById(R.id.ig);

        final Button buy1 = (Button) findViewById(R.id.buy1);
        final TextView buy1text = (TextView) findViewById(R.id.buy1text);
        final TextView cost1 = (TextView) findViewById(R.id.cost1);
        final Button buy2 = (Button) findViewById(R.id.buy2);
        final TextView buy2text = (TextView) findViewById(R.id.buy2text);
        final TextView cost2 = (TextView) findViewById(R.id.cost2);
        final TextView sellText1 = (TextView) findViewById(R.id.sellText1);
        final TextView sellText2 = (TextView) findViewById(R.id.sellText2);
        final TextView selldesc = (TextView) findViewById(R.id.selldesc);

        final TextView name = (TextView) findViewById(R.id.name);
        name.setText(usr);

        //final ListView listl = (ListView) findViewById(R.id.listl);

        String[] values = new String[]{
            "1. " + p1,
                "2. " + p2,
                "3. " + p3,
                "4. " + p4,
                "5. " + p5,
                "6. " + p6,
                "7. " + p7,
                "8. " + p8,
                "9. " + p9,
                "10. " + p10
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        //listl.setAdapter(adapter);


        //set views on store to be invisible
        button.bringToFront();
        buytoggle.setVisibility(View.GONE);
        sl.setVisibility(View.GONE);
        sellText1.setVisibility(View.GONE);
        sellText2.setVisibility(View.GONE);
        selldesc.setVisibility(View.GONE);

        //initializes lists for selling
        listTap.add(0);
        listUp.add(0);
        sellText1.setText("+"+listTap.get(listTap.size()-1));
        sellText2.setText("+"+listUp.get(listUp.size()-1));

        database = FirebaseDatabase.getInstance().getReference();
        //sends user score to database
        dataTimer = new CountDownTimer(Long.MAX_VALUE,15000) {
            @Override
            public void onTick(long millisUntilFinished) {
                database.child("Scores").push().setValue(usr + " " + String.valueOf(scoreNum));
                //myRef.push(usr + " " + String.valueOf(scoreNum) + ", " + String.valueOf(tapper) + ", " + String.valueOf(multiplier));
            }

            @Override
            public void onFinish() {

            }
        };


        //timer updates score every second
        countDownTimer = new CountDownTimer(Long.MAX_VALUE,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                scoreNum += tapper;
                score.setText(String.valueOf(scoreNum));
            }
            @Override
            public void onFinish() {

            }
        };

        //timer updates scale of imageView once it is finished animating
        animTimer = new CountDownTimer(300,300){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                    button.setScaleX((float) .01);
                    button.setScaleY((float) .01);
                    storeOut = true;

            }
        };

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/mvhsblackmarket/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        buytoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBuy){
                    //toggle is now set to sell items
                    buytoggle.setBackgroundResource(R.color.redt);
                    buy1text.setVisibility(View.GONE);
                    cost1.setVisibility(View.GONE);
                    buy2text.setVisibility(View.GONE);
                    cost2.setVisibility(View.GONE);

                    selldesc.setVisibility(View.VISIBLE);
                    sellText1.setVisibility(View.VISIBLE);
                    sellText2.setVisibility(View.VISIBLE);

                    buy1.setText("Sell Tapper");
                    buy2.setText("Sell Upgrade");
                    buy1.setBackgroundDrawable(getResources().getDrawable(R.drawable.sellbutton));
                    buy2.setBackgroundDrawable(getResources().getDrawable(R.drawable.sellbutton));
                    isBuy=false;

                }else{
                    //toggle is now set to buy items
                    buytoggle.setBackgroundResource(R.color.greent);
                    sellText1.setVisibility(View.GONE);
                    sellText2.setVisibility(View.GONE);
                    selldesc.setVisibility(View.GONE);

                    buy1text.setVisibility(View.VISIBLE);
                    cost1.setVisibility(View.VISIBLE);
                    buy2text.setVisibility(View.VISIBLE);
                    cost2.setVisibility(View.VISIBLE);

                    buy1.setText("Buy Tapper");
                    buy2.setText("Upgrade Tap");
                    buy1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
                    buy2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
                    isBuy=true;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(storeOut==false) {
                    scoreNum += 1 * multiplier;
                    score.setText(String.valueOf(scoreNum));
                }
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        //overlay is black with transparency of 0x77 (119)
                        button.getDrawable().setColorFilter(0x37000000, PorterDuff.Mode.SRC_ATOP);
                        button.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        //clear the overlay
                        button.getDrawable().clearColorFilter();
                        button.invalidate();
                        break;
                    }
                }

                return false;
            }
        });

        store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeOut==false) {
                    button.startAnimation(banIn);
                    sl.startAnimation(animIn);
                    sl.setVisibility(View.VISIBLE);
                    buytoggle.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    animTimer.start();
                    store_button.setText("Back");
                }else{
                    button.setScaleX(1);
                    button.setScaleY(1);
                    button.setVisibility(View.VISIBLE);
                    button.startAnimation(banOut);
                    sl.startAnimation(animOut);
                    sl.setVisibility(View.GONE);
                    buytoggle.setVisibility(View.VISIBLE);
                    store_button.setText("Store");
                    storeOut = false;
                }
            }
        });

        buy1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isBuy) {
                    if (scoreNum >= tapperCost) {
                        tapper += 1;
                        scoreNum -= tapperCost;
                        tapSellVal += (tapperCost * .8);
                        listTap.add((int) (tapperCost * .8));
                        sellText1.setText("+"+listTap.get(listTap.size()-1));
                        updateTapperCost();

                        buy1.setText("Buy Tapper");
                        buy1text.setText("+" + tapper + " Banana/sec");
                        cost1.setText("Cost: " + String.valueOf(tapperCost));
                        score.setText(String.valueOf(scoreNum));
                    }
                }else{
                    if(listTap.size()>=1) {
                        tapper-=1;
                        buy1text.setText("+" + tapper + " Banana/sec");
                        int prev = listTap.get(listTap.size() - 1);
                        scoreNum += prev;
                        tapSellVal-=prev;
                        listTap.remove(listTap.size()-1);
                        sellText1.setText("+"+prev);
                        score.setText(String.valueOf(scoreNum));
                        if(listTap.size()==1){
                            sellText1.setText("+0");
                        }
                    }
                }
            }
        });
        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBuy) {
                    if (scoreNum >= multiplierCost) {
                        multiplier += 1;
                        scoreNum -= multiplierCost;
                        upSellVal += (multiplierCost * .8);
                        listUp.add((int) (multiplierCost * .8));
                        sellText2.setText("+"+listUp.get(listUp.size()-1));
                        updateMultiplierCost();

                        buy2text.setText(String.valueOf(multiplier) + " Banana/tap");
                        cost2.setText("Cost: " + String.valueOf(multiplierCost));
                        score.setText(String.valueOf(scoreNum));
                    }
                }else{
                    if(listUp.size()>=2) {
                        multiplier-=1;
                        buy2text.setText(String.valueOf(multiplier) + " Banana/tap");
                        int prev2 = listUp.get(listUp.size() - 1);
                        scoreNum += prev2;
                        listUp.remove(listUp.size()-1);
                        upSellVal-=prev2;
                        sellText2.setText("+"+upSellVal);
                        score.setText(String.valueOf(scoreNum));
                        if(listUp.size()==1){
                            sellText2.setText("+0");
                        }
                    }
                }
            }
        });
/*
        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNum=0;
                multiplier=1;
                multiplierCost=1000;
                tapper=0;
                tapperCost=20;
                adder1=1;
                adder2=100;
                buy1.setText("Buy Tapper");
                buy1text.setText("+" + tapper + " Banana/sec");
                cost1.setText("Cost: " + String.valueOf(tapperCost));
                buy2text.setText(String.valueOf(multiplier) + " Banana/tap");
                cost2.setText("Cost: " + String.valueOf(multiplierCost));
                score.setText(String.valueOf(scoreNum));
            }
        });
*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                username="";
                signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void initialize(){
        TextView name = (TextView) findViewById(R.id.name);
        TextView score = (TextView) findViewById(R.id.score);
        Button buy1 = (Button) findViewById(R.id.buy1);
        TextView buy1text = (TextView) findViewById(R.id.buy1text);
        TextView cost1 = (TextView) findViewById(R.id.cost1);
        TextView buy2text = (TextView) findViewById(R.id.buy2text);
        TextView cost2 = (TextView) findViewById(R.id.cost2);

        name.setText(usr);

        score.setText(String.valueOf(scoreNum));
        buy1.setText("Buy Tapper");
        buy1text.setText("+" + tapper + " Banana/sec");
        cost1.setText("Cost: " + String.valueOf(tapperCost));

        buy2text.setText(String.valueOf(multiplier) + " Banana/tap");
        cost2.setText("Cost: " + String.valueOf(multiplierCost));

    }

    public void updateTapperCost(){
        tapperCost+=adder1;
        x+=1;
        if(x==2){
            adder1+=1;
            x=0;
        }else{
            adder1+=0;
        }
    }

    public void updateMultiplierCost(){
        multiplierCost+=adder2;
        adder2+=100;
    }

    @Override
    protected void onResume(){
        super.onResume();

        dataTimer.start();
        countDownTimer.start();
        database.child("Scores").push().setValue(usr + " " + String.valueOf(scoreNum));

        SharedPreferences prefs = getSharedPreferences("myPrefsKey", MODE_PRIVATE);

        usr = prefs.getString("usr", usr);

        scoreNum = prefs.getInt("scoreNum", scoreNum);
        scoreNum+=(currentTimePause-currentTimeResume)*tapper;
        tapperCost = prefs.getInt("tapperCost", tapperCost);
        adder1 = prefs.getInt("adder1", adder1);
        tapper = prefs.getInt("tapper", tapper);

        multiplier = prefs.getInt("multiplier", multiplier);
        multiplierCost = prefs.getInt("multiplierCost", multiplierCost);
        adder2 = prefs.getInt("adder2", adder2);

        initialize();
    }

    @Override
    protected void onPause(){
        super.onPause();

        dataTimer.cancel();
        countDownTimer.cancel();
        database.child("Scores").push().setValue(usr + " " + String.valueOf(scoreNum));

        SharedPreferences prefs = getSharedPreferences("myPrefsKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("usr", usr);

        editor.putInt("scoreNum", scoreNum);
        editor.putInt("tapper", tapper);
        editor.putInt("tapperCost", tapperCost);
        editor.putInt("adder1", adder1);

        editor.putInt("multiplier", multiplier);
        editor.putInt("multiplierCost", multiplierCost);
        editor.putInt("adder2", adder2);

        editor.commit();
    }

}


