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

package shaishav.com.bebetter.models

import android.view.View
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import shaishav.com.bebetter.R
import shaishav.com.bebetter.data.models.UsageStat
import shaishav.com.bebetter.databinding.ListItemAppInfoBinding
import shaishav.com.bebetter.extensions.toFormattedTime

@EpoxyModelClass(layout = R.layout.list_item_app_info)
abstract class AppInfoModel(
        private val stat: UsageStat
): EpoxyModelWithHolder<AppInfoHolder>() {

  override fun bind(holder: AppInfoHolder) {
    super.bind(holder)

    holder.binding?.apply {
      appIcon.setImageDrawable(stat.icon)
      label.text = stat.appName
      usage.text = stat.usage.toFormattedTime()
    }
  }
}

class AppInfoHolder: EpoxyHolder() {

  var binding: ListItemAppInfoBinding? = null

  override fun bindView(itemView: View) {
    binding = DataBindingUtil.bind(itemView)
  }
}