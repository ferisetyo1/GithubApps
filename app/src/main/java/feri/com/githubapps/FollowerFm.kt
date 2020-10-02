package feri.com.githubapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import kotlinx.android.synthetic.main.fragment_follower_fm.*


class FollowerFm : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower_fm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidNetworking.get("https://api.github.com/users/{username}/followers")
            .addPathParameter("username", arguments?.getString("nama"))
            .addHeaders(
                "Authorization",
                "token 8132a56327cd541be68ed0804d82e437a179a7dc"
            )
            .build()
            .getAsObjectList(ModelUserGeneral::class.java,object :ParsedRequestListener<List<ModelUserGeneral>>{
                override fun onResponse(response: List<ModelUserGeneral>?) {
                    rv_follower.adapter=AdapterUserV2(requireContext(),response)
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(requireContext(), anError?.message,Toast.LENGTH_LONG).show()
                }

            })
    }
}