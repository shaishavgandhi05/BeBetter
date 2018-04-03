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

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import shaishav.com.bebetter.data.database.UsageDatabaseManager
import shaishav.com.bebetter.data.models.Usage
import shaishav.com.bebetter.data.preferences.PreferenceDataStore
import java.util.*
import javax.inject.Inject

/**
 * Created by shaishav.gandhi on 12/17/17.
 */
class UsageRepository @Inject constructor(val databaseManager: UsageDatabaseManager, val preferenceStore: PreferenceDataStore) {

  fun usages(): Observable<List<Usage>> {
    return databaseManager.usages()
  }

  fun currentSession(): Observable<Long> {
    return preferenceStore.currentSession(Date().time)
  }

  fun dailyUsage(): Observable<Long> {
    return preferenceStore.dailyUsageSoFar()
  }

  fun rawDailyUsage(): Long {
    return preferenceStore.rawDailyUsage()
  }

  fun averageDailyUsage(): Observable<Long> {
    return databaseManager.averageDailyUsage()
  }

  fun totalUsage(): Observable<Long> {
    return Observable.combineLatest(databaseManager.totalUsage(), preferenceStore.dailyUsageSoFar(), BiFunction { totalUsage, dailyUsageSoFar ->
      return@BiFunction totalUsage + dailyUsageSoFar
    })
  }

  fun insertSession(usage: Usage): Single<Long> {
    return Single.fromCallable {
      return@fromCallable databaseManager.insertSession(usage)
    }.subscribeOn(Schedulers.io())
  }

  fun storePhoneLockedTime(lockTime: Long) {
    preferenceStore.insertPhoneLockTime(lockTime)
  }

  fun storePhoneUnlockedTime(unlockTime: Long) {
    preferenceStore.insertPhoneUnlockTime(unlockTime)
  }

  fun lastUnlockedTime(): Long {
    return preferenceStore.lastUnlockTime()
  }

  fun storeCurrentDayUsage(sessionTime: Long) {
    preferenceStore.storeCurrentDayUsage(sessionTime)
  }


}