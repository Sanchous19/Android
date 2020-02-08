package com.example.sanchous19;

public interface RssData {
    void notifyDataSetChanged();
    void showDialog(String messageText);
    void dismissDialog();
    void makeToast(final String toastText);
}
