package com.kfe.conductortest.controllers

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.kfe.conductortest.R
import com.kfe.conductortest.bindView
import com.kfe.conductortest.utils.getRootController

/**
 * @Author Fedarets Kanstantsin
 */
class ParentController : BaseController {

    interface ToolbarController {
        val title: String?
    }

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    private val drawerLayout by bindView<DrawerLayout>(R.id.drawer_layout)
    private val toolbarView by bindView<Toolbar>(R.id.toolbar_view)
    private val navigationView by bindView<NavigationView>(R.id.navigation_drawer_view)
    private val controllerContainer by bindView<ViewGroup>(R.id.controller_container)
    private lateinit var controllersRouter: Router
    private var drawerToggle: ActionBarDrawerToggle? = null

    private var currentItemId: Int? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_parent, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentItemId?.apply {
            outState.putInt(KEY_MENU_ITEM_ID, this)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(KEY_MENU_ITEM_ID)) {
            currentItemId = savedInstanceState.getInt(KEY_MENU_ITEM_ID).apply {
                openMenuItem(this)
            }
        }
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        setHasOptionsMenu(true)
        controllersRouter = getChildRouter(controllerContainer)
        initDrawer()
        initNavigationMenu()
        updateMenuItem()
        updateToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbarView,
            0,
            0
        ).apply {
            drawerLayout.addDrawerListener(this)
        }
    }

    override fun onDestroyView(view: View) {
        drawerToggle = null
        super.onDestroyView(view)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        drawerToggle?.syncState()
    }

    private fun updateMenuItem() {
        openMenuItem(currentItemId ?: R.id.menu_contacts)
    }

    override fun handleBack(): Boolean {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers()
            return true
        } else {
            return super.handleBack()
        }
    }

    private fun initNavigationMenu() {
        navigationView.apply {
            setNavigationItemSelectedListener { item ->
                drawerLayout.closeDrawers()
                handleMenuClick(item)
            }
        }
    }

    private fun openMenuItem(menuItemId: Int) {
        navigationView.menu.performIdentifierAction(menuItemId, 0)
        navigationView.setCheckedItem(menuItemId)
    }

    private fun handleMenuClick(item: MenuItem): Boolean {
        if (currentItemId == item.itemId) {
            return true
        }
        return when (item.itemId) {
            R.id.menu_contacts -> {
                startController(ContactsController(), item.itemId)
                true
            }
            R.id.menu_profile -> {
                startController(ProfileController(), item.itemId)
                true
            }
            else -> false
        }
    }

    private fun updateToolbar() {
        (controllersRouter.getRootController() as? ToolbarController)?.title?.apply {
            toolbarView.title = this
        }

    }

    private fun <T> startController(controller: T, itemId: Int)
            where T : Controller,
                  T : ToolbarController {
        controllersRouter.setRoot(RouterTransaction.with(controller))
        toolbarView.title = controller.title
        currentItemId = itemId
    }

    private companion object {
        private const val KEY_MENU_ITEM_ID = "ParentController.item_id"
    }
}