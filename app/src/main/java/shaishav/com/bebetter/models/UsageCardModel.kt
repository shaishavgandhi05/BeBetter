package shaishav.com.bebetter.models

import android.support.v4.content.res.ResourcesCompat
import android.view.Gravity
import android.view.View
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.robinhood.ticker.TickerUtils
import shaishav.com.bebetter.R
import shaishav.com.bebetter.listener.SummaryListener
import shaishav.com.bebetter.viewholder.UsageViewHolder

/**
 * Created by shaishav.gandhi on 12/24/17.
 */
@EpoxyModelClass
abstract class UsageCardModel(val header: String, val usage: Long, val footer: String) : EpoxyModelWithHolder<UsageViewHolder>() {

  var listener: SummaryListener? = null

  override fun bind(holder: UsageViewHolder) {
    super.bind(holder)

    val binding = holder.binding
    binding?.let { binding ->
      with(binding) {
        title = header
        this.usage = this@UsageCardModel.usage.toString()
        this.footer = this@UsageCardModel.footer
      }
    }

    val exoFont = ResourcesCompat.getFont(binding?.root?.context!!, R.font.exo_2)

    holder.binding?.value?.apply {
      setCharacterList(TickerUtils.getDefaultNumberList())
      animationDuration = 800
      text = usage.toString()
      typeface = exoFont
    }

    if (listener != null) {
      listener?.let { callback ->
        binding?.icon?.visibility = View.VISIBLE
        binding?.icon?.setOnClickListener {
          callback.onEditGoal()
        }
      }
    } else {
      binding?.icon?.visibility = View.GONE
    }

    binding?.executePendingBindings()
  }

  fun addListener(listener: SummaryListener): UsageCardModel {
    this.listener = listener
    return this
  }

  override fun getDefaultLayout(): Int {
    return R.layout.list_item_usage_card
  }
}