package com.kfe.conductortest.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.kfe.conductortest.ViewBinder

/**
 * @Author Fedarets Kanstantsin
 */
abstract class BaseController : Controller {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        ViewBinder.setup(this, view)
        onBindView(view)
        return view
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    override fun onDestroyView(view: View) {
        ViewBinder.tearDown(this)
        super.onDestroyView(view)
    }

    open fun onBindView(view: View) { }
}