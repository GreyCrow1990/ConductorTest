package com.kfe.conductortest.controllers

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.kfe.conductortest.R
import com.kfe.conductortest.bindView
import com.kfe.conductortest.model.contacts.ContactData
import com.kfe.conductortest.model.contacts.ContactsManager
import com.kfe.conductortest.utils.getText
import com.kfe.conductortest.utils.rootRouter
import kotlinx.android.synthetic.main.item_contact.view.*

/**
 * @Author Fedarets Kanstantsin
 */
class ContactsController : BaseController,
    ParentController.ToolbarController {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    private val recyclerView by bindView<RecyclerView>(R.id.contacts_list)

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_contacts, container, false)
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        recyclerView.adapter = ContactsAdapter(object : ContactsAdapter.Listener {
            override fun onContactSelected(contactData: ContactData) {
                rootRouter()?.pushController(
                    RouterTransaction.with(
                        ContactDetailsController.newInstance(contactData.id)
                    )
                        .pushChangeHandler(HorizontalChangeHandler())
                        .popChangeHandler(HorizontalChangeHandler())
                )
            }
        })
    }

    override val title: String?
        get() = getText(R.string.screen_contacts)


    private class ContactsAdapter(private val listener: Listener) :
        RecyclerView.Adapter<ContactViewHolder>() {

        interface Listener {
            fun onContactSelected(contactData: ContactData)
        }

        override fun getItemCount(): Int = ContactsManager.contacts.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
            return ContactViewHolder(view)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            holder.bind(ContactsManager.contacts[position], listener)
        }
    }

    private class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTextView = view.nameTextView
        private val phoneTextView = view.phoneTextView

        fun bind(contactData: ContactData, listener: ContactsAdapter.Listener) {
            nameTextView.text = contactData.name
            phoneTextView.text = contactData.phone
            itemView.setOnClickListener { listener.onContactSelected(contactData) }
        }
    }
}