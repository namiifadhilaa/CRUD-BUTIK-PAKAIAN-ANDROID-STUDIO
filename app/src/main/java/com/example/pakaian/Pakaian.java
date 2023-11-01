package com.example.pakaian;

public class Pakaian extends Koneksi{

    String URL = "http://192.168.100.62/pakaian/server.php";
    String url = "";
    String response = "";

    public String tampilPakaian() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Pakaian: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String inserPakaian(String merk, String jenis,String ukuran, String harga) {
        try {
            url = URL + "?operasi=insert&merk=" + merk + "&jenis=" + jenis+ "&ukuran=" + ukuran+ "&harga=" + harga;
            System.out.println("URL Insert Pakaian : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getPakaianById(int id) {
        try {
            url = URL + "?operasi=get_pakaian_by_id&id=" + id;
            System.out.println("URL Insert Pakaian : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updatePakaian(String id, String merk, String jenis, String ukuran, String harga) {
        try {
            url = URL + "?operasi=update&id=" + id + "&merk=" + merk + "&jenis=" + jenis + "&ukuran=" + ukuran + "&harga=" + harga;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deletePakaian(int id) {
        try {
            url = URL + "?operasi=delete&id=" + id;
            System.out.println("URL Insert Pakaian : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
}
