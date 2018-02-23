package com.softwarejoint.contact;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.PhoneNumber;
import com.github.tamir7.contacts.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements PermissionCallBack {

    private static final String TAG = "MainActivity";

    private boolean permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager.readContactPermission(this, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionResult(requestCode, grantResults, this);
    }

    @Override
    public void onAccessPermission(boolean permissionGranted, int permission) {
       this.permissionGranted = permissionGranted;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!permissionGranted) return;
        List<Contact> contacts = getPhoneContacts();
        for (Contact contact: contacts) {
            Log.d(TAG,  "Contact: \tname: " + contact.getDisplayName()  + "\tlookUpKey: " + contact.getLookUpKey() + "\tid: " + contact.getId());
            for (PhoneNumber number: contact.getPhoneNumbers()) {
                Log.d(TAG, "\tNumber: account: " + number.getAccountType() + "\tnumber: " + number.getNumber());
            }
        }
    }

    public List<Contact> getPhoneContacts() {
        List<String> filterAccounts = new ArrayList<>();
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account: accounts) {
            filterAccounts.add(account.type);
        }

        filterAccounts.remove("com.google");

        return Query.newQuery(this)
                .hasPhoneNumber()
                .filterDuplicates(true)
                .filterAccounts(filterAccounts)
                .setMinPhoneNumberLength(6)
                .include(Contact.Field.DisplayName, Contact.Field.PhoneNumber,
                        Contact.Field.PhoneNormalizedNumber, Contact.Field.AccountName,
                        Contact.Field.AccountType)
                .find();
    }
}