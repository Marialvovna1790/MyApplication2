package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

abstract class BaseListActivity : AppCompatActivity() {
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val delegate = createDataLoader()
        setContentView(R.layout.activity_list)
        findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = delegate.adapter
        }

        progress = findViewById(R.id.progress_circular)
        delegate.doLoad()
    }

    protected abstract fun createDataLoader(): DataLoader<*>

    abstract inner class DataLoader<T>(private val table: String) {

        open lateinit var adapter: BaseAdapter<*, T>

        fun doLoad() {
            val db = FirebaseDatabase.getInstance()
            db.reference.child(table).addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) = Unit

                override fun onDataChange(p0: DataSnapshot) {
                    Log.d("debug", "added $p0")
                    val type = object : GenericTypeIndicator<ArrayList<HashMap<String, *>>>() {}
                    val value = p0.getValue(type)
                    value?.mapTo(adapter.dataSet, ::mapper)
                    progress.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                }
            })
        }
        protected abstract fun mapper(map: HashMap<String, *>): T
    }

    abstract class BaseAdapter<T, V> : RecyclerView.Adapter<T>() where T : RecyclerView.ViewHolder, T : Bindable<V> {

        val dataSet = mutableListOf<V>()

        override fun getItemCount() = dataSet.size

        override fun onBindViewHolder(holder: T, position: Int) {
            holder.bind(dataSet[position])
        }
    }
}