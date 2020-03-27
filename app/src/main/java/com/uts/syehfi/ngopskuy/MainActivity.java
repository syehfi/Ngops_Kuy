package com.uts.syehfi.ngopskuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uts.syehfi.ngopskuy.adapters.TransactionAdapter;
import com.uts.syehfi.ngopskuy.models.Order;
import com.uts.syehfi.ngopskuy.models.Session;
import com.uts.syehfi.ngopskuy.models.Transaction;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TransactionAdapter.OnItemTransactionListener {

    public static final String TRANSACTION_KEY = "TRANSACTION";
    public static final String INDEX_KEY = "INDEX";
    public static final int INSERT_REQUEST = 1;
    public static final int UPDATE_REQUEST = 2;

    private TextView balanceText;
    private RecyclerView transactionsView;
    private TransactionAdapter adapter;
    private Order order;
    private Session session;
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceText = findViewById(R.id.balance_text);
        transactionsView = findViewById(R.id.rv_transaction);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                intent.putExtra(TRANSACTION_KEY, new Transaction());
                startActivityForResult(intent, INSERT_REQUEST);
            }
        });

        order = new Order();
        adapter = new TransactionAdapter(order.getTransactions(), this);
        transactionsView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        transactionsView.setLayoutManager(layoutManager);
        balanceText.setText(formatRupiah.format(order.getBalance()));

        session = Application.getSession();
        if (!session.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                order.removeTransaction(index);
                adapter.notifyDataSetChanged();
                balanceText.setText(formatRupiah.format(order.getBalance()));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(transactionsView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        session.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onTransactionClicked(int index, Transaction item) {
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra(TRANSACTION_KEY, item);
        intent.putExtra(INDEX_KEY, index);
        startActivityForResult(intent, UPDATE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Transaction transaction = data.getParcelableExtra(TRANSACTION_KEY);
            if (requestCode == INSERT_REQUEST) {
                order.addTransaction(transaction);
            } else if (requestCode == UPDATE_REQUEST) {
                int index = data.getIntExtra(INDEX_KEY, 0);
                order.updateTransaction(index, transaction);
            }
            adapter.notifyDataSetChanged();
            balanceText.setText(formatRupiah.format(order.getBalance()));
        }
    }
}
