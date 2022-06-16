package shuhbam.sevenminuteworkout

object Constants{
    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciselist=ArrayList<ExerciseModel>()
        val jumpingjacks=ExerciseModel(
            id = 1, name = "Jumping Jacks",R.drawable.jumping_jacks,false,false
        )
        exerciselist.add(jumpingjacks)

        val wallsit=ExerciseModel(
            id = 2, name = "Wall Sit",R.drawable.wall_sit_lower_body,false,false
        )
        exerciselist.add(wallsit)

        val pushups=ExerciseModel(
            id = 3, name = "Push Ups",R.drawable.pushup_upper_body,false,false
        )
        exerciselist.add(pushups)

        val abdominal_crunch=ExerciseModel(
            id = 4, name = "Abdominal Crunch",R.drawable.abdominal_crunch_core,false,false
        )
        exerciselist.add(abdominal_crunch)

        val stepup_onto_chair=ExerciseModel(
            id = 5, name = "StepUp Onto Chair",R.drawable.stepup_onto_chair,false,false
        )
        exerciselist.add(stepup_onto_chair)

        val squat=ExerciseModel(
            id = 6, name = "Squats",R.drawable.squat_lower_body,false,false
        )
        exerciselist.add(squat)

        val tricepdiponchair=ExerciseModel(
            id = 7, name = "Tricep Dip Onchair",R.drawable.triceps_dip,false,false
        )
        exerciselist.add(tricepdiponchair)

        val plank=ExerciseModel(
            id = 8, name = "Plank",R.drawable.plank_core,false,false
        )
        exerciselist.add(plank)

        val highkneesrunninginplace=ExerciseModel(
            id = 9, name = "High Knees Running In Place",R.drawable.high_knees_lower_body,false,false
        )
        exerciselist.add(highkneesrunninginplace)

        val lunges=ExerciseModel(
            id = 10, name = "Lunges",R.drawable.lunge_lower_body,false,false
        )
        exerciselist.add(lunges)

        val pushandrotation=ExerciseModel(
            id = 11, name = "Push And Rotation",R.drawable.pushup_and_rotation,false,false
        )
        exerciselist.add(pushandrotation)

        val sideplank=ExerciseModel(
            id = 12, name = "Side Plank",R.drawable.side_plank_core,false,false
        )
        exerciselist.add(sideplank)
        return exerciselist
    }
}