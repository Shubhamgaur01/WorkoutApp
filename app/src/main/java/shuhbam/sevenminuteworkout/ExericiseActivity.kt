package shuhbam.sevenminuteworkout

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import shuhbam.sevenminuteworkout.databinding.ActivityExericiseBinding
import shuhbam.sevenminuteworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExericiseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExericiseBinding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0

    private var ExerciseTimer:CountDownTimer?=null
    private var ExerciseProgress=0

    private var exerciselist:ArrayList<ExerciseModel>?=null
    private var currentexerciseposition=-1

    private var tts:TextToSpeech?=null

    private var exerciseAdapter:ExerciseStatusAdapter?=null

    private var restTimerDuration:Long=1
    private var ExerciseTimerDuration:Long=1

    override fun onCreate(savedInstanceState: Bundle?) {//invisible-This view is invisible,but it still takes up space for layout purposes.
        super.onCreate(savedInstanceState)        //gone-This view is invisible, and it doesn't take any space for layout purposes.
        binding=ActivityExericiseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.ToolbarExercise)       //this will show the tool bar of the app

        exerciselist=Constants.defaultExerciseList()
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)           //check is action bar exist then it will display the
        }                                                                                   //navigation backward sign
        binding?.ToolbarExercise?.setNavigationOnClickListener {
            customdialogforbackbutton()                                            //this willl active only when toolbar back is pressed and not wrking when mobile back button is pressed
        }                                                               //so for mobile back button pressed there is pre defined functio onbackpressed()
        //just like we have a back button in phone

        tts= TextToSpeech(this,this)

        setuprestview()
        setupExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {              //this onbackpressed is already predefined in the android studio
        customdialogforbackbutton()
    }

    private fun customdialogforbackbutton(){        //hamari dialog_custom xml file ek dailog box ki tarah pop up hogi jab ham tool bar ma back ko click karenga
        val customdialog=Dialog(this)
        val dialogbinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)//DialogCustomBackConfirmationBinding this is our xml file and connecting it to binding
        customdialog.setContentView(dialogbinding.root)
        customdialog.setCanceledOnTouchOutside(false)       //agar dialog box ka bahar click karenga toh kuch nhi nhi hoga
        dialogbinding.btnyes.setOnClickListener {
            this@ExericiseActivity.finish()
            customdialog.dismiss()
        }
        dialogbinding.btnno.setOnClickListener {
            customdialog.dismiss()
        }
        customdialog.show()
    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciselist!!)          //exerciseAdapter is val that cantains adapter join of ExerciseStatusAdapter
        binding?.rvExerciseStatus?.adapter=exerciseAdapter              //finally join exerciseAdapter to the id of recyclerview(id:rvExerciseStatus)
    }

    private fun setuprestview(){

        binding?.flrestview?.visibility= View.VISIBLE
        binding?.tvTitle?.visibility=View.VISIBLE
        binding?.tvexerciseName?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.ivimage?.visibility=View.INVISIBLE
        binding?.tvupcominglabel?.visibility=View.VISIBLE
        binding?.tvupcomingexercisename?.visibility=View.VISIBLE

        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        speakOut("REST FOR TEN SECONDS")
        binding?.tvupcomingexercisename?.text=exerciselist!![currentexerciseposition+1].getname()
        setRestProgressBar()
    }
    private fun setupexerciseview(){
        binding?.flrestview?.visibility= View.INVISIBLE           //invisible the view of flprogressbar in framelayout xml
        binding?.tvTitle?.visibility=View.INVISIBLE
        binding?.tvexerciseName?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE        //visible the view of flexercisebar in framelayout xml
        binding?.ivimage?.visibility=View.VISIBLE
        binding?.tvupcominglabel?.visibility=View.INVISIBLE
        binding?.tvupcomingexercisename?.visibility=View.INVISIBLE

        if(ExerciseTimer!=null){
            ExerciseTimer?.cancel()
            ExerciseProgress=0
        }
        speakOut("NEXT EXERCISE IS "+exerciselist!![currentexerciseposition].getname())
        binding?.ivimage?.setImageResource(exerciselist!![currentexerciseposition].getimage())
        binding?.tvexerciseName?.text=exerciselist!![currentexerciseposition].getname()
        setExerciseProgressBar()
    }


    private fun setRestProgressBar(){
        binding?.progressbar?.progress=restProgress
        restTimer=object:CountDownTimer(restTimerDuration*10000,1000){
            override fun onTick(millisUntilFinished: Long) {        //this is count down timeer for the rest time
                restProgress++
                speakOut((10-restProgress).toString())
                binding?.progressbar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentexerciseposition++
                exerciselist!![currentexerciseposition].setisselected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupexerciseview()
            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressbarExercise?.progress=ExerciseProgress
        ExerciseTimer=object:CountDownTimer(ExerciseTimerDuration*30000,1000){
            override fun onTick(millisUntilFinished: Long) {    //this is count down timer for the exercise time
                ExerciseProgress++
                binding?.progressbarExercise?.progress=30-ExerciseProgress
                binding?.tvTimerExercise?.text=(30-ExerciseProgress).toString()
            }

            override fun onFinish() {
                exerciselist!![currentexerciseposition].setisselected(false)
                exerciselist!![currentexerciseposition].setiscompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
                if(currentexerciseposition<exerciselist!!.size-1){
                    setuprestview()
                }
                else{
                    Toast.makeText(this@ExericiseActivity,"EXERCISE COMPLETED SUCCESSFULLY",Toast.LENGTH_LONG).show()
                    val transfer=Intent(this@ExericiseActivity,Finish::class.java)
                    startActivity(transfer)
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        if(ExerciseTimer!=null){
            ExerciseTimer?.cancel()
            ExerciseProgress=0
        }
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        binding=null
    }

    override fun onInit(status: Int) {          //function for tts=Texttospeech(this,this)
        if(status==TextToSpeech.SUCCESS){                       //TEXT TO SPEECH FUNCTION
            val result=tts?.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","THE LANGUAGE SPECIFIED IS NOT SUPPORTED")
            }
        }
        else{
            Log.e("TTS","INITIALIZATION FAILED")
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }
}