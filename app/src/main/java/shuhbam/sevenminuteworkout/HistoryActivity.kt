package shuhbam.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import shuhbam.sevenminuteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect

class HistoryActivity : AppCompatActivity() {
    private var binding:ActivityHistoryBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.ToolbarHistoryActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="HISTORY"
        }
        binding?.ToolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao=(application as WorkOutApp).db.historyDao()
        getallDates(dao)
    }

    private fun getallDates(historyDao: HistoryDao){      //all date list is working as a it->list<alldates>
        lifecycleScope.launch{
            historyDao.fetchalldates().collect{allDateList->
                if(allDateList.isNotEmpty()){
                    binding?.tvHistory?.visibility= View.VISIBLE
                    binding?.rvHistory?.visibility=View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility=View.INVISIBLE
                    binding?.rvHistory?.layoutManager=LinearLayoutManager(this@HistoryActivity)

                    val dates=ArrayList<String>()
                    for(date in allDateList){
                        dates.add(date.date)
                    }
                    val historyadapter=HistoryAdapter(dates)
                    binding?.rvHistory?.adapter=historyadapter
                }
                else{
                    binding?.tvHistory?.visibility= View.INVISIBLE
                    binding?.rvHistory?.visibility=View.INVISIBLE
                    binding?.tvNoDataAvailable?.visibility=View.VISIBLE
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}