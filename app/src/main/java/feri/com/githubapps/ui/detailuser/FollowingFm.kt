package feri.com.githubapps.ui.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import feri.com.githubapps.databinding.FragmentFollowingFmBinding
import feri.com.githubapps.shared.getViewModel
import feri.com.githubapps.ui.adapter.AdapterUser

class FollowingFm : Fragment(){

    private lateinit var binding: FragmentFollowingFmBinding
    private lateinit var vm: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingFmBinding.inflate(inflater, container, false)
        vm = getViewModel { FollowingViewModel() }
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFollowingData()
    }

    private fun getFollowingData() {
        vm.getFollowing(arguments?.getString("nama", ""))
        vm.dataRepo.observe(this, Observer {
            if (it!=null) binding.rvFollowing.adapter = AdapterUser(requireContext(), it,false)
        })
    }
}