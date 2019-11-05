package com.example.testandroidbinar.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroidbinar.R
import com.example.testandroidbinar.databinding.ActivityMainBinding
import com.example.testandroidbinar.model.Item
import com.example.testandroidbinar.ui.adapter.ItemListBindingAdapter
import com.example.testandroidbinar.viewmodel.ItemViewModel

class MainActivity : AppCompatActivity() {
    private val addItemRequestCode = 1
    private lateinit var itemViewModel: ItemViewModel

    private lateinit var mainBinding: ActivityMainBinding

    private var rvAdapter = ItemListBindingAdapter()

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             val swipedPosition = viewHolder.adapterPosition
            val item:Item = rvAdapter.getItemById(swipedPosition)
            showDialogDeleteItem(item)

        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar( mainBinding.toolbar)

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        mainBinding.lifecycleOwner = this

        mainBinding.viewmodel = itemViewModel
        mainBinding.recyclerview.adapter = rvAdapter
        itemTouchHelper.attachToRecyclerView(mainBinding.recyclerview)
    }

    fun addItem(view: View) {
        val intent = Intent(this@MainActivity, AddActivity::class.java)
        startActivityForResult(intent, addItemRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == addItemRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val item = data.getParcelableExtra<Item>(AddActivity.EXTRA_REPLY)
                itemViewModel.insert(item)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
                R.id.delete_all_menu -> showDialogDeleteAllItems()
        }

        return true
    }


    private fun showDialogDeleteAllItems(){
        val alertDialog = AlertDialog.Builder(this)

        with(alertDialog)
        {
            setTitle("Delete All Items Warning")
            setMessage("Are you sure want to delete all items?")
            setPositiveButton("Yes") { _:DialogInterface, _:Int ->
                itemViewModel.deleteAll()
            }
            setNegativeButton("No") { dialog:DialogInterface, _:Int ->
                dialog.dismiss()
            }
            show()
        }

    }

    private fun showDialogDeleteItem(item:Item){
        val alertDialog = AlertDialog.Builder(this)

        with(alertDialog)
        {
            setTitle("Delete Item Warning")
            setMessage("Are you sure want to delete this item?")
            setPositiveButton("Yes") { _:DialogInterface, _:Int ->
                itemViewModel.deleteItem(item)
            }
            setNegativeButton("No") { dialog:DialogInterface, _:Int ->
                mainBinding.recyclerview.adapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
            show()
        }

    }
}
