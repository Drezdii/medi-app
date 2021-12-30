package com.bartoszdrozd.mediapp.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bartoszdrozd.mediapp.R
import com.bartoszdrozd.mediapp.auth.viewmodels.SignInViewModel
import com.bartoszdrozd.mediapp.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewmodel: SignInViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

//    private var signInLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                lifecycleScope.launchWhenCreated {
//                    val googleUser = GoogleSignIn.getSignedInAccountFromIntent(result.data).await()
//
//                    val credential = GoogleAuthProvider.getCredential(googleUser.idToken, null)
//                    try {
//                        FirebaseAuth.getInstance().signInWithCredential(credential).await()
//                    } catch (e: Exception) {
//
//                    }
//                }
//            }
//        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("770555527219-ae025vpurmkfmdsd77ppmjq0o5p5oct9.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            signUpLink.setOnClickListener {
                navController.navigate(R.id.action_nav_graph_register)
            }

            signInButton.setOnClickListener {
                val email = binding.loginText.text.toString()
                val password = binding.passwordText.text.toString()
                viewmodel.signIn(email, password)
            }

            forgotPasswordLink.setOnClickListener {
                navController.navigate(R.id.action_signin_to_resetPassword)
            }

            signWithGoogle.setOnClickListener {
                signInWithGoogle()
            }
        }

        viewmodel.validationError.observe(viewLifecycleOwner, { error ->
            binding.login.error = if (error != null) {
                resources.getString(error.messageResId)
            } else {
                null
            }
        })

        viewmodel.signInSuccessEvent.onEach {
            if (it == 1) {
                navController.navigate(R.id.action_signin_to_dashboard)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//
//        signInLauncher.launch(signInIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}