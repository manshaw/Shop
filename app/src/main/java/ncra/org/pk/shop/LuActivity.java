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

public class LuActivity extends AppCompatActivity {

    private static final String PACAKGE_NAME = "ncra.org.pk.shop";
    EditText p1, p2, p3, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24;
    EditText o1, o2, o3, o5, o6, o7, o8, o9, o10, o11, o12, o13, o14, o15, o16, o17, o18, o19, o20, o21, o22, o23, o24;
    EditText sc1, sc2, sc3, sc5, sc6, sc7, sc8, sc9, sc10, sc11, sc12, sc13, sc14, sc15, sc16, sc17, sc18, sc19, sc20, sc21, sc22, sc23, sc24;
    TextView b1, b2, b3, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24;
    TextView p25_total, o25_total, sc25_total;
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
        setContentView(R.layout.activity_lu);
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
                Toast.makeText(LuActivity.this, "Already Here!", Toast.LENGTH_SHORT).show();
            }
        });
        tobacco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
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

    public void startMainActivity() {
        Intent nextActivity;
        nextActivity = new Intent(this, MainActivity.class);
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
        sendData(b9, sc9, o9, p9);
        sendData(b10, sc10, o10, p10);
        sendData(b11, sc11, o11, p11);
        sendData(b12, sc12, o12, p12);
        sendData(b13, sc13, o13, p13);
        sendData(b14, sc14, o14, p14);
        sendData(b15, sc15, o15, p15);
        sendData(b16, sc16, o16, p16);
        sendData(b17, sc17, o17, p17);
        sendData(b18, sc18, o18, p18);
        sendData(b19, sc19, o19, p19);
        sendData(b20, sc20, o20, p20);
        sendData(b21, sc21, o21, p21);
        sendData(b22, sc22, o22, p22);
        sendData(b23, sc23, o23, p23);
        sendData(b24, sc24, o24, p24);
        sendTotal(p25_total, o25_total, sc25_total);
        setZero(p1, o1, sc1);
        setZero(p2, o2, sc2);
        setZero(p3, o3, sc3);
        setZero(p5, o5, sc5);
        setZero(p6, o6, sc6);
        setZero(p7, o7, sc7);
        setZero(p8, o8, sc8);
        setZero(p9, o9, sc9);
        setZero(p10, o10, sc10);
        setZero(p11, o11, sc11);
        setZero(p12, o12, sc12);
        setZero(p13, o13, sc13);
        setZero(p14, o14, sc14);
        setZero(p15, o15, sc15);
        setZero(p16, o16, sc16);
        setZero(p17, o17, sc17);
        setZero(p18, o18, sc18);
        setZero(p19, o19, sc19);
        setZero(p20, o20, sc20);
        setZero(p21, o21, sc21);
        setZero(p22, o22, sc22);
        setZero(p23, o23, sc23);
        setZero(p24, o24, sc24);
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
                        Toast.makeText(LuActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendOrderClicked();
                        setContactLess(FALSE);
                        setDate(date);
                        Toast.makeText(LuActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void setContactLess(Boolean contactLess) {
        myRef.child("lu").child("Contact Less Delivery").setValue(contactLess.booleanValue());
    }

    public void setDate(String date) {
        myRef.child("lu").child("Date").setValue(date);
    }

    public void setZero(EditText editText, EditText outer, EditText pack) {
        outer.setText("");
        pack.setText("");
        editText.setText("");
    }

    public void sendData(TextView brandName, EditText box, EditText outer, EditText pack) {
        myRef.child("lu").child(brandName.getText().toString()).child("BOX").setValue(isValid(pack));
        myRef.child("lu").child(brandName.getText().toString()).child("CRTN").setValue(isValid(outer));
        myRef.child("lu").child(brandName.getText().toString()).child("SC(Box)").setValue(isValid(box));
    }

    public void sendTotal(TextView pack, TextView outer, TextView box) {
        myRef.child("lu").child("Total").child("BOX").setValue(isValid_total(pack));
        myRef.child("lu").child("Total").child("CRTN").setValue(isValid_total(outer));
        myRef.child("lu").child("Total").child("SC(Box)").setValue(isValid_total(box));
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
        p9 = findViewById(R.id.p9);
        p10 = findViewById(R.id.p10);
        p11 = findViewById(R.id.p11);
        p12 = findViewById(R.id.p12);
        p13 = findViewById(R.id.p13);
        p14 = findViewById(R.id.p14);
        p15 = findViewById(R.id.p15);
        p16 = findViewById(R.id.p16);
        p17 = findViewById(R.id.p17);
        p18 = findViewById(R.id.p18);
        p19 = findViewById(R.id.p19);
        p20 = findViewById(R.id.p20);
        p21 = findViewById(R.id.p21);
        p22 = findViewById(R.id.p22);
        p23 = findViewById(R.id.p23);
        p24 = findViewById(R.id.p24);

        o1 = findViewById(R.id.o1);
        o2 = findViewById(R.id.o2);
        o3 = findViewById(R.id.o3);
        o5 = findViewById(R.id.o5);
        o6 = findViewById(R.id.o6);
        o7 = findViewById(R.id.o7);
        o8 = findViewById(R.id.o8);
        o9 = findViewById(R.id.o9);
        o10 = findViewById(R.id.o10);
        o11 = findViewById(R.id.o11);
        o12 = findViewById(R.id.o12);
        o13 = findViewById(R.id.o13);
        o14 = findViewById(R.id.o14);
        o15 = findViewById(R.id.o15);
        o16 = findViewById(R.id.o16);
        o17 = findViewById(R.id.o17);
        o18 = findViewById(R.id.o18);
        o19 = findViewById(R.id.o19);
        o20 = findViewById(R.id.o20);
        o21 = findViewById(R.id.o21);
        o22 = findViewById(R.id.o22);
        o23 = findViewById(R.id.o23);
        o24 = findViewById(R.id.o24);

        sc1 = findViewById(R.id.sc1);
        sc2 = findViewById(R.id.sc2);
        sc3 = findViewById(R.id.sc3);
        sc5 = findViewById(R.id.sc5);
        sc6 = findViewById(R.id.sc6);
        sc7 = findViewById(R.id.sc7);
        sc8 = findViewById(R.id.sc8);
        sc9 = findViewById(R.id.sc9);
        sc10 = findViewById(R.id.sc10);
        sc11 = findViewById(R.id.sc11);
        sc12 = findViewById(R.id.sc12);
        sc13 = findViewById(R.id.sc13);
        sc14 = findViewById(R.id.sc14);
        sc15 = findViewById(R.id.sc15);
        sc16 = findViewById(R.id.sc16);
        sc17 = findViewById(R.id.sc17);
        sc18 = findViewById(R.id.sc18);
        sc19 = findViewById(R.id.sc19);
        sc20 = findViewById(R.id.sc20);
        sc21 = findViewById(R.id.sc21);
        sc22 = findViewById(R.id.sc22);
        sc23 = findViewById(R.id.sc23);
        sc24 = findViewById(R.id.sc24);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b10 = findViewById(R.id.b10);
        b11 = findViewById(R.id.b11);
        b12 = findViewById(R.id.b12);
        b13 = findViewById(R.id.b13);
        b14 = findViewById(R.id.b14);
        b15 = findViewById(R.id.b15);
        b16 = findViewById(R.id.b16);
        b17 = findViewById(R.id.b17);
        b18 = findViewById(R.id.b18);
        b19 = findViewById(R.id.b19);
        b20 = findViewById(R.id.b20);
        b21 = findViewById(R.id.b21);
        b22 = findViewById(R.id.b22);
        b23 = findViewById(R.id.b23);
        b24 = findViewById(R.id.b24);

        sc25_total = findViewById(R.id.sc25_total);
        p25_total = findViewById(R.id.p25_total);
        o25_total = findViewById(R.id.o25_total);

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
        p25_total.setText(String.valueOf(isValid(p1)
                + isValid(p2)
                + isValid(p3)
                + isValid(p5)
                + isValid(p6)
                + isValid(p7)
                + isValid(p8)
                + isValid(p8)
                + isValid(p9)
                + isValid(p10)
                + isValid(p11)
                + isValid(p12)
                + isValid(p13)
                + isValid(p14)
                + isValid(p15)
                + isValid(p16)
                + isValid(p17)
                + isValid(p18)
                + isValid(p19)
                + isValid(p20)
                + isValid(p21)
                + isValid(p22)
                + isValid(p23)
                + isValid(p24)
        ));


        sc25_total.setText(String.valueOf(isValid(sc1)
                + isValid(sc2)
                + isValid(sc3)
                + isValid(sc5)
                + isValid(sc6)
                + isValid(sc7)
                + isValid(sc8)
                + isValid(sc9)
                + isValid(sc10)
                + isValid(sc11)
                + isValid(sc12)
                + isValid(sc13)
                + isValid(sc14)
                + isValid(sc15)
                + isValid(sc16)
                + isValid(sc17)
                + isValid(sc18)
                + isValid(sc19)
                + isValid(sc20)
                + isValid(sc21)
                + isValid(sc22)
                + isValid(sc23)
                + isValid(sc24)
        ));

        o25_total.setText(String.valueOf(isValid(o1)
                + isValid(o2)
                + isValid(o3)
                + isValid(o5)
                + isValid(o6)
                + isValid(o7)
                + isValid(o8)
                + isValid(o9)
                + isValid(o10)
                + isValid(o11)
                + isValid(o12)
                + isValid(o13)
                + isValid(o14)
                + isValid(o15)
                + isValid(o16)
                + isValid(o17)
                + isValid(o18)
                + isValid(o19)
                + isValid(o20)
                + isValid(o21)
                + isValid(o22)
                + isValid(o23)
                + isValid(o24)
        ));
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
        isNonZero(table, b9, p9, o9, sc9);
        isNonZero(table, b10, p10, o10, sc10);
        isNonZero(table, b11, p11, o11, sc11);
        isNonZero(table, b12, p12, o12, sc12);
        isNonZero(table, b13, p13, o13, sc13);
        isNonZero(table, b14, p14, o14, sc14);
        isNonZero(table, b15, p15, o15, sc15);
        isNonZero(table, b16, p16, o16, sc16);
        isNonZero(table, b17, p17, o17, sc17);
        isNonZero(table, b18, p18, o18, sc18);
        isNonZero(table, b19, p19, o19, sc19);
        isNonZero(table, b20, p20, o20, sc20);
        isNonZero(table, b21, p21, o21, sc21);
        isNonZero(table, b22, p22, o22, sc22);
        isNonZero(table, b23, p23, o23, sc23);
        isNonZero(table, b24, p24, o24, sc24);
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
        tb_pack.setText("Box");
        tb_pack.setTextColor(getColor(R.color.colorPrimary));
        tb_pack.setGravity(Gravity.CENTER);
        item.addView(tb_pack);

        TextView tb_outer = new TextView(this);
        tb_outer.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_outer.setText("CRTN");
        tb_outer.setTextColor(getColor(R.color.colorPrimary));
        tb_outer.setGravity(Gravity.CENTER);
        item.addView(tb_outer);

        TextView tb_box = new TextView(this);
        tb_box.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_box.setText("SC(Box)");
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
        tb_pack.setText(p25_total.getText().toString());
        tb_pack.setTextColor(getColor(R.color.colorPrimary));
        tb_pack.setGravity(Gravity.CENTER);
        item.addView(tb_pack);

        TextView tb_outer = new TextView(this);
        tb_outer.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_outer.setText(o25_total.getText().toString());
        tb_outer.setTextColor(getColor(R.color.colorPrimary));
        tb_outer.setGravity(Gravity.CENTER);
        item.addView(tb_outer);

        TextView tb_box = new TextView(this);
        tb_box.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        tb_box.setText(sc25_total.getText().toString());
        tb_box.setTextColor(getColor(R.color.colorPrimary));
        tb_box.setGravity(Gravity.CENTER);
        item.addView(tb_box);

        tr.addView(item);
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
