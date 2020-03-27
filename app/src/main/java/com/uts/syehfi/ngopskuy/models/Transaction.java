package com.uts.syehfi.ngopskuy.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {
    public enum Type {
        EMPTY,
        STRONG,
        CREAMY
    }

    private String nama;
    private int jmlHarga;
    private int jumlah;
    private Type type;

    public Transaction() {
    }

    public Transaction(String nama, int jmlHarga, int jumlah, Type type) {
        this.nama = nama;
        this.jmlHarga = jmlHarga;
        this.jumlah = jumlah;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeInt(this.jmlHarga);
        dest.writeInt(this.jumlah);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected Transaction(Parcel in) {
        this.nama = in.readString();
        this.jmlHarga = in.readInt();
        this.jumlah = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
