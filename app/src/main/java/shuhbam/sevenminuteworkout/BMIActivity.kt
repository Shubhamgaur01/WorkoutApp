package shuhbam.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import shuhbam.sevenminuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW="US_UNITS_VIEW"
    }
    private var currentvisibleview:String= METRIC_UNITS_VIEW

    private var binding:ActivityBmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.ToolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="BMI Calculator"
        }
        binding?.ToolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        makevisiblemetricunitsview()                    //1 default case
        binding?.rgunits?.setOnCheckedChangeListener{_,checkedId:Int->      //1
            if(checkedId==R.id.rbmetricunits){
                makevisiblemetricunitsview()
            }
            else{
                makevisibleUSmetricunitsview()
            }
        }
        binding?.btnCalculateunits?.setOnClickListener {
            calculateunits()
        }
    }

    private fun makevisiblemetricunitsview(){
        currentvisibleview= METRIC_UNITS_VIEW                   //1
        binding?.tilmetricunitweight?.visibility=View.VISIBLE
        binding?.tilmetricunitheight?.visibility=View.VISIBLE
        binding?.tilUSmetricunitweight?.visibility=View.INVISIBLE
        binding?.tilMetricUSunitheightfeet?.visibility=View.INVISIBLE
        binding?.tilMetricUSunitheightinch?.visibility=View.INVISIBLE
        binding?.lldisplayBMIresult?.visibility=View.INVISIBLE
        binding?.etmetricunitweight?.text?.clear()
        binding?.etmetricunitheight?.text?.clear()
    }

    private fun makevisibleUSmetricunitsview(){
        currentvisibleview= US_UNITS_VIEW                       //1
        binding?.tilmetricunitweight?.visibility=View.INVISIBLE
        binding?.tilmetricunitheight?.visibility=View.INVISIBLE
        binding?.tilUSmetricunitweight?.visibility=View.VISIBLE
        binding?.tilMetricUSunitheightfeet?.visibility=View.VISIBLE
        binding?.tilMetricUSunitheightinch?.visibility=View.VISIBLE
        binding?.lldisplayBMIresult?.visibility=View.INVISIBLE
        binding?.etmetricUSunitweight?.text?.clear()
        binding?.etUSmetricunitheightfeet?.text?.clear()
        binding?.etUSmetricunitheightinch?.text?.clear()
    }

    private fun displayBMIresult(bmi:Float){
        val bmilabel:String                         //2
        val bmidescription:String
        if(bmi<=15f){
            bmilabel="Very severely underweight"
            bmidescription="Oops! You really need to care of yourself! Eat more"
        }
        else if(bmi>15f && bmi<=16f){
            bmilabel="Severely underweight"
            bmidescription="Oops! You really need to care of yourself! Eat more"
        }
        else if(bmi>16f && bmi<=18.5f){
            bmilabel="underweight"
            bmidescription="Oops! You really need to care of yourself! Eat more"
        }
        else if(bmi>18.5f && bmi<=25f){
            bmilabel="Normal"
            bmidescription="Congratulations! You are in a good shape"
        }
        else if(bmi>25f && bmi<=30f){
            bmilabel="Overweight"
            bmidescription="Oops! You really need to care of yourself! Workout more"
        }
        else if(bmi>30f && bmi<=35f){
            bmilabel="Obese class | (Moderately obese)"
            bmidescription="Oops! You really need to care of yourself! Workout more"
        }
        else if(bmi>35f && bmi<=40f){
            bmilabel="Obese class || (Severely obese)"
            bmidescription="Oops! You really need to care of yourself! Act now"
        }
        else{
            bmilabel="Obese class ||| (Very severely obese)"
            bmidescription="Oops! You really need to care of yourself! Act now"
        }
        val bmivalue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()   //converting float to string
        binding?.lldisplayBMIresult?.visibility= View.VISIBLE
        binding?.tvbmitype?.text=bmilabel
        binding?.tvbmivalue?.text=bmivalue
        binding?.tvbmidescription?.text=bmidescription
    }

    private fun validatemetricunits():Boolean{
        var isvalid=true                                        //2
        if(binding?.etmetricunitweight?.text.toString().isEmpty()){
            isvalid=false
        }
        else if(binding?.etmetricunitheight?.text.toString().isEmpty()){
            isvalid=false
        }
        return isvalid
    }

    private fun validateUSmetricunits():Boolean{
        var isValid=true                                        //2
        when{
            binding?.etmetricUSunitweight?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etUSmetricunitheightfeet?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etUSmetricunitheightinch?.text.toString().isEmpty()->{
                isValid=false
            }
        }
        return isValid
    }

    private fun calculateunits(){
        if(currentvisibleview== METRIC_UNITS_VIEW){
            if(validatemetricunits()){                              //2
                val heightvalue:Float=binding?.etmetricunitheight?.text.toString().toFloat()/100
                val weightvalue:Float=binding?.etmetricunitweight?.text.toString().toFloat()
                val bmi:Float=weightvalue/(heightvalue*heightvalue)
                displayBMIresult(bmi)

            }
            else{
                Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_LONG).show()
            }
        }
        else{
            if(validateUSmetricunits()){
                val USunitheightfeetvalue:String=binding?.etUSmetricunitheightfeet?.text.toString()
                val USunitheightINCHvalue:String=binding?.etUSmetricunitheightinch?.text.toString()
                val USunitweightvalue:Float=binding?.etmetricUSunitweight?.text.toString().toFloat()
                val heightvalue=USunitheightINCHvalue.toFloat()+USunitheightfeetvalue.toFloat()*12  //all height in inches
                val bmi=703*(USunitweightvalue/(heightvalue*heightvalue))
                displayBMIresult(bmi)
            }
            else{
                Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_LONG).show()
            }
        }
    }
}