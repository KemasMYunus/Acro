package com.acropora.acro.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.acropora.acro.Adapter.LeaderAdapter
import com.acropora.acro.Model.PlayerModel
import com.acropora.acro.R
import com.acropora.acro.databinding.ActivityVoteBinding
import com.bumptech.glide.Glide

class VoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityVoteBinding
    private val leaderAdapter by lazy { LeaderAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window : Window = this@VoteActivity.window
        window.statusBarColor = ContextCompat.getColor(this@VoteActivity, R.color.light_blue)

        binding.apply {
            scoreTop1Txt.text = loadData().get(0).score.toString()
            scoreTop2Txt.text = loadData().get(1).score.toString()
            scoreTop3Txt.text = loadData().get(2).score.toString()
            titleTop1Txt.text = loadData().get(0).name
            titleTop2Txt.text = loadData().get(1).name
            titleTop3Txt.text = loadData().get(2).name

            val drawableResourceId1: Int = binding.root.resources.getIdentifier(
                loadData().get(0).pic,"drawable", binding.root.context.packageName
            )
            Glide.with(root.context)
                .load(drawableResourceId1)
                .into(pic1)

            val drawableResourceId2: Int = binding.root.resources.getIdentifier(
                loadData().get(1).pic,"drawable", binding.root.context.packageName
            )
            Glide.with(root.context)
                .load(drawableResourceId2)
                .into(pic2)

            val drawableResourceId3: Int = binding.root.resources.getIdentifier(
                loadData().get(2).pic,"drawable", binding.root.context.packageName
            )
            Glide.with(root.context)
                .load(drawableResourceId3)
                .into(pic3)


            menu.setItemSelected(R.id.vote)
            menu.setOnItemSelectedListener {
                if(it == R.id.home){
                    startActivity(Intent(this@VoteActivity,MainActivity::class.java))
                    overridePendingTransition(0, 0) // Menghilangkan animasi
                    finish()
                }
                if(it == R.id.setting){
                    startActivity(Intent(this@VoteActivity,SettingActivity::class.java))
                    overridePendingTransition(0, 0) // Menghilangkan animasi
                    finish()
                }
            }
            var list:MutableList<PlayerModel> = loadData()
            list.removeAt(0)
            list.removeAt(0)
            list.removeAt(0)
            leaderAdapter.differ.submitList(list)
            leaderView.apply {
                layoutManager = LinearLayoutManager(this@VoteActivity)
                adapter = leaderAdapter
            }
        }
    }

    // contoh data
    private fun loadData(): MutableList<PlayerModel>{
        val player:MutableList<PlayerModel> = mutableListOf()
        player.add(PlayerModel(1,"Aoba Moca","picture1",9876))
        player.add(PlayerModel(2,"Hikawa Hina","picture2",8765))
        player.add(PlayerModel(3,"Hikawa Sayo","picture3",7654))
        player.add(PlayerModel(4,"Imai Lisa","picture4",6543))
        player.add(PlayerModel(5,"Kaname Raana","picture5",5432))
        player.add(PlayerModel(6,"Maruyama Aya","picture6",4321))
        player.add(PlayerModel(7,"Minato Yukina","picture7",3210))
        player.add(PlayerModel(8,"Shiina Taki","picture8",2109))
        player.add(PlayerModel(9,"Shirasagi Chisato","picture9",1098))
        player.add(PlayerModel(10,"Takamatsu Tomori","picture10",987))
        player.add(PlayerModel(11,"Yamato Maya","picture11",876))
        return player
    }
}