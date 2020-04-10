package com.uts.syehfi.ngopskuy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {

    public enum Menu {
        EMPTY,
        MENU1,
        MENU2,
        MENU3,
        MENU4,
//        Menu 1 Duo
        MENU1_DUO1,
        MENU1_DUO2,
        MENU1_DUO3,
//        Menu 2 Duo
        MENU2_DUO1,
        MENU2_DUO2,
//        Menu 3 Duo
        MENU3_DUO1,
//        Menu All
        MENU_ALL
    }

    private String nama;
    private int jmlHarga;
    private int jumlah;
    private Menu menu;

    public Transaction(){

    }

    public Transaction(String nama, int jmlHarga, int jumlah, Menu menu) {
        this.nama = nama;
        this.jmlHarga = jmlHarga;
        this.jumlah = jumlah;
        this.menu = menu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJmlHarga() {
        return jmlHarga;
    }

    public void setJmlHarga(int jmlHarga) {
        this.jmlHarga = jmlHarga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    protected Transaction(Parcel in) {
        nama = in.readString();
        jmlHarga = in.readInt();
        jumlah = in.readInt();
        int tmpType = in.readInt();
        this.menu = tmpType == -1 ? null : Menu.values()[tmpType];
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeInt(jmlHarga);
        dest.writeInt(jumlah);
        dest.writeInt(this.menu == null ? -1 : this.menu.ordinal());
    }
}
