package com.kfe.conductortest.model.contacts

/**
 * @Author Fedarets Kanstantsin
 */
object ContactsManager {

    private val _contacts = mutableListOf(
        ContactData(
            1,
            "John Smith",
            "2780429",
            "New York",
            "john@gmail.com"
        ),
        ContactData(
            2,
            "John Snow",
            "7865629",
            "Washington",
            "snow@gmail.com"
        ),
        ContactData(
            3,
            "Peter Pan",
            "6581823",
            "Los Angeles",
            "peter@gmail.com"
        ),
        ContactData(
            4,
            "Albus Bumbledore",
            "6667788",
            "London",
            "albus@gmail.com"
        )
    )

    val contacts: List<ContactData> = _contacts

    fun find(id: Int): ContactData? {
        return contacts.find { it.id == id }
    }

    fun edit(newContactData: ContactData) {
        _contacts[_contacts.indexOf(_contacts.find { it.id == newContactData.id })] = newContactData
    }
}