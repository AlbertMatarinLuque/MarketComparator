package cat.copernic.daniel.marketcomparator.ui.configuration.users.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.databinding.FragmentRegisterBinding
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.setcurrentUser
import cat.copernic.daniel.marketcomparator.updateNav
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class RegisterFragment : Fragment() {
    private val repo = Repo()
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var viewModel: RegisterFragmetVM
    private val GOOGLE_SIGN_IN = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.titleRegister)
        binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        mAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(RegisterFragmetVM::class.java)
        var emailEditText = binding.etMail
        var passwordEditText = binding.ptPassword

        binding.signUpButton.setOnClickListener {

            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showPositiveRegisterAlert()
                        currentUser = mAuth.currentUser!!
                        viewModel.currentUser = currentUser
                        viewModel.usuari.nomUsuari = binding.ptUser.text.toString()
                        viewModel.usuari.mail = binding.etMail.text.toString()

                        viewModel.insertDataBBDD()
                        setcurrentUser(currentUser)
                        observeData()
                      //  updateNav(currentUser, null)
                        requireView().findNavController()
                            .navigate(R.id.action_registerFragment_to_nav_home)
                    } else {
                        showNegativeRegisterAlert()
                    }
                }
            } else {
                Snackbar.make(requireView(), getString(R.string.emptyFields), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.googleImageRegister.setOnClickListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showPositiveGoogleAlert()
                                currentUser = mAuth.currentUser!!
                                viewModel.currentUser = currentUser
                                viewModel.usuari.nomUsuari = currentUser.displayName!!
                                viewModel.usuari.mail = currentUser.email!!
                                viewModel.insertDataBBDD()
                                setcurrentUser(currentUser)
                                observeData()
                                //updateNav(currentUser, null)
                                requireView().findNavController()
                                    .navigate(R.id.action_registerFragment_to_nav_home)
                            } else {
                                showNegativeAlert()
                            }
                        }
                }
            } catch (e: ApiException) {
                showNegativeAlert()
            }
        }
    }

    private fun showNegativeRegisterAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.wrongMessage))
        builder.setMessage(getString(R.string.verifyFR))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveRegisterAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.rightMessage))
        builder.setMessage(getString(R.string.verifyCR))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNegativeAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context?.getString(R.string.wrongMessage))
        builder.setMessage(getString(R.string.verifyFRG))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPositiveGoogleAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.perfectMessage))
        builder.setMessage(getString(R.string.verifyCRG))
        builder.setPositiveButton(context?.getString(R.string.acceptMessage), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun observeData() {
        //binding.shimmerViewContainer.startShimmer()
        // updateNav(viewModel.fetchProductData())
        viewModel.fetchProductData().observe(viewLifecycleOwner, Observer {
            //binding.shimmerViewContainer.stopShimmer()
            //binding.shimmerViewContainer.visibility = View.GONE
            //  Log.e("User",it.toString())
            // updateNav(it)
        })
    }
}