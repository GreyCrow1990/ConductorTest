package com.kfe.conductortest.utils

import android.support.annotation.StringRes
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.kfe.conductortest.MainActivity

/**
 * @Author Fedarets Kanstantsin
 */

fun Controller.getText(@StringRes resId: Int): String? {
    return activity?.getString(resId)
}

fun Controller.rootRouter() = (activity as? MainActivity)?.router

fun Router.getRootController() : Controller? {
    if (hasRootController()) {
        return backstack[0].controller()
    }
    return null
}