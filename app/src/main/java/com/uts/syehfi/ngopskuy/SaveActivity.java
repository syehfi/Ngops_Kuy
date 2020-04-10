package com.uts.syehfi.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uts.syehfi.ngopskuy.models.Transaction;

public class SaveActivity extends AppCompatActivity {

    private EditText namaInput;
    private CheckBox menu1, menu2, menu3, menu4;
    private Transaction item;
    private int index;
    private TextView jumlah;
    private TextView jumlahHarga;
    private int jumlahPesanan = 0;
    private int harga;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        namaInput = findViewById(R.id.input_name);
        menu1 = findViewById(R.id.chechbox_menu1);
        menu2 = findViewById(R.id.checkbox_menu2);
        menu3 = findViewById(R.id.checkbox_menu3);
        menu4 = findViewById(R.id.checkbox_menu4);
        jumlah = findViewById(R.id.amount_text);
        jumlahHarga = findViewById(R.id.jumlah_harga);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            item = extras.getParcelable(MainActivity.TRANSACTION_KEY);
            index = extras.getInt(MainActivity.INDEX_KEY, 0);
            namaInput.setText(item.getNama());
            jumlah.setText(String.valueOf(item.getJumlah()));
            jumlahHarga.setText(String.valueOf(item.getJmlHarga()));


            if (item.getMenu() == Transaction.Menu.MENU1_DUO1){
                menu1.setChecked(true);
                menu2.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU1_DUO2){
                menu1.setChecked(true);
                menu3.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU1_DUO3){
                menu1.setChecked(true);
                menu4.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU2_DUO1){
                menu2.setChecked(true);
                menu3.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU2_DUO2){
                menu2.setChecked(true);
                menu4.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU3_DUO1){
                menu3.setChecked(true);
                menu4.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU_ALL){
                menu1.setChecked(true);
                menu2.setChecked(true);
                menu3.setChecked(true);
                menu4.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU1){
                menu1.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU2){
                menu2.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU3){
                menu3.setChecked(true);
            } else if (item.getMenu() == Transaction.Menu.MENU4){
                menu4.setChecked(true);
            }
        }
    }

    private Transaction.Menu getCheckedType() {

        if (menu1.isChecked() && menu2.isChecked() && menu3.isChecked() && menu4.isChecked()){
            return Transaction.Menu.MENU_ALL;
        } else if (menu1.isChecked() && menu2.isChecked()){
            return Transaction.Menu.MENU1_DUO1;
        } else if (menu1.isChecked() && menu3.isChecked()){
            return Transaction.Menu.MENU1_DUO2;
        } else if (menu1.isChecked() && menu4.isChecked()){
            return Transaction.Menu.MENU1_DUO3;
        } else if (menu2.isChecked() && menu3.isChecked()){
            return Transaction.Menu.MENU2_DUO1;
        } else if (menu2.isChecked() && menu4.isChecked()){
            return Transaction.Menu.MENU2_DUO2;
        } else if (menu3.isChecked() && menu4.isChecked()) {
            return Transaction.Menu.MENU3_DUO1;
        } else if (menu1.isChecked()) {
            return Transaction.Menu.MENU1;
        } else if (menu2.isChecked()) {
            return Transaction.Menu.MENU2;
        } else if (menu3.isChecked()){
            return Transaction.Menu.MENU3;
        } else if (menu4.isChecked()){
            return Transaction.Menu.MENU4;
        }
        return Transaction.Menu.EMPTY;
    }

    public void itemPrice(){

        if (menu1.isChecked() && menu2.isChecked() && menu3.isChecked() && menu4.isChecked()){
            harga = 10000 + 12000 + 15000 + 8000;
        } else if (menu1.isChecked() && menu2.isChecked()){
            harga = 10000 + 12000;
        } else if (menu1.isChecked() && menu3.isChecked()){
            harga = 10000 + 15000;
        } else if (menu1.isChecked() && menu4.isChecked()){
            harga = 10000 + 8000;
        }else if (menu2.isChecked() && menu3.isChecked()){
            harga = 12000 + 15000;
        } else if (menu2.isChecked() && menu4.isChecked()){
            harga = 12000 + 8000;
        } else if (menu3.isChecked() && menu4.isChecked()) {
            harga = 15000 + 8000;
        } else if (menu1.isChecked()){
            harga = 10000;
        } else if (menu2.isChecked()){
            harga = 12000;
        } else if (menu3.isChecked()){
            harga = 15000;
        } else if (menu4.isChecked()){
            harga = 8000;
        }
    }

    public void addAmount(View view) {
        if (jumlahPesanan < 10){
            itemPrice();
            jumlahPesanan++;
            int jmlHarga = jumlahPesanan * harga;
            jumlahHarga.setText(String.valueOf(jmlHarga));
            jumlah.setText(String.valueOf(jumlahPesanan));
        }
    }

    public void minAmount(View view) {
        if (jumlahPesanan >= 1 ){
            itemPrice();
            jumlahPesanan--;
            int jmlHarga = jumlahPesanan * harga;
            jumlahHarga.setText(String.valueOf(jmlHarga));
            jumlah.setText(String.valueOf(jumlahPesanan));
        } else {
            jumlahHarga.setText("0");
            jumlah.setText("0");
        }
    }

    public void handleOrder(View view) {
        String nama = namaInput.getText().toString();
        int total = Integer.parseInt(jumlahHarga.getText().toString());
        int jmlHarga = Integer.parseInt(jumlah.getText().toString());
        Transaction.Menu menu = getCheckedType();

        item.setNama(nama);
        item.setJmlHarga(total);
        item.setJumlah(jmlHarga);
        item.setMenu(menu);

        if (nama.isEmpty()){
            namaInput.setError("Isi terlebih dahulu");
        } else if (getCheckedType() == Transaction.Menu.EMPTY){
            Toast.makeText(getApplicationContext(), "Pilih salah satu menu terlebih dahulu",Toast.LENGTH_LONG).show();
        } else if (jmlHarga == 0){
            Toast.makeText(getApplicationContext(), "Pesanan minimal 1 Cup",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.TRANSACTION_KEY, item);
            intent.putExtra(MainActivity.INDEX_KEY, index);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
