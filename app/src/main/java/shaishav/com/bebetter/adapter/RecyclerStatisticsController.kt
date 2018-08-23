/*
 *  Copyright (c) 2018 Shaishav Gandhi
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
 *
 */

package shaishav.com.bebetter.adapter

import com.airbnb.epoxy.EpoxyController
import shaishav.com.bebetter.data.models.UsageStat
import shaishav.com.bebetter.models.appInfo

class RecyclerStatisticsController: EpoxyController() {
  var usageStats: List<UsageStat> = emptyList()
  set(value) {
    field = value
    requestModelBuild()
  }

  init {
    setFilterDuplicates(true)
  }

  override fun buildModels() {
    for (stat in usageStats) {
      appInfo(stat) {
        id(stat.packageName)
      }
    }
  }
}
