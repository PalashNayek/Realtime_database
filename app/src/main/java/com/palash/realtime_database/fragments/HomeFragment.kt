package com.palash.realtime_database.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.palash.realtime_database.R
import com.palash.realtime_database.adapter.UserListAdapter
import com.palash.realtime_database.databinding.FragmentHomeBinding
import com.palash.realtime_database.models.User
import com.palash.realtime_database.view_models.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Delete user...........
        fun deleteItem(user: User) {
            userViewModel.deleteUser(user.id)
        }/////////////////////


        fun itemClick(user: User) {
            val bundle = Bundle()
            bundle.putString("userId", user.id)
            bundle.putString("userName", user.name)
            bundle.putString("userEmail", user.email)

            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(user.id, user.name, user.email)
            findNavController().navigate(direction)

            //findNavController().navigate(R.id.action_homeFragment_to_updateFragment)

            //findNavController().navigate(direction)
        }

        val adapter = UserListAdapter(::deleteItem, ::itemClick)
        userViewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter



        //Add new user........................
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addUserFragment)
        }/////////////////////////////////////////
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}