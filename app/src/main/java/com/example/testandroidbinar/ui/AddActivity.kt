package com.example.testandroidbinar.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testandroidbinar.R
import com.example.testandroidbinar.databinding.ActivityAddBinding
import com.example.testandroidbinar.model.Item


class AddActivity : AppCompatActivity() {

    private lateinit var addBinding:ActivityAddBinding


    companion object {
        const val EXTRA_REPLY = "com.example.testandroidbinar.REPLY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        toggleDatePicker()
        datePickerChangeListener()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun toggleDatePicker(){
        addBinding.etTanggal.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= addBinding.etTanggal.right - addBinding.etTanggal.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        if (addBinding.datepicker.visibility==View.GONE){
                            setEditTextTanggalValue()

                            addBinding.datepicker.visibility = View.VISIBLE
                       } else {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                                setEditTextTanggalValue()
                            }
                            addBinding.datepicker.visibility = View.GONE

                        }

                        return true
                    }
                }
                return false
            }

        })
    }
    
    private fun datePickerChangeListener(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addBinding.datepicker.setOnDateChangedListener{ view, year, month, dayOfMonth ->
                val realWordMonth = month + 1
                val tanggal = "$dayOfMonth/$realWordMonth/$year"
                addBinding.etTanggal.setText(tanggal)

            }
        }
   }

    private fun checkInput(): Boolean {

        if (addBinding.etNamaBarang.text.isNullOrEmpty()) {
            addBinding.etNamaBarang.error = "is required!"
            addBinding.etNamaBarang.requestFocus()
            return false
        }

        if (addBinding.etTanggal.text.isNullOrEmpty()) {
            addBinding.etTanggal.error = "is required!"
            addBinding.etTanggal.requestFocus()
            return false
        }

        if (addBinding.etJumlahBarang.text.isNullOrEmpty()) {
            addBinding.etJumlahBarang.error = "is required!"
            addBinding.etJumlahBarang.requestFocus()
            return false
        }


        if (addBinding.etPemasok.text.isNullOrEmpty()) {
            addBinding.etPemasok.error = "is required!"
            addBinding.etPemasok.requestFocus()
            return false
        }
        return true
    }


    private fun setEditTextTanggalValue(){
        val dayOfMonth = addBinding.datepicker.dayOfMonth
        val month = (addBinding.datepicker.month + 1)
        val year = addBinding.datepicker.year

        val tanggal = "$dayOfMonth/$month/$year"
        addBinding.etTanggal.setText(tanggal)
    }

    fun saveToDB(view: View){
        val replyIntent = Intent()
        if (checkInput()){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                setEditTextTanggalValue()
            }
            val namaBarang = addBinding.etNamaBarang.text.toString()
            val tanggal = addBinding.etTanggal.text.toString()
            val jumlahBarang =  addBinding.etJumlahBarang.text.toString()
            val pemasok = addBinding.etPemasok.text.toString()

            val item = Item(namaBarang,tanggal,jumlahBarang,pemasok)
            replyIntent.putExtra(EXTRA_REPLY, item)
            setResult(Activity.RESULT_OK, replyIntent)

        } else {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        }

        finish()
    }
}
