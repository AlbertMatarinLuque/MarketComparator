package cat.copernic.daniel.marketcomparator.ui.configuration.users

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentRegisterBinding
import cat.copernic.daniel.marketcomparator.updateNav
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private val GOOGLE_SIGN_IN = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Registre"
        binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        mAuth = FirebaseAuth.getInstance()
        var emailEditText = binding.etMail
        var passwordEditText = binding.ptPassword
        binding.signUpButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        showPositiveRegisterAlert()
                        currentUser = mAuth.currentUser!!
                        updateNav(currentUser)
                        requireView().findNavController().navigate(R.id.action_registerFragment_to_nav_home)
                    }else{
                        showNegativeRegisterAlert()
                    }
                }
            }
            else{
                showEmptyAlert()
            }
        }

        return binding.root
    }

    private fun showNegativeRegisterAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Error!")
        builder.setMessage("S'ha produit un error al registrarse.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showEmptyAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Error!")
        builder.setMessage("Falten dades per emplenar.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveRegisterAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Correcte!")
        builder.setMessage("T'has registrat correctament.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}