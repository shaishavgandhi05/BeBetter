/*
 * Copyright (c) 2018 Shaishav Gandhi
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions
 *  and limitations under the License.
 */

package shaishav.com.bebetter.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import shaishav.com.bebetter.data.database.PointsDatabaseManager
import shaishav.com.bebetter.data.models.Level
import shaishav.com.bebetter.data.models.Point
import javax.inject.Inject

/**
 * Created by shaishav.gandhi on 12/25/17.
 */
class PointsRepository @Inject constructor(private val databaseManager: PointsDatabaseManager) {

  fun points(): Observable<List<Point>> {
    return databaseManager.points().subscribeOn(Schedulers.io())
  }

  fun totalPoints(): Observable<Long> {
    return databaseManager.totalPoints().subscribeOn(Schedulers.io())
  }

  fun save(point: Point): Completable {
    return databaseManager.savePoint(point).subscribeOn(Schedulers.io())
  }

  fun point(date: Long): Observable<Point> {
    return databaseManager.point(date)
  }

  fun level(): Observable<Level> {
    return databaseManager.totalPoints()
            .flatMap { points ->
              return@flatMap when(points) {
                in 0..999 -> Observable.just(Level.BEGINNER)
                in 1000..2499 -> Observable.just(Level.APPRENTICE)
                in 2499..4999 -> Observable.just(Level.INTERMEDIATE)
                in 5000..7499 -> Observable.just(Level.PRO)
                in 7500..9999 -> Observable.just(Level.EXPERT)
                in 10000..19999 -> Observable.just(Level.MASTER)
                in 20000..49999 -> Observable.just(Level.LEGEND)
                in 50000..9999999999 -> Observable.just(Level.ULTRA_LEGEND)
                else -> {
                  Observable.just(Level.BEGINNER)
                }
              }
            }
  }

  fun averagePoints(): Observable<Int> {
    return databaseManager.averagePoints()
  }

}