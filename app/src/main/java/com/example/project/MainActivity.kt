package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.project.Model22.CityDetailsResponse
import com.example.project.Models.City
import com.example.project.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var adapter: RecyClerviewAdapter? = null
    var list: kotlin.collections.ArrayList<CityDetailsResponse> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener(View.OnClickListener {
            val citiName = binding.searchView.text.toString().trim()
            if (!citiName.isEmpty()) {
                getCityDetails(citiName)
                binding.searchView.setText("")
            }
        })


        /*  editText.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(s: Editable?) {
              }

              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              }

              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                  contactFilter(s.toString())
              }
          })*/

//        getMarletData()

    }


//    private fun getMarletData() {
////        val position = requireArguments().getInt("position")
//        lifecycleScope.launch(Dispatchers.IO) {
//            val res =
//                RetrofitInstace().getRetrofit().create(ApiInterface::class.java).getMarketData()
//
//            if (res.body() != null) {
////                Log.e("aassa@32233", "getMarletData:    ${res.body()!!.cities.size}", )
//
//
//                val arrays = res.body()!!.cities
//                list = arrays
//
//
//                lifecycleScope.launch(Dispatchers.Main) {
//                    adapter = RecyClerviewAdapter(arrays,object :
//                        RecyClerviewAdapter.ClickListener{
//                        override fun click(name: String) {
//                            startActivity(Intent(this@MainActivity,WeatherDetailsActivity::class.java).putExtra("city",name))
//                        }
//
//                    })
//                    binding.homerecyclerview.adapter = adapter
//                }
//
//
//
//
//                }
//            }
//        }


    fun getCityDetails(city: String) {
        binding.progrebar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val url =
                "v1/forecast.json?key=5121c3e648f046d09df45242231002&q=$city&days=1&aqi=no&alerts=no"
            val res =
                RetrofitInstace().getRetrofit1().create(ApiInterface::class.java).getCityData(url)
            Log.d("aassa@32233", "Details Name:    ${res.code()}")
            if (res.body() != null) {
                if (res.code() == 200) {
                    val arrays = res.body()!!
                    list.add(arrays)
//                    Log.d("aassa@32233", "Details Name:    ${res.code()}", )
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.progrebar.visibility = View.GONE
                        binding.homerecyclerview.visibility = View.VISIBLE
                        adapter = RecyClerviewAdapter(list, object :
                            RecyClerviewAdapter.ClickListener {
                            override fun click(name: String) {
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        WeatherDetailsActivity::class.java
                                    ).putExtra("city", name)
                                )
                            }

                        })
                        binding.homerecyclerview.adapter = adapter
                    }
                } else {
                    lifecycleScope.launch(Dispatchers.Main) {

                        if (adapter != null) {
                            adapter!!.notifyDataSetChanged()
                        }
                        binding.progrebar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "DATA NOT FOUND", Toast.LENGTH_LONG)
                            .show()
                        binding.homerecyclerview.visibility = View.GONE
                    }

                }
            } else {

                lifecycleScope.launch(Dispatchers.Main) {
                    if (adapter != null) {
                        adapter!!.notifyDataSetChanged()
                    }

                    binding.progrebar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "DATA NOT FOUND", Toast.LENGTH_LONG).show()
                    binding.homerecyclerview.visibility = View.GONE
                }
            }
        }
    }

    class RecyClerviewAdapter(
        var list: kotlin.collections.ArrayList<CityDetailsResponse>,
        val listeer: ClickListener
    ) : RecyclerView.Adapter<RecyClerviewAdapter.MyDataHolder>() {

        class MyDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val citiname = itemView.findViewById<TextView>(R.id.citiName)
            val temprecher = itemView.findViewById<TextView>(R.id.temprechar)
            val weather = itemView.findViewById<TextView>(R.id.weather)
            val maxmin = itemView.findViewById<TextView>(R.id.maxmin)
        }


        /* fun filterDataa( cilty: kotlin.collections.ArrayList<City>){
             arrays = cilty
             notifyDataSetChanged()
         }*/

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDataHolder {
            return MyDataHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.home_items, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyDataHolder, position: Int) {
            holder.citiname.text = list[position].location.name
            holder.temprecher.text = "${list[position].current.temp_c.toString()}℃"
            for (i in 0..list[position].forecast.forecastday.size) {
                holder.weather.text =
                    "${list[position].forecast.forecastday[0].day.condition.text}"
                holder.maxmin.text =
                    "H: ${list[position].forecast.forecastday[0].day.maxtemp_c.toString()}\u2103 , L: ${list[position].forecast.forecastday[0].day.mintemp_c.toString()}℃"
            }

            holder.itemView.setOnClickListener {
                listeer.click(list[position].location.name)
            }
        }


        interface ClickListener {
            fun click(name: String)
        }
    }


    /* fun contactFilter(s: String) {
         val filtercitylist = ArrayList<City>()
         for (i in list.indices) {
             if (list[i].City.lowercase().contains(s.lowercase(Locale.getDefault()))
             ) {
                 filtercitylist.add(list[i])
             }
         }
         adapter!!.filterDataa(filtercitylist)
     }*/

}