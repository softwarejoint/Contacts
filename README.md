# Contacts

Android Contacts API.

[![Release](https://jitpack.io/v/softwarejoint/Contacts.svg)](https://jitpack.io/#softwarejoint/Contacts)

## Installation

Published to JitPack

Add to build.gradle (app)
```
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }

    dependencies {
        compile 'com.github.softwarejoint:Contacts:1.3.1'
    }
    
```

## Quick Start

Get All Contacts 

```
    List<Contact> contacts = Contacts.getQuery(context).find();
```

Get Contacts with phone numbers only

```
    Query q = Contacts.getQuery(context);
    q.hasPhoneNumber();
    List<Contact> contacts = q.find();
```

Get Unique Contacts with phone numbers only

```
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
                .include(Contact.Field.DisplayName, Contact.Field.PhoneNumber,
                        Contact.Field.PhoneNormalizedNumber, Contact.Field.AccountName,
                        Contact.Field.AccountType)
                .find();
    }
```

Get Account
```
    PhoneNumber number = getPhoneContact();
    Account account = number.getAccount();
    account.getAccountType();
    account.getAccountName();
    
```

Get Specific fields

```
    Query q = Contacts.getQuery(context);
    q.include(Contact.Field.DisplayName, Contact.Field.Email, Contact.Field.PhotoUri);
    List<Contact> contacts = q.find();
```

Search By Display Name

```
    Query q = Contacts.getQuery(context);
    q.whereContains(Contact.Field.DisplayName, "some string");
    List<Contact> contacts = q.find();
```

Find all numbers with a specific E164 code

```
    Query q = Contacts.getQuery(context);
    q.whereStartsWith(Contact.Field.PhoneNormalizedNumber, "+972");
    List<Contact> contacts = q.find();
```

Find a Contact by phone Number

```
    Query q = Contacts.getQuery(context);
    q.whereEqualTo(Contact.Field.PhoneNumber, "Some phone Number");
    List<Contact> contacts = q.find();
```

Get all Contacts that their name begins with a specific string OR their phone begins with a specific prefix.

```
    Query mainQuery = Contacts.getQuery(context);
    Query q1 = Contacts.getQuery(context);
    q1.whereStartsWith(Contact.Field.DisplayName, "Some String");
    Query q2 = Contacts.getQuery(context);
    q2.whereStartsWith(Contact.Field.PhoneNormalizedNumber, "+972");
    List<Query> qs = new ArrayList<>();
    qs.add(q1);
    qs.add(q2);
    mainQuery.or(qs);
    List<Contact> contacts = mainQuery.find();

```

## License

    Copyright 2016 Tamir Shomer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
