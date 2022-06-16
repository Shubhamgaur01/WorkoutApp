package shuhbam.sevenminuteworkout

class ExerciseModel(
    private var id:Int,
    private var name:String,
    private var image:Int,
    private var iscompleted:Boolean,
    private var isselected:Boolean) {
    fun getId():Int{
        return id
    }
    fun setId(id: Int){
        this.id=id
    }
    fun getname():String{
        return name
    }
    fun setname(name: String){
        this.name=name
    }
    fun getimage():Int{
        return image
    }
    fun setimage(image: Int){
        this.image=image
    }
    fun getiscompleted():Boolean{
        return iscompleted
    }
    fun setiscompleted(iscompleted: Boolean){
        this.iscompleted=iscompleted
    }
    fun getisselected():Boolean{
        return isselected
    }
    fun setisselected(isselected: Boolean){
        this.isselected=isselected
    }
}