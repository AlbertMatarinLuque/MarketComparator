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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AuthActivity : Fragment() {

    private lateinit var MainActivity: Intent
    private lateinit var main : MainActivity
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
                            showPositiveRgisterAlert()
                        }else{
                            showNegativeAlert()
                        }
                }
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
        }

        return binding.root
    }

    private fun showNegativeAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("S'ha produit un error al hora de autentificarse")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveRgisterAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Perfetcte")
        builder.setMessage("T'has autentificat correctament")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveLoginAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Perfetcte")
        builder.setMessage("T'has loginat correctament")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }




}