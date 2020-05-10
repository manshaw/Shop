package ncra.org.pk.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    private static final String PACAKGE_NAME = "ncra.org.pk.shop";
    EditText p1, p2, p3, p5, p6, p7, p8;
    EditText o1, o2, o3, o5, o6, o7, o8;
    EditText sc1, sc2, sc3, sc5, sc6, sc7, sc8;
    TextView b1, b2, b3, b5, b6, b7, b8;
    TextView p17_total, o17_total, sc17_total;
    Button sendOrder;
    SharedPreferences prefs = null;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String phoneNumber, date;
    TextView tobacco, lu;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(PACAKGE_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("PhoneNumber", null);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + phoneNumber + "/" + new Date() + "/");
        initUi();
        initFunc();
        Date d = Calendar.getInstance().getTime();
        date = new SimpleDateFormat("dd-MMM-yyyy").format(d);
        lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLuActivity();
            }
        });
        tobacco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Already Here!", Toast.LENGTH_SHORT).show();
            }
        });
        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderSummaryDialog();
//                contactLessDialog(view);
            }
        });
    }

    public void startLuActivity() {
        Intent nextActivity;
        nextActivity = new Intent(this, LuActivity.class);
        startActivity(nextActivity);
        finish();
    }

    public void sendOrderClicked() {
        sendData(b1, sc1, o1, p1);
        sendData(b2, sc2, o2, p2);
        sendData(b3, sc3, o3, p3);
        sendData(b5, sc5, o5, p5);
        sendData(b6, sc6, o6, p6);
        sendData(b7, sc7, o7, p7);
        sendData(b8, sc8, o8, p8);
        sendTotal(p17_total, o17_total, sc17_total);
        setZero(p1, o1, sc1);
        setZero(p2, o2, sc2);
        setZero(p3, o3, sc3);
        setZero(p5, o5, sc5);
        setZero(p6, o6, sc6);
        setZero(p7, o7, sc7);
        setZero(p8, o8, sc8);
    }

    private void orderSummaryDialog() {
        TableLayout table = new TableLayout(this);
        table.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        table.setBackgroundColor(getColor(R.color.colorPrimary));
        TextView heading = new TextView(this);
        heading.setText("Order Summary");
        heading.setTextSize(20);
        table.addView(heading, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        addHeader(table);
        isNonZero(table, b1, p1, o1, sc1);
        isNonZero(table, b2, p2, o2, sc2);
        isNonZero(table, b3, p3, o3, sc3);
        isNonZero(table, b5, p5, o5, sc5);
        isNonZero(table, b6, p6, o6, sc6);
        isNonZero(table, b7, p7, o7, sc7);
        isNonZero(table, b8, p8, o8, sc8);
        addTotal(table);

        LinearLayout item = new LinearLayout(this);
        item.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 2));
        item.setGravity(Gravity.CENTER);
        item.setOrientation(LinearLayout.HORIZONTAL);
        Button cancel = new Button(this);
        cancel.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        cancel.setText("cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        item.addView(cancel);
        Button ok = new Button(this);
        ok.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        ok.setText("ok");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                contactLessDialog(view);
            }
        });
        item.addView(ok);
        table.addView(item, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(table);
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void isNonZero(TableLayout table, TextView brand, EditText pack, EditText outer, EditText box) {
        if (!pack.getText().toString().isEmpty() || !box.getText().toString().isEmpty() || !outer.getText().toString().isEmpty()) {
            addRow(table, brand.getText().toString(), pack.getText().toString(), outer.getText().toString(), box.getText().toString());
        }
    }

    public void addRow(TableLayout table, String brand, String pack, String outer, String box) {
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setBackground(getDrawable(R.drawable.cell));
        LinearLayout item = new LinearLayout(this);
        item.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 10));
        item.setGravity(Gravity.CENTER);
        item.setOrientation(LinearLayout.HORIZONTAL);
        TextView tb_brand = new TextView(this);
        tb_brand.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5.5f));
        tb_brand.setText(brand);
        tb_brand.setGravity(Gravity.CENTER);
        item.addView(tb_brand);

        TextView tb_pack = new TextView(this);
        tb_pack.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_pack.setText(pack);
        tb_pack.setGravity(Gravity.CENTER);
        item.addView(tb_pack);

        TextView tb_outer = new TextView(this);
        tb_outer.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_outer.setText(outer);
        tb_outer.setGravity(Gravity.CENTER);
        item.addView(tb_outer);

        TextView tb_box = new TextView(this);
        tb_box.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_box.setText(box);
        tb_box.setGravity(Gravity.CENTER);
        item.addView(tb_box);

        tr.addView(item);
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void addHeader(TableLayout table) {
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setBackgroundColor(getColor(R.color.colorAccent));
        LinearLayout item = new LinearLayout(this);
        item.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 10));
        item.setGravity(Gravity.CENTER);
        item.setOrientation(LinearLayout.HORIZONTAL);
        TextView tb_brand = new TextView(this);
        tb_brand.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5.5f));
        tb_brand.setText("Brand Name");
        tb_brand.setTextColor(getColor(R.color.colorPrimary));
        tb_brand.setGravity(Gravity.CENTER);
        item.addView(tb_brand);

        TextView tb_pack = new TextView(this);
        tb_pack.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_pack.setText("Pack");
        tb_pack.setTextColor(getColor(R.color.colorPrimary));
        tb_pack.setGravity(Gravity.CENTER);
        item.addView(tb_pack);

        TextView tb_outer = new TextView(this);
        tb_outer.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_outer.setText("Outer");
        tb_outer.setTextColor(getColor(R.color.colorPrimary));
        tb_outer.setGravity(Gravity.CENTER);
        item.addView(tb_outer);

        TextView tb_box = new TextView(this);
        tb_box.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_box.setText("Box");
        tb_box.setTextColor(getColor(R.color.colorPrimary));
        tb_box.setGravity(Gravity.CENTER);
        item.addView(tb_box);

        tr.addView(item);
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }


    public void addTotal(TableLayout table) {
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setBackgroundColor(getColor(R.color.colorAccent));
        LinearLayout item = new LinearLayout(this);
        item.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 10));
        item.setGravity(Gravity.CENTER);
        item.setOrientation(LinearLayout.HORIZONTAL);
        TextView tb_brand = new TextView(this);
        tb_brand.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5.5f));
        tb_brand.setText("Total");
        tb_brand.setTextColor(getColor(R.color.colorPrimary));
        tb_brand.setGravity(Gravity.CENTER);
        item.addView(tb_brand);

        TextView tb_pack = new TextView(this);
        tb_pack.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_pack.setText(p17_total.getText().toString());
        tb_pack.setTextColor(getColor(R.color.colorPrimary));
        tb_pack.setGravity(Gravity.CENTER);
        item.addView(tb_pack);

        TextView tb_outer = new TextView(this);
        tb_outer.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_outer.setText(o17_total.getText().toString());
        tb_outer.setTextColor(getColor(R.color.colorPrimary));
        tb_outer.setGravity(Gravity.CENTER);
        item.addView(tb_outer);

        TextView tb_box = new TextView(this);
        tb_box.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_box.setText(sc17_total.getText().toString());
        tb_box.setTextColor(getColor(R.color.colorPrimary));
        tb_box.setGravity(Gravity.CENTER);
        item.addView(tb_box);

        tr.addView(item);
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void contactLessDialog(View view) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(view.getContext())
                .setIcon(R.drawable.ic_logo)
                .setView(R.layout.dialog_contact_less)
                .setTitle("Contact Less Delivery?")
                .setMessage("Do you want contact less delivery?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendOrderClicked();
                        setContactLess(TRUE);
                        setDate(date);
                        Toast.makeText(MainActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendOrderClicked();
                        setContactLess(FALSE);
                        setDate(date);
                        Toast.makeText(MainActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void setContactLess(Boolean contactLess) {
        myRef.child("tobacco").child("Contact Less Delivery").setValue(contactLess.booleanValue());
    }

    public void setDate(String date) {
        myRef.child("tobacco").child("Date").setValue(date);
    }

    public void setZero(EditText editText, EditText outer, EditText pack) {
        outer.setText("");
        pack.setText("");
        editText.setText("");
    }

    public void sendData(TextView brandName, EditText box, EditText outer, EditText pack) {
        myRef.child("tobacco").child(brandName.getText().toString()).child("Pack").setValue(isValid(pack));
        myRef.child("tobacco").child(brandName.getText().toString()).child("Outer").setValue(isValid(outer));
        myRef.child("tobacco").child(brandName.getText().toString()).child("SC(Box)").setValue(isValid(box));
    }

    public void sendTotal(TextView pack, TextView outer, TextView box) {
        myRef.child("tobacco").child("Total").child("Pack").setValue(isValid_total(pack));
        myRef.child("tobacco").child("Total").child("Outer").setValue(isValid_total(outer));
        myRef.child("tobacco").child("Total").child("SC(Box)").setValue(isValid_total(box));
    }

    public void initUi() {
        tobacco = findViewById(R.id.tobacco);
        lu = findViewById(R.id.lu);

        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p5 = findViewById(R.id.p5);
        p6 = findViewById(R.id.p6);
        p7 = findViewById(R.id.p7);
        p8 = findViewById(R.id.p8);

        o1 = findViewById(R.id.o1);
        o2 = findViewById(R.id.o2);
        o3 = findViewById(R.id.o3);
        o5 = findViewById(R.id.o5);
        o6 = findViewById(R.id.o6);
        o7 = findViewById(R.id.o7);
        o8 = findViewById(R.id.o8);

        sc1 = findViewById(R.id.sc1);
        sc2 = findViewById(R.id.sc2);
        sc3 = findViewById(R.id.sc3);
        sc5 = findViewById(R.id.sc5);
        sc6 = findViewById(R.id.sc6);
        sc7 = findViewById(R.id.sc7);
        sc8 = findViewById(R.id.sc8);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);

        sc17_total = findViewById(R.id.sc17_total);
        p17_total = findViewById(R.id.p17_total);
        o17_total = findViewById(R.id.o17_total);

        sendOrder = findViewById(R.id.btnSend);
    }

    public void initFunc() {
        setListener(p1);
        setListener(p2);
        setListener(p3);
        setListener(p5);
        setListener(p6);
        setListener(p7);
        setListener(p8);

        setListener(o1);
        setListener(o2);
        setListener(o3);
        setListener(o5);
        setListener(o6);
        setListener(o7);
        setListener(o8);

        setListener(sc1);
        setListener(sc2);
        setListener(sc3);
        setListener(sc5);
        setListener(sc6);
        setListener(sc7);
        setListener(sc8);
    }

    public void setTotal() {
        p17_total.setText(String.valueOf(isValid(p1)
                + isValid(p2)
                + isValid(p3)
                + isValid(p5)
                + isValid(p6)
                + isValid(p7)
                + isValid(p8)));


        sc17_total.setText(String.valueOf(isValid(sc1)
                + isValid(sc2)
                + isValid(sc3)
                + isValid(sc5)
                + isValid(sc6)
                + isValid(sc7)
                + isValid(sc8)));

        o17_total.setText(String.valueOf(isValid(o1)
                + isValid(o2)
                + isValid(o3)
                + isValid(o5)
                + isValid(o6)
                + isValid(o7)
                + isValid(o8)));
    }

    public Integer isValid(EditText editText) {
        try {
            return Integer.valueOf(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Integer isValid_total(TextView textView) {
        try {
            return Integer.valueOf(textView.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    setTotal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public void calVal(CharSequence charSequence, TextView outer, TextView pack) {
//        if (charSequence.toString().length() == 0) {
//            outer.setText("0");
//            pack.setText("0");
//        } else if (Integer.parseInt(charSequence.toString()) < 10) {
//            outer.setText("0");
//            pack.setText(charSequence.toString());
//        } else {
//            try {
//                int count = 0, val = Integer.parseInt(charSequence.toString());
//                while (val - 10 >= 10) {
//                    val = val - 10;
//                    count++;
//                }
//                pack.setText(String.valueOf(Integer.valueOf(charSequence.toString()) % 10));
//                outer.setText(String.valueOf(count + 1));
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
