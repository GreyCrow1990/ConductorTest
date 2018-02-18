package com.kfe.conductortest.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kfe.conductortest.R
import com.kfe.conductortest.bindView
import com.kfe.conductortest.model.profile.ProfileManager
import com.kfe.conductortest.utils.getText

/**
 * @Author Fedarets Kanstantsin
 */
class ProfileController : BaseController, ParentController.ToolbarController {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    private val nameTextView by bindView<TextView>(R.id.name_text_view)
    private val emailTextView by bindView<TextView>(R.id.email_text_view)

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_profile, container, false)
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        ProfileManager.profile.apply {
            nameTextView.text = name
            emailTextView.text = email
        }
    }

    override val title: String?
        get() = getText(R.string.screen_profile)
}