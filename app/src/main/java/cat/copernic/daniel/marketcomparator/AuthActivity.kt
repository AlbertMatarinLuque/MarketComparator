package cat.copernic.daniel.marketcomparator

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cat.copernic.daniel.marketcomparator.databinding.FragmentAuthActivityBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import kotlinx.coroutines.newFixedThreadPoolContext
import cat.copernic.daniel.marketcomparator.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AuthActivity : Fragment() {

    private lateinit var MainActivity: Intent
    private lateinit var main : MainActivity
    private val GOOGLE_SIGN_IN = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=  DataBindingUtil.inflate<FragmentAuthActivityBinding>(inflater,
            R.layout.fragment_auth_activity, container, false)

      //  MainActivity = Intent(context, cat.copernic.daniel.marketcomparator.MainActivity::class.java)
        var emailEditText = binding.emailEditText
        var passwordEditText = binding.PasswordEditText
        binding.singUpButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showPositiveRegisterAlert()
                        }else{
                            showNegativeRegisterAlert()
                        }
                }
            }
            else{
                showEmptyAlert()
            }
        }

        binding.loginButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showPositiveLoginAlert()
                    }else{
                        showNegativeAlert()
                    }
                }
            }
            else{
                showEmptyAlert()
            }
        }
        binding.googleImage.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
        return binding.root

    }



    private fun showNegativeAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Error!")
        builder.setMessage("S'ha produit un error a l'inicar sessió.")

        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNegativeRegisterAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Error!")
        builder.setMessage("S'ha produit un error al registrarse.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showEmptyAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Error!")
        builder.setMessage("Falten dades per emplenar.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveRegisterAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Correcte!")
        builder.setMessage("T'has registrat correctament.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveLoginAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Perfecte!")
        builder.setMessage("Has iniciat sessió correctament.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            showPositiveRegisterAlert()
                        }
                        else{
                            showNegativeAlert()
                        }
                    }
                }
            } catch (e: ApiException){
                showNegativeAlert()
            }
        }
    }
}