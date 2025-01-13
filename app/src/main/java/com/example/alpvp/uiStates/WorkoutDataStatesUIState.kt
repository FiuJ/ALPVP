package com.example.alpvp.uiStates

import com.example.alpvp.models.WorkoutModel

interface WorkoutDataStatesUIState {
    data class Success(val data: WorkoutModel): WorkoutDataStatesUIState
    object Start: WorkoutDataStatesUIState
    object Loading: WorkoutDataStatesUIState
    data class Failed(val errorMessage:String): WorkoutDataStatesUIState
}