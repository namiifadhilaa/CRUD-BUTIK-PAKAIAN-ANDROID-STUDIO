package com.example.pakaian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewPakaian extends AppCompatActivity {

    String URL = "http://192.168.100.62/pakaian/server.php";
    String url = "";
    String response = "";
    Pakaian pakaian = new Pakaian();
    ListView listViewPakaian;

    JSONArray arrayPakaian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_pakaian);
        listViewPakaian = findViewById(R.id.listViewPakaian);

        try {
            arrayPakaian = new JSONArray(pakaian.tampilPakaian());
            ArrayList<String> listMerkPakaian = new ArrayList<>();
            final ArrayList<String> listDetailPakaian = new ArrayList<>();
            for (int i = 0; i < arrayPakaian.length(); i++) {
                JSONObject jsonChildNode = arrayPakaian.getJSONObject(i);
                String merk = jsonChildNode.optString("merk");
                String jenis = jsonChildNode.optString("jenis");
                String ukuran = jsonChildNode.optString("ukuran");
                String harga = jsonChildNode.optString("harga");
                String id = jsonChildNode.optString("id");

                listMerkPakaian.add(merk);
                listDetailPakaian.add("ID: " + id + "\nMerk: " + merk + "\nJenis: " + jenis + "\nUkuran: " + ukuran + "\nHarga: " + harga);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMerkPakaian);
            listViewPakaian.setAdapter(adapter);

            listViewPakaian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String detailPakaian = listDetailPakaian.get(position);
                    showDetailDialog(detailPakaian);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetailDialog(String detail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(detail)
                .setTitle("Detail Pakaian")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
