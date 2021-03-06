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

package shaishav.com.bebetter.utils

import com.shaishavgandhi.navigator.Extra
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by shaishav.gandhi on 2/25/18.
 */
class Constants {

  companion object {
    @JvmField val PACKAGE = "shaishav.com.bebetter"
    @JvmField val PREFERENCES = "com.bebetter.com"
    @Extra(key = "screen_name")
    val screenName: String? = null

    @JvmStatic fun getFormattedDate(date: Date): String {
      val dateFormat = SimpleDateFormat("MMM dd")
      return dateFormat.format(date)
    }
  }

}