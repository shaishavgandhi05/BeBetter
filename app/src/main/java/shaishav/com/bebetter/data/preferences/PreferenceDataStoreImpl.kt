package shaishav.com.bebetter.data.preferences

import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import shaishav.com.bebetter.utils.Constants
import java.util.*

/**
 * Created by shaishav.gandhi on 12/24/17.
 */
class PreferenceDataStoreImpl(val preferences: RxSharedPreferences): PreferenceDataStore {

    override fun currentSession(currentTime: Long): Observable<Long> {
        return preferences
                .getLong(Constants.UNLOCKED)
                .asObservable()
                .map {
                    // TODO: Replace with all the minutes and hours from preferences
                    return@map (currentTime - it) / (1000 * 60)
                }
    }

    override fun dailyUsageSoFar(): Observable<Long> {
        // TODO: Add current session to it
        return Observable.combineLatest(currentSession(Date().time),
                dailyUsage(), BiFunction { currentSession, dailyUsage ->
            return@BiFunction currentSession + dailyUsage
        })

    }

    fun dailyUsage(): Observable<Long> {
        return preferences
                .getLong(Constants.SESSION)
                .asObservable()
                .map {
                    return@map it / (1000 * 60)
                }
    }
}