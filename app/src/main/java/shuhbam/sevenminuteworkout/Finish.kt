package shuhbam.sevenminuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import shuhbam.sevenminuteworkout.databinding.ActivityExericiseBinding
import shuhbam.sevenminuteworkout.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class Finish : AppCompatActivity() {
    private var binding:ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.Toolbarfinish)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.Toolbarfinish?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao=(application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
        binding?.btnFinish?.setOnClickListener{
            val transfer=Intent(this@Finish,MainActivity::class.java)
            startActivity(transfer)
        }
    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        val c=Calendar.getInstance()
        val datetime=c.time
        Log.e("Date:",""+datetime)
        val sdf=SimpleDateFormat("dd MM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(datetime)
        Log.e("Formatted date",""+date)
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
            Log.e("Date","added")
        }
    }
}