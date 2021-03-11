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
import com.google.firebase.auth.FirebaseUser
import cat.copernic.daniel.marketcomparator.getnav
import cat.copernic.daniel.marketcomparator.ui.configuration.users.authentication.AuthActivity


private lateinit var navView: NavigationView

fun setnavView(nav: NavigationView) {
    navView = nav
}

fun updateNav(user: LiveData<UsuariDTO>) {
    val headerView: View
    headerView = navView.getHeaderView(0)
    val headerUserName: TextView
    val headerUserMail: TextView
    // val headerUserPhoto: ImageView
    headerUserName = headerView.findViewById(R.id.nav_username)
    headerUserMail = headerView.findViewById(R.id.nav_user_mail)
    // headerUserPhoto = headerView.findViewById(R.id.nav_user_photo)
    Log.e("User",user.value.toString())
    if (user.value!!.nomUsuari == "") {
        headerUserMail.setText(user.value!!.mail)
        headerUserName.setText(user.value!!.nomUsuari)
    } else {
        headerUserMail.setText(user.value!!.mail)
        headerUserName.setText(user.value!!.nomUsuari)
    }

    if(user.value!!.permisos.equals("admin")){
        getnav().menu.findItem(R.id.admin).isVisible = true
    }
    //  headerUserPhoto
}

fun updateNavAnonimo() {
    val headerView: View
    headerView = navView.getHeaderView(0)
    val headerUserName: TextView
    val headerUserMail: TextView
    // val headerUserPhoto: ImageView
    headerUserName = headerView.findViewById(R.id.nav_username)
    headerUserMail = headerView.findViewById(R.id.nav_user_mail)
    // headerUserPhoto = headerView.findViewById(R.id.nav_user_photo)

    headerUserMail.setText("COMPTE ANÒNIMA")
    headerUserName.setText("")
    //  headerUserPhoto
    getnav().menu.findItem(R.id.admin).isVisible = false
}


fun hideKeyBoard(activity: Activity){
    val input: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.hideSoftInputFromWindow(activity.getCurrentFocus()!!.getWindowToken(), 0)
}


