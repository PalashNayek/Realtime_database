package com.palash.realtime_database.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.palash.realtime_database.R
import com.palash.realtime_database.databinding.FragmentUpdateBinding
import com.palash.realtime_database.models.User
import com.palash.realtime_database.view_models.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()
    /*var name: String? = null
    var email: String? = null*/

    private val args: UpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameEditTextUpdate.setText(args.name)
        binding.emailEditTextUpdate.setText(args.email)

        binding.updateButton.setOnClickListener {
            val name = binding.nameEditTextUpdate.text.toString()
            val email = binding.emailEditTextUpdate.text.toString()

            if (args.id.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty()) {
                val user = User(args.id, name, email)
                userViewModel.updateUser(user)
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}