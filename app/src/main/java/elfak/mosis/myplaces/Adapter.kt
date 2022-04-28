package elfak.mosis.myplaces

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val places: List<String>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val text:TextView = view.findViewById(R.id.text_view)
    }

    override fun getItemCount(): Int = places.size

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

        val item = places[position]
        // Needed to call startActivity
        val context = holder.view.context

        holder.text.text = item

    }

}