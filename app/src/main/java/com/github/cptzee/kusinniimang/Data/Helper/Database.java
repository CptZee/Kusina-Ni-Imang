package com.github.cptzee.kusinniimang.Data.Helper;

import android.content.Context;

public class Database {
    private Context context;

    public Database(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        AccountHelper ah = AccountHelper.instance(context);
        CredentialHelper ch = CredentialHelper.instance(context);
        ItemHelper ih = ItemHelper.instance(context);

        ah.onCreate(ah.getWritableDatabase());
        ch.onCreate(ch.getWritableDatabase());
        ih.onCreate(ih.getWritableDatabase());
    }
}
