package com.funkymaster.demo.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.funkymaster.demo.data.network.ApiInterface
import com.funkymaster.demo.data.model.Brand
import com.funkymaster.demo.R
import com.funkymaster.demo.data.network.RetrofitInstanceAuthenticated
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

private lateinit var registerBtn: Button
private lateinit var brandTxt: EditText


class FragmentBrand : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_brand, container, false)
        registerBtn = view.findViewById(
            R.id.registerBrandBtn
        )
        brandTxt = view.findViewById(R.id.nameBrand)
        registerBtn.setOnClickListener(View.OnClickListener {
            if(brandTxt.text.toString().equals(""))
                Toast.makeText(getActivity(),"Brand Name is Required!",Toast.LENGTH_LONG).show();
            else{
                registerBrand(brandTxt.text.toString())
            }   //register

        })
        return view
    }




    private fun registerBrand(name: String){
        val retIn = RetrofitInstanceAuthenticated.getRetrofitAuthenticatedInstance()
            .create(ApiInterface::class.java)
        val registerInfo = Brand(name)
        retIn.registerBrand(registerInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    context,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if (response.code() == 200) { //success
                     System.out.println("Mars: "+ response.body()!!.string())
                    Toast.makeText(context, "Registration success!", Toast.LENGTH_LONG)
                        .show()
                    brandTxt.setText("")
                } else{
                    Toast.makeText(context, "registration failed!", Toast.LENGTH_LONG).show()
                } //end of else
            }   //end of on response method
        })
    }   //end of register method

}