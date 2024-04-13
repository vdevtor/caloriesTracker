package com.vitorthemyth.tracker_data.repository

import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import com.vitorthemyth.tracker_data.local.TrackerDao
import com.vitorthemyth.tracker_data.mapper.toTrackableFood
import com.vitorthemyth.tracker_data.mapper.toTrackedFood
import com.vitorthemyth.tracker_data.mapper.toTrackedFoodEntity
import com.vitorthemyth.tracker_data.remote.OpenFoodApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.time.LocalDate

class TrackerRepositoryImp(
   private val dao: TrackerDao, private val api: OpenFoodApi
) : TrackerRepository {

   override suspend fun searchFood(
      query: String, page: Int, pageSize: Int
   ): Result<List<TrackableFood>> {
      return try {
         val searchedDto = api.searchFood(
            query = query, page = page, pageSize = pageSize
         )

         Result.success(searchedDto.products.mapNotNull { it.toTrackableFood() })

      } catch (e: Exception) {
         e.printStackTrace()
         Result.failure(e)
      }
   }

   override suspend fun insertTrackedFood(food: TrackedFood) {
      dao.insertTrackedFood(food.toTrackedFoodEntity())
   }

   override suspend fun deleteTrackedFood(food: TrackedFood) {
      dao.deleteTrackedFood(food.toTrackedFoodEntity())
   }

   override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
      return dao.getFoodsForDate(
         day = localDate.dayOfMonth, month = localDate.monthValue, year = localDate.year
      ).map { entities ->
         entities.map { it.toTrackedFood() }
      }
   }
}