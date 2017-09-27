package com.github.tamir7.contacts;

/**
 * Created by Pankaj Soni on 27/09/17.
 * Software Joint Pvt. Ltd.
 * pankajsoni@softwarejoint.com
 */
public class Account {
    private final String name;
    private final String type;

    Account(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isLocal() {
        return "com.google".equals(type);
    }
}
