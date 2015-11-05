package com.main;

import com.contacts.ContactsInitializer;

public class Phonebook {
	public static void main(String args[])	{
		ContactsInitializer i = new ContactsInitializer();
		i.initProperty();
		i.initContacts();
		i.menu(i);
	}
}
