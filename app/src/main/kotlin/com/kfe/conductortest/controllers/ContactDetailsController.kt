package com.kfe.conductortest.controllers

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.kfe.conductortest.R
import com.kfe.conductortest.bindView
import com.kfe.conductortest.model.contacts.ContactsManager

/**
 * @Author Fedarets Kanstantsin
 */
class ContactDetailsController : BaseController {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    private val contactId: Int by lazy { args.getInt(KEY_CONTACT_ID) }

    private val toolbarView by bindView<Toolbar>(R.id.toolbar_view)
    private val nameTextView by bindView<TextView>(R.id.profile_name_text_view)
    private val phoneTextView by bindView<TextView>(R.id.phone_text_view)
    private val addressTextView by bindView<TextView>(R.id.address_text_view)
    private val emailTextView by bindView<TextView>(R.id.email_text_view)

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(
            R.layout.controller_contact_details,
            container,
            false
        )
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        initToolbar()
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        ContactsManager.find(contactId)?.apply {
            nameTextView.text = name
            phoneTextView.text = phone
            addressTextView.text = address
            emailTextView.text = email
        }
    }

    private fun initToolbar() {
        toolbarView.setNavigationOnClickListener { router.popCurrentController() }
        toolbarView.inflateMenu(R.menu.contact_details_menu)
        toolbarView.setOnMenuItemClickListener { item ->
            return@setOnMenuItemClickListener when (item.itemId) {
                R.id.edit -> {
                    router.pushController(
                        RouterTransaction.with(
                            EditContactController.newInstance(contactId)
                        )
                            .pushChangeHandler(FadeChangeHandler())
                            .popChangeHandler(FadeChangeHandler())
                    )
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val KEY_CONTACT_ID = "ContactDetailsController.contact_id"

        fun newInstance(contactId: Int): ContactDetailsController {
            return ContactDetailsController(Bundle().apply {
                putInt(KEY_CONTACT_ID, contactId)
            })
        }
    }
}