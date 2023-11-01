package com.example.pakaian;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener {

    Pakaian pakaian = new Pakaian();
    TableLayout tabelPakaian;

    Button buttonTambahPakaian;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();

    JSONArray arrayPakaian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //pengenalan variabel
        tabelPakaian = (TableLayout) findViewById(R.id.tablePakaian);
        buttonTambahPakaian = (Button) findViewById(R.id.buttonTambahPakaian);
        buttonTambahPakaian.setOnClickListener(this);

        TableRow barisTabel = new TableRow(this);
        int warnaSesuaiKeinginan = Color.rgb(255, 221, 131);
        barisTabel.setBackgroundColor(warnaSesuaiKeinginan);

        TextView viewHeaderMerk = new TextView(this);
        TextView viewHeaderJenis = new TextView(this);
        TextView viewHeaderUkuran = new TextView(this);
        TextView viewHeaderHarga = new TextView(this);
        TextView viewHeaderAction = new TextView(this);

        viewHeaderMerk.setText("Merk");
        viewHeaderJenis.setText("Jenis");
        viewHeaderUkuran.setText("Ukuran");
        viewHeaderHarga.setText("Harga");
        viewHeaderAction.setText("Action");


        viewHeaderMerk.setPadding(5, 1, 5, 1);
        viewHeaderJenis.setPadding(5, 1, 5, 1);
        viewHeaderUkuran.setPadding(5, 1, 5, 1);
        viewHeaderHarga.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);


        barisTabel.addView(viewHeaderMerk);
        barisTabel.addView(viewHeaderJenis);
        barisTabel.addView(viewHeaderUkuran);
        barisTabel.addView(viewHeaderHarga);
        barisTabel.addView(viewHeaderAction);

        tabelPakaian.addView(barisTabel, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        try {

            arrayPakaian = new JSONArray(pakaian.tampilPakaian());

            for (int i = 0; i < arrayPakaian.length(); i++) {
                JSONObject jsonChildNode = arrayPakaian.getJSONObject(i);
                String merk = jsonChildNode.optString("merk");
                String jenis = jsonChildNode.optString("jenis");
                String ukuran = jsonChildNode.optString("ukuran");
                String harga = jsonChildNode.optString("harga");
//                String id = jsonChildNode.optString("id");

                System.out.println("Merk :" + merk);
                System.out.println("Jenis :" + jenis);
                System.out.println("Ukuran :" + ukuran);
                System.out.println("Harga :" + harga);
//                System.out.println("ID :" + id);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

//                TextView viewId = new TextView(this);
//                viewId.setText(id);
//                viewId.setPadding(5, 1, 5, 1);
//                barisTabel.addView(viewId);

                TextView viewMerk = new TextView(this);
                viewMerk.setText(merk);
                viewMerk.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewMerk);

                TextView viewJenis = new TextView(this);
                viewJenis.setText(jenis);
                viewJenis.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewJenis);

                TextView viewUkuran = new TextView(this);
                viewUkuran.setText(ukuran);
                viewUkuran.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewUkuran);

                TextView viewHarga = new TextView(this);
                viewHarga.setText(harga);
                viewHarga.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewHarga);

                buttonEdit.add(i, new Button(this));
//                buttonEdit.get(i).setId(Integer.parseInt(id));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
//                buttonDelete.get(i).setId(Integer.parseInt(id));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Delete");
                buttonDelete.get(i).setOnClickListener(this);
                barisTabel.addView(buttonDelete.get(i));

                tabelPakaian.addView(barisTabel, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {

        if (view.getId() == R.id.buttonTambahPakaian) {
            // Toast.makeText(MainActivity.this, "Button Tambah Data",
            // Toast.LENGTH_SHORT).show();

            tambahPakaian();

        } else {
            /*
             * Melakukan pengecekan pada data array, agar sesuai dengan index
             * masing-masing button
             */
            for (int i = 0; i < buttonEdit.size(); i++) {

                /* jika yang diklik adalah button edit */
                if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                    // Toast.makeText(MainActivity.this, "Edit : " +
                    // buttonEdit.get(i).getId(), Toast.LENGTH_SHORT).show();
                    int id = buttonEdit.get(i).getId();
                    getDataByID(id);

                } /* jika yang diklik adalah button delete */
                else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                    // Toast.makeText(MainActivity.this, "Delete : " +
                    // buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
                    int id = buttonDelete.get(i).getId();
                    deletePakaian(id);

                }
            }
        }
    }

    public void deletePakaian(int id) {
        pakaian.deletePakaian(id);

        /* restart acrtivity */
        finish();
        startActivity(getIntent());

    }

    public void getDataByID(int id) {

        String merkEdit = null, jenisEdit = null, ukuranEdit = null, hargaEdit = null;
        JSONArray arrayPersonal;

        try {

            arrayPersonal = new JSONArray(pakaian.getPakaianById(id));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                merkEdit = jsonChildNode.optString("merk");
                jenisEdit = jsonChildNode.optString("jenis");
                ukuranEdit = jsonChildNode.optString("ukuran");
                hargaEdit = jsonChildNode.optString("harga");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // buat id tersembunyi di alertbuilder
        final TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);

        final EditText editMerk = new EditText(this);
        editMerk.setText(merkEdit);
        layoutInput.addView(editMerk);

        final EditText editJenis = new EditText(this);
        editJenis.setText(jenisEdit);
        layoutInput.addView(editJenis);

        final EditText editUkuran = new EditText(this);
        editUkuran.setText(ukuranEdit);
        layoutInput.addView(editUkuran);

        final EditText editHarga = new EditText(this);
        editHarga.setText(hargaEdit);
        layoutInput.addView(editHarga);

        AlertDialog.Builder builderEditPakaian = new AlertDialog.Builder(this);
        builderEditPakaian.setIcon(R.drawable.batagrams);
        builderEditPakaian.setTitle("Update Pakaian");
        builderEditPakaian.setView(layoutInput);
        builderEditPakaian.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String merk = editMerk.getText().toString();
                String jenis = editJenis.getText().toString();
                String ukuran = editUkuran.getText().toString();
                String harga = editHarga.getText().toString();

                System.out.println("Merk : " + merk + " Jenis : " + jenis+ " Ukuran : " + ukuran+ " Harga : " + harga);

                String laporan = pakaian.updatePakaian(viewId.getText().toString(), editMerk.getText().toString(),
                        editJenis.getText().toString(),editUkuran.getText().toString(),  editHarga.getText().toString());

                Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();

                /* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderEditPakaian.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderEditPakaian.show();

    }

    public void tambahPakaian() {
        /* layout akan ditampilkan pada AlertDialog */
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editMerk = new EditText(this);
        editMerk.setHint("Merk");
        layoutInput.addView(editMerk);

        final EditText editJenis = new EditText(this);
        editJenis.setHint("Jenis");
        layoutInput.addView(editJenis);

        final EditText editUkuran = new EditText(this);
        editUkuran.setHint("Ukuran");
        layoutInput.addView(editUkuran);

        final EditText editHarga = new EditText(this);
        editHarga.setHint("Harga");
        layoutInput.addView(editHarga);

        AlertDialog.Builder builderInsertPakaian = new AlertDialog.Builder(this);
        builderInsertPakaian.setTitle("Insert Pakaian");
        builderInsertPakaian.setView(layoutInput);
        builderInsertPakaian.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String merk = editMerk.getText().toString();
                String jenis = editJenis.getText().toString();
                String ukuran = editUkuran.getText().toString();
                String harga = editHarga.getText().toString();

                System.out.println("Merk : " + merk + " Jenis : " + jenis+ " Ukuran : " + ukuran+ " Harga : " + harga);

                String laporan = pakaian.inserPakaian(merk, jenis, ukuran, harga);

                Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();

                /* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderInsertPakaian.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderInsertPakaian.show();
    }

    public void next(View view) {
        Intent intent = new Intent(MainActivity.this, ListViewPakaian.class);
        startActivity(intent);
    }

}