package com.github.tamir7.contacts;

/**
 * Created by Pankaj Soni <pankajsoni@softwarejoint.com> on 26/12/17.
 * Copyright (c) 2017 ${$COMPANY}. All rights reserved.
 */
public class Account {

    private final String accountName;
    private final String accountType;

    Account(String accountName, String accountType) {

        this.accountName = accountName;
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }
}
