package cat.copernic.daniel.marketcomparator

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


private lateinit var navView:NavigationView
var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
var currentUser: FirebaseUser = mAuth.currentUser!!

fun setnavView(nav: NavigationView){
    navView = nav
}

fun updateNav(currentUser: FirebaseUser, name: String?){
    val headerView: View
    headerView = navView.getHeaderView(0)
    val headerUserName: TextView
    val headerUserMail: TextView
    // val headerUserPhoto: ImageView
    headerUserName = headerView.findViewById(R.id.nav_username)
    headerUserMail = headerView.findViewById(R.id.nav_user_mail)
    // headerUserPhoto = headerView.findViewById(R.id.nav_user_photo)
    if (currentUser.displayName == ""){
        headerUserMail.setText(currentUser.email)
        headerUserName.setText(name)
    } else{
        headerUserMail.setText(currentUser.email)
        headerUserName.setText(currentUser.displayName)
    }

    //  headerUserPhoto
}

fun updateNavAnonimo(){
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
}


