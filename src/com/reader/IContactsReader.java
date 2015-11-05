package com.reader;

import java.util.List;

import com.contacts.Contacts;
import com.contacts.ContactsInitializer;

public interface IContactsReader {
	public List<Contacts> readContacts(ContactsInitializer contactsInitializer);
}
