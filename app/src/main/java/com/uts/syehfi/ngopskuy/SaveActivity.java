package com.uts.syehfi.ngopskuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uts.syehfi.ngopskuy.models.Transaction;

public class SaveActivity extends AppCompatActivity {

    private EditText namaInput;
    private RadioGroup typeRadioGroup;
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
        typeRadioGroup = findViewById(R.id.group_type);
        jumlah = findViewById(R.id.amount_text);
        jumlahHarga = findViewById(R.id.jumlah_harga);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            item = extras.getParcelable(MainActivity.TRANSACTION_KEY);
            index = extras.getInt(MainActivity.INDEX_KEY, 0);
            namaInput.setText(item.getNama());
            jumlah.setText(String.valueOf(item.getJumlah()));
            jumlahHarga.setText(String.valueOf(item.getJmlHarga()));

            if (item.getMenu() == Transaction.Menu.MENU1){
                typeRadioGroup.check(R.id.radio_menu1);
            } else if (item.getMenu() == Transaction.Menu.MENU2){
                typeRadioGroup.check(R.id.radio_menu2);
            } else if (item.getMenu() == Transaction.Menu.MENU3){
                typeRadioGroup.check(R.id.radio_menu3);
            } else if (item.getMenu() == Transaction.Menu.MENU4){
                typeRadioGroup.check(R.id.radio_menu4);
            }
        }
    }

    private Transaction.Menu getCheckedType() {
        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu1) {
            return Transaction.Menu.MENU1;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu2) {
            return Transaction.Menu.MENU2;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu3){
            return Transaction.Menu.MENU3;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu4){
            return Transaction.Menu.MENU4;
        }
        return Transaction.Menu.EMPTY;
    }

    public void addAmount(View view) {

        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu1){
            harga = 10000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu2){
            harga = 12000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu3){
            harga = 15000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu4){
            harga = 8000;
        }

        if (jumlahPesanan < 10){
            jumlahPesanan++;
            int jmlHarga = jumlahPesanan * harga;
            jumlahHarga.setText(String.valueOf(jmlHarga));
            jumlah.setText(String.valueOf(jumlahPesanan));
        } else {
            Toast.makeText(this, "Batas Pemesanan Hanya 10 Cup", Toast.LENGTH_LONG).show();
        }
    }

    public void minAmount(View view) {

        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu1){
            harga = 10000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu2){
            harga = 12000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu3){
            harga = 15000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_menu4){
            harga = 8000;
        }

        if (jumlahPesanan > 1 ){
            jumlahPesanan--;
            int jmlHarga = jumlahPesanan * harga;
            jumlahHarga.setText(String.valueOf(jmlHarga));
            jumlah.setText(String.valueOf(jumlahPesanan));
        } else {
            Toast.makeText(this, "Minimal Pemesanan 1 Cup", Toast.LENGTH_LONG).show();
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
