package com.pukimen.babygrowth.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pukimen.babygrowth.data.repository.AuthRepository
import com.pukimen.babygrowth.data.repository.NutritionRepository
import com.pukimen.babygrowth.data.repository.RecomendationRepository
import com.pukimen.babygrowth.data.repository.ScanRepository
import com.pukimen.babygrowth.di.Injection
import com.pukimen.babygrowth.ui.auth.AuthViewModel
import com.pukimen.babygrowth.ui.bottomNav.FoodViewModel
import com.pukimen.babygrowth.ui.bottomNav.RecomendationViewModel
class ViewModelFactory private constructor(private val loginRepository: AuthRepository, private val nutritionRepository: NutritionRepository, private val recomendationRepository: RecomendationRepository, private val scanRepository: ScanRepository, application: Application) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(loginRepository) as T
            }
            modelClass.isAssignableFrom(FoodViewModel::class.java) -> {
                FoodViewModel(nutritionRepository) as T
            }
            modelClass.isAssignableFrom(RecomendationViewModel::class.java) -> {
                RecomendationViewModel(recomendationRepository) as T
            }
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(scanRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context, application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                val authRepository = Injection.provideAuthRepository(context)
                val recomendationRepository = Injection.provideRecomndationRepository(context)
                val nutritionRepository = Injection.provideNutritionRepository(context, application)
                val scanRepository = Injection.provideScanRepository(context)
                instance ?: ViewModelFactory(authRepository, nutritionRepository, recomendationRepository, scanRepository, application)
            }.also { instance = it }
    }
}
