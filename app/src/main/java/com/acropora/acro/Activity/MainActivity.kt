package com.acropora.acro.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acropora.acro.R
import com.acropora.acro.databinding.ActivityMainBinding
import com.acropora.acro.Model.StandingModel
import com.acropora.acro.Adapter.StandingTableViewAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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
            }

        }

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
}