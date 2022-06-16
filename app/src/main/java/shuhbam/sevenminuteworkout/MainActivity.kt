package shuhbam.sevenminuteworkout
//dimen is a file in which every main screen has a layout of padding 5dp
//dimen is created just like we wanted a same modify button everywhere that button not the normal button
//binding is basically used for replacing the findviewbyid
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import shuhbam.sevenminuteworkout.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null

    private var player:MediaPlayer?=null                //create a media player for playing the mp3 files
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //val flStartButton:FrameLayout=findViewById(R.id.flstart)
        try {
            val soundURI= Uri.parse("android.resource://shuhbam.sevenminuteworkout/"+R.raw.welcome)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false                 //creating media player for playing mp3 files
            player?.start()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
        binding?.flstart?.setOnClickListener{
            val transfer=Intent(this@MainActivity,ExericiseActivity::class.java)
            startActivity(transfer)
        }
        binding?.flBMI?.setOnClickListener{
            val intent=Intent(this@MainActivity,BMIActivity::class.java)
            startActivity(intent)
        }
        binding?.flhistory?.setOnClickListener {
            val transfer=Intent(this@MainActivity,HistoryActivity::class.java)
            startActivity(transfer)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(player!=null){
            player!!.stop()
        }
        binding=null                    //always do that if view binding creates to avoid memory leakage
    }
}
