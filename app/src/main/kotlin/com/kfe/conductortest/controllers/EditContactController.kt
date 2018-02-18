package com.kfe.conductortest.controllers

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.kfe.conductortest.R
import com.kfe.conductortest.bindView
import com.kfe.conductortest.model.contacts.ContactData
import com.kfe.conductortest.model.contacts.ContactsManager

/**
 * @Author Fedarets Kanstantsin
 */
class EditContactController : BaseController {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    private val contactId: Int = args.getInt(EditContactController.KEY_CONTACT_ID)

    private val toolbarView by bindView<Toolbar>(R.id.toolbar_view)
    private val nameTextView by bindView<EditText>(R.id.profile_name_text_view)
    private val phoneTextView by bindView<EditText>(R.id.phone_text_view)
    private val addressTextView by bindView<EditText>(R.id.address_text_view)
    private val emailTextView by bindView<EditText>(R.id.email_text_view)

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_edit_contact, container, false)
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        initToolbar()
        ContactsManager.find(contactId)?.apply {
            nameTextView.setText(name, TextView.BufferType.EDITABLE)
            phoneTextView.setText(phone, TextView.BufferType.EDITABLE)
            addressTextView.setText(address, TextView.BufferType.EDITABLE)
            emailTextView.setText(email, TextView.BufferType.EDITABLE)
        }
    }

    private fun initToolbar() {
        toolbarView.setNavigationOnClickListener { handleBack() }
    }

    override fun handleBack(): Boolean {
        ContactsManager.edit(ContactData(contactId,
            nameTextView.text.toString(),
            phoneTextView.text.toString(),
            addressTextView.text.toString(),
            emailTextView.text.toString()))
        router.popCurrentController()
        return true
    }

    companion object {
        private const val KEY_CONTACT_ID = "EditContactController.contact"

        fun newInstance(contactId: Int): EditContactController {
            return EditContactController(Bundle().apply {
                putInt(KEY_CONTACT_ID, contactId)
            })
        }
    }
}