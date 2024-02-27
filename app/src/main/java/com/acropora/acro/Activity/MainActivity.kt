package com.acropora.acro.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acropora.acro.R
import com.acropora.acro.databinding.ActivityMainBinding
import com.acropora.acro.Model.StandingModel
import com.acropora.acro.Adapter.StandingTableViewAdapter
import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.acropora.acro.Adapter.ImageAdapter
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var  viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window : Window = this@MainActivity.window
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.light_blue)

        binding.apply{
            menu.setItemSelected(R.id.home)
            menu.setOnItemSelectedListener {
                if(it == R.id.vote){
                    startActivity(Intent(this@MainActivity,VoteActivity::class.java))
                    overridePendingTransition(0, 0) // Menghilangkan animasi
                    finish()
                }
                if(it == R.id.setting){
                    startActivity(Intent(this@MainActivity,SettingActivity::class.java))
                    overridePendingTransition(0, 0) // Menghilangkan animasi
                    finish()
                }
            }

            matchButton.setOnClickListener {
                // Menampilkan pesan "coming soon"
                Toast.makeText(applicationContext, "Coming Soon", Toast.LENGTH_SHORT).show()

                // Mengubah warna latar belakang tombol menjadi lebih gelap untuk sesaat
                matchButton.background = ContextCompat.getDrawable(applicationContext, R.drawable.btn_background_2)

                // Tambahkan animasi kembali ke warna semula setelah waktu tertentu (opsional)
                Handler().postDelayed({
                    // Mengembalikan warna asli tombol
                    matchButton.background = ContextCompat.getDrawable(applicationContext, R.drawable.btn_background_1)
                }, 50L)
            }

        }
        init()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })

        val recyclerViewMovieList = findViewById<RecyclerView>(R.id.recyclerViewMovieList)
        recyclerViewMovieList.layoutManager = LinearLayoutManager(this)
        recyclerViewMovieList.adapter = StandingTableViewAdapter(movieList)
    }


    // data sementara
    private val movieList = ArrayList<StandingModel>().apply {
        add(StandingModel(1, "Roosters", 3, 3, 0, 6))
        add(StandingModel(2, "Sunday Morning", 3, 3, 0, 6))
        add(StandingModel(3, "Rong Qing", 3, 2, 1, 5))
        add(StandingModel(4, "Spartans Banjarbaru", 3, 2, 1, 5))
        add(StandingModel(5, "Gazelle", 3, 1, 2, 4))
        add(StandingModel(6, "Titan Sharks", 3, 1, 2, 4))
        add(StandingModel(7, "Citra Satria", 3, 0, 3, 3))
        add(StandingModel(8, "Triple One", 3, 0, 3, 3))

    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2 = findViewById(R.id.viewPager2)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.team1)
        imageList.add(R.drawable.team2)
        imageList.add(R.drawable.team3)
        imageList.add(R.drawable.team4)
        imageList.add(R.drawable.team5)
        imageList.add(R.drawable.team6)
        imageList.add(R.drawable.team7)
        imageList.add(R.drawable.team8)


        adapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

}