package feri.com.githubapps.ui.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import feri.com.githubapps.databinding.FragmentFollowerFmBinding
import feri.com.githubapps.shared.getViewModel
import feri.com.githubapps.ui.adapter.AdapterUser


class FollowerFm : Fragment() {

    private lateinit var binding: FragmentFollowerFmBinding
    private lateinit var vm: FollowerViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerFmBinding.inflate(inflater, container, false)
        vm = getViewModel { FollowerViewModel() }
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFollowerData()
    }

    private fun getFollowerData() {
        vm.getFollower(arguments?.getString("nama", ""))
        vm.dataRepo.observe(this, Observer {
            if (it != null) binding.rvFollower.adapter = AdapterUser(requireContext(), it, false)
        })
    }

}