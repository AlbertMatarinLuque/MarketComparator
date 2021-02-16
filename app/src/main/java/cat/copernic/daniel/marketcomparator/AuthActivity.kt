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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import cat.copernic.daniel.marketcomparator.databinding.FragmentAuthActivityBinding
import cat.copernic.daniel.marketcomparator.ui.configuration.users.RegisterFragmetVM
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
    private lateinit var  viewModel: RegisterFragmetVM
    private val GOOGLE_SIGN_IN = 100
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.titleAuthentication)
         binding=  DataBindingUtil.inflate<FragmentAuthActivityBinding>(inflater,
                 R.layout.fragment_auth_activity, container, false)
        mAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(RegisterFragmetVM::class.java)
        val vieww = requireActivity().currentFocus
        var emailEditText = binding.emailEditText
        var passwordEditText = binding.PasswordEditText

        binding.singUpButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_authActivity_to_registerFragment)
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
        builder.setTitle(context?.getString(R.string.wrongMessage))
        builder.setMessage(getString(R.string.verifyFI))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showEmptyAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.wrongMessage))
        builder.setMessage(getString(R.string.verifyE))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveLoginAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.perfectMessage))
        builder.setMessage(getString(R.string.verifyCI))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showPositiveGoogleAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.perfectMessage))
        builder.setMessage(getString(R.string.verifyCIG))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
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
        builder.setTitle(getString(R.string.perfectMessage))
        builder.setMessage(getString(R.string.logOut))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
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