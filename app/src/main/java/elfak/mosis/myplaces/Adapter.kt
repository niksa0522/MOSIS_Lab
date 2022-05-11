package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter() :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var position = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val text:TextView = view.findViewById(R.id.text_view)
        init{
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            p0: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {
            p0?.setHeaderTitle(text.text.toString())
            p0?.add(0,1,2, "View place")
            p0?.add(0,2,2, "Edit place")
        }
    }

    private fun MyPlacesData.size():Int{
        return MyPlacesData.myPlaces.size
    }

    override fun getItemCount(): Int = MyPlacesData.size()




    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.text_row_layout, parent, false)

        return ViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = MyPlacesData.getPlace(position)
        // Needed to call startActivity
        val context = holder.view.context

        holder.text.text = item.toString()

        holder.view.setOnClickListener{
            var bundle: Bundle = Bundle()
            bundle.putInt("position",position)
            val i:Intent = Intent(context,ViewMyPlaceActivity::class.java)
            i.putExtras(bundle)
            context.startActivity(i)
            //Toast.makeText(context,"${item.name} selected",Toast.LENGTH_SHORT).show()
        }
        holder.view.setOnLongClickListener{
            setPosition(position)
            return@setOnLongClickListener false
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
    }

}