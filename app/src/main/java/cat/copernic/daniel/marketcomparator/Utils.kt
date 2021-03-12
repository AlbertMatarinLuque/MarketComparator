package cat.copernic.daniel.marketcomparator

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.LiveData
import cat.copernic.daniel.marketcomparator.model.UsuariDTO
import com.google.android.material.navigation.NavigationView


private lateinit var navView: NavigationView

fun setnavView(nav: NavigationView) {
    navView = nav
}

fun updateNav(user: LiveData<UsuariDTO>) {
    val headerView: View
    headerView = navView.getHeaderView(0)
    val headerUserName: TextView
    val headerUserMail: TextView
    headerUserName = headerView.findViewById(R.id.nav_username)
    headerUserMail = headerView.findViewById(R.id.nav_user_mail)
    Log.e("User", user.value.toString())
    if (user.value!!.nomUsuari == "") {
        headerUserMail.text = user.value!!.mail
        headerUserName.text = user.value!!.nomUsuari
    } else {
        headerUserMail.text = user.value!!.mail
        headerUserName.text = user.value!!.nomUsuari
    }
    if (user.value!!.permisos.equals("admin")) {
        getnav().menu.findItem(R.id.admin).isVisible = true
    }
}

fun updateNavAnonimo() {
    val headerView: View
    headerView = navView.getHeaderView(0)
    val headerUserName: TextView
    val headerUserMail: TextView
    headerUserName = headerView.findViewById(R.id.nav_username)
    headerUserMail = headerView.findViewById(R.id.nav_user_mail)
    headerUserMail.text = "COMPTE ANÒNIMA"
    headerUserName.text = ""
    getnav().menu.findItem(R.id.admin).isVisible = false
}


fun hideKeyBoard(activity: Activity) {
    val input: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
}


