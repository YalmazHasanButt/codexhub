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
import com.funkymaster.demo.data.model.Product
import com.funkymaster.demo.R
import com.funkymaster.demo.data.network.RetrofitInstanceAuthenticated
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback



class FragmentProduct : Fragment() {

    private lateinit var name_em: EditText
    private lateinit var barcode: EditText
    private lateinit var sku: EditText
    private lateinit var category: EditText
    private lateinit var cost_price: EditText
    private lateinit var selling_price: EditText
    private lateinit var quanity: EditText
    private lateinit var alert_quantity: EditText
    private lateinit var expiry_date: EditText
    private lateinit var registerProductButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_product, container, false)
        name_em  = view.findViewById(R.id.name_enField)
        barcode = view.findViewById(R.id.barcodeTxt)
        sku = view.findViewById(R.id.sku)
        category = view.findViewById(R.id.category)
        cost_price = view.findViewById(R.id.costPrice)
        selling_price = view.findViewById(R.id.sellingPrice)
        quanity = view.findViewById(R.id.quantity)
        alert_quantity = view.findViewById(R.id.alert_quantity)
        expiry_date = view.findViewById(R.id.dateProductView)
        registerProductButton = view.findViewById(R.id.createProductBtn)


        registerProductButton.setOnClickListener(View.OnClickListener {

            if(name_em.text.toString().equals("") || barcode.text.toString().equals("") || sku.text.toString().equals("") ||
            category.text.toString().equals("") || cost_price.text.toString().equals("") || selling_price.text.toString().equals("") ||
            quanity.text.toString().equals("") ||  alert_quantity.text.toString().equals("") || expiry_date.text.toString().equals("") )
                Toast.makeText(context, "Fill all the fields", Toast.LENGTH_LONG).show()

            else {
                registerProduct(
                    name_em.text.toString(),
                    barcode.text.toString(),
                    sku.text.toString(),
                    category.text.toString().toInt(),
                    cost_price.text.toString().toDouble(),
                    selling_price.text.toString().toDouble(),
                    quanity.text.toString().toInt(),
                    alert_quantity.text.toString().toInt(),
                    expiry_date.text.toString()
                )
            }

        })
        return view
    }


    private fun registerProduct(namE: String, barcodE: String, skU: String, categorY: Int, cost_pricE: Double, selling_pricE: Double,
    quantitY: Int, alert_quantitY: Int, expiry_datE: String){
        val retIn = RetrofitInstanceAuthenticated.getRetrofitAuthenticatedInstance()
            .create(ApiInterface::class.java)
        val registerInfo = Product(
            namE,
            barcodE,
            skU,
            categorY,
            cost_pricE,
            selling_pricE,
            quantitY,
            alert_quantitY,
            expiry_datE
        )
        retIn.registerProduct(registerInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    context,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if (response.code() == 200) { //success
                    Toast.makeText(context, "Registration success!", Toast.LENGTH_LONG)
                        .show()
                        name_em.setText("")
                    sku.setText("")
                    expiry_date.setText("")
                    alert_quantity.setText("")
                    quanity.setText("")
                    selling_price.setText("")
                    cost_price.setText("")
                    barcode.setText("")
                    category.setText("")

                } else{
                    System.out.println("Jupiter: "+ response.body()!!.string())
                    Toast.makeText(context, "registration failed!", Toast.LENGTH_LONG).show()
                } //end of else
            }   //end of on response method
        })
    }   //end of register method


}