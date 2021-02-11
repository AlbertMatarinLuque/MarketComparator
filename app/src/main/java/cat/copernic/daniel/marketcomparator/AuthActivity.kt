package cat.copernic.daniel.marketcomparator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cat.copernic.daniel.marketcomparator.databinding.FragmentAuthActivityBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase





class AuthActivity : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var MainActivity: Intent
    private lateinit var main : MainActivity
    private lateinit var binding: FragmentAuthActivityBinding
            private val GOOGLE_SIGN_IN = 100
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Autentificarse"
         binding=  DataBindingUtil.inflate<FragmentAuthActivityBinding>(inflater,
                 R.layout.fragment_auth_activity, container, false)
        mAuth = FirebaseAuth.getInstance()
        val vieww = requireActivity().currentFocus
      //  MainActivity = Intent(context, cat.copernic.daniel.marketcomparator.MainActivity::class.java)
        var emailEditText = binding.emailEditText
        var passwordEditText = binding.PasswordEditText
        binding.singUpButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showPositiveRegisterAlert()
                            binding.loginButton.setVisibility(View.GONE)
                            binding.connectionClose.setVisibility(View.VISIBLE)
                            currentUser = mAuth.currentUser!!
                            updateNav(currentUser)
                            requireView().findNavController().navigate(R.id.action_authActivity_to_nav_home)
                        }else{
                            showNegativeRegisterAlert()
                        }
                }
            }
            else{
                showEmptyAlert()
            }
            ocultar()
        }

        binding.loginButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showPositiveLoginAlert()
                        binding.loginButton.setVisibility(View.GONE)
                        binding.connectionClose.setVisibility(View.VISIBLE)
                        currentUser = mAuth.currentUser!!
                        updateNav(currentUser)
                        requireView().findNavController().navigate(R.id.action_authActivity_to_nav_home)
                    }else{
                        showNegativeAlert()
                    }
                }
            }
            else{
                showEmptyAlert()
            }
            ocultar()
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

        binding.connectionClose.setOnClickListener {
            closeConnection()
            updateNavAnonimo()
        }
        binding.connectionClose.setVisibility(View.GONE)
        return binding.root

    }



    private fun showNegativeAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Error!")
        builder.setMessage("S'ha produit un error a l'inicar sessió.")

        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
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

    private fun showPositiveLoginAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Perfecte!")
        builder.setMessage("Has iniciat sessió correctament.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showPositiveGoogleAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Perfecte!")
        builder.setMessage("Ja pots utiltzar la teva compte de Google.")
        builder.setPositiveButton("Aceptar", null)
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
                            showPositiveGoogleAlert()
                            binding.loginButton.setVisibility(View.GONE)
                            binding.connectionClose.setVisibility(View.VISIBLE)
                            currentUser = mAuth.currentUser!!
                            updateNav(currentUser)
                            requireView().findNavController().navigate(R.id.action_authActivity_to_nav_home)
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


    fun closeConnection(){
        Firebase.auth.signOut()
        binding.loginButton.setVisibility(View.VISIBLE)
        binding.connectionClose.setVisibility(View.GONE)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¡Perfecte!")
        builder.setMessage("Has tancat la ssesió")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun ocultar() {
        val vieww: View? = requireActivity().getCurrentFocus()
        if (vieww != null) {
            //Aquí esta la magia
            val input = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(vieww.windowToken, 0)
        }
    }


}