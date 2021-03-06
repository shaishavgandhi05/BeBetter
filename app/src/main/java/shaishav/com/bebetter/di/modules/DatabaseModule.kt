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

package shaishav.com.bebetter.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.squareup.sqlbrite2.BriteContentResolver
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import shaishav.com.bebetter.data.MySQLiteHelper
import shaishav.com.bebetter.data.database.*
import shaishav.com.bebetter.data.preferences.PreferenceDataStore
import shaishav.com.bebetter.data.preferences.PreferenceDataStoreImpl
import shaishav.com.bebetter.data.repository.GoalRepository
import shaishav.com.bebetter.data.repository.UsageRepository
import shaishav.com.bebetter.di.scopes.ApplicationScope
import shaishav.com.bebetter.utils.Constants

/**
 * Created by shaishav.gandhi on 12/19/17.
 */
@Module
class DatabaseModule {

    @Provides @ApplicationScope fun proviesSqlBrite(): SqlBrite {
        return SqlBrite.Builder().build()
    }

    @Provides @ApplicationScope fun providesBriteContentResolver(sqlBrite: SqlBrite, application: Application, scheduler: Scheduler): BriteContentResolver {
        return sqlBrite.wrapContentProvider(application.contentResolver, scheduler)
    }

    @Provides @ApplicationScope fun providesBriteDatabase(sqlBrite: SqlBrite, sqLiteOpenHelper: SQLiteOpenHelper, scheduler: Scheduler): BriteDatabase {
        return sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper, scheduler)
    }

    @Provides @ApplicationScope fun providesSqliteOpenHelper(context: Context): SQLiteOpenHelper {
        return MySQLiteHelper(context)
    }

    @Provides @ApplicationScope fun providesSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides @ApplicationScope fun providesRxPreferences(preferences: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(preferences)
    }

    @Provides @ApplicationScope fun providesPreferenceDataStore(preferences: RxSharedPreferences, editor: SharedPreferences.Editor): PreferenceDataStore {
        return PreferenceDataStoreImpl(preferences, editor)
    }

    @Provides @ApplicationScope fun providesEditor(preferences: SharedPreferences): SharedPreferences.Editor {
        return preferences.edit()
    }

    @Provides fun providesUsageDatabaseManager(database: UsageDatabaseManagerImpl): UsageDatabaseManager {
        return database
    }

    @Provides fun providesGoalDatabaseManager(goalDatabaseManager: GoalDatabaseManagerImpl): GoalDatabaseManager {
        return goalDatabaseManager
    }

    @Provides fun providesPointsDatabaseManager(pointsDatabaseManagerImpl: PointsDatabaseManagerImpl): PointsDatabaseManager {
        return pointsDatabaseManagerImpl
    }
}