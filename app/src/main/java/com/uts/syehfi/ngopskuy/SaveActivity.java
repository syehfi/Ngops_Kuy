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

    private EditText descriptionInput;
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
        descriptionInput = findViewById(R.id.input_name);
        typeRadioGroup = findViewById(R.id.group_type);
        jumlah = findViewById(R.id.amount_text);
        jumlahHarga = findViewById(R.id.jumlah_harga);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            item = extras.getParcelable(MainActivity.TRANSACTION_KEY);
            index = extras.getInt(MainActivity.INDEX_KEY, 0);
            descriptionInput.setText(item.getDescription());
            jumlahHarga.setText(String.valueOf(item.getAmount()));

            if (item.getType() == Transaction.Type.STRONG){
                typeRadioGroup.check(R.id.radio_strong);
            } else if (item.getType() == Transaction.Type.CREAMY){
                typeRadioGroup.check(R.id.radio_creamy);
            }
        }
    }

    private Transaction.Type getCheckedType() {
        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_strong) {
            return Transaction.Type.STRONG;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_creamy) {
            return Transaction.Type.CREAMY;
        }
        return Transaction.Type.EMPTY;
    }

    public void addAmount(View view) {

        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_strong){
            harga = 10000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_creamy){
            harga = 12000;
        }

        if (jumlahPesanan < 20){
            jumlahPesanan++;
            int jmlHarga = jumlahPesanan * harga;
            jumlahHarga.setText(String.valueOf(jmlHarga));
            jumlah.setText(String.valueOf(jumlahPesanan));
        } else {
            Toast.makeText(this, "Batas Pemesanan Hanya 20 Cup", Toast.LENGTH_LONG).show();
        }
    }

    public void minAmount(View view) {

        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_strong){
            harga = 10000;
        } else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_creamy){
            harga = 12000;
        }

        if (jumlahPesanan > 1){
            jumlahPesanan--;
            int jmlHarga = jumlahPesanan * harga;
            jumlahHarga.setText(String.valueOf(jmlHarga));
            jumlah.setText(String.valueOf(jumlahPesanan));
        } else {
            Toast.makeText(this, "Minimal Pemesanan 1 Cup", Toast.LENGTH_LONG).show();
        }
    }

    public void handleOrder(View view) {
        String description = descriptionInput.getText().toString();
        int amount = Integer.parseInt(jumlahHarga.getText().toString());
        Transaction.Type type = getCheckedType();

        item.setDescription(description);
        item.setAmount(amount);
        item.setType(type);

        Intent intent = new Intent();
        intent.putExtra(MainActivity.TRANSACTION_KEY, item);
        intent.putExtra(MainActivity.INDEX_KEY, index);
        setResult(RESULT_OK, intent);
        finish();
    }
}
