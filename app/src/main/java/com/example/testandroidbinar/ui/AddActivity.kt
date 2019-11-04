package com.example.testandroidbinar.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroidbinar.R
import com.example.testandroidbinar.model.Item
import com.google.android.material.textfield.TextInputEditText


class AddActivity : AppCompatActivity() {

    private lateinit var inputNamaBarang: TextInputEditText
    private lateinit var inputTanggal: EditText
    private lateinit var inputJumlahBarang: TextInputEditText
    private lateinit var inputPemasok:TextInputEditText
    private lateinit var datePicker: DatePicker


    companion object {
        const val EXTRA_REPLY = "com.example.testandroidbinar.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        inputNamaBarang = findViewById(R.id.etNamaBarang)
        inputTanggal = findViewById(R.id.etTanggal)
        inputJumlahBarang = findViewById(R.id.etJumlahBarang)
        inputPemasok = findViewById(R.id.etPemasok)
        datePicker = findViewById(R.id.dp_tanggal)

        val button = findViewById<Button>(R.id.button_save)

        button.setOnClickListener {
            val replyIntent = Intent()
            if (checkInput()){
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    setEditTextTanggalValue()
                }
                val namaBarang = inputNamaBarang.text.toString()
                val tanggal = inputTanggal.text.toString()
                val jumlahBarang =  inputJumlahBarang.text.toString()
                val pemasok = inputPemasok.text.toString()

                val item = Item(namaBarang,tanggal,jumlahBarang,pemasok)
                replyIntent.putExtra(EXTRA_REPLY, item)
                setResult(Activity.RESULT_OK, replyIntent)

            } else {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }

            finish()
        }

        toggleDatePicker()
        datePickerChangeListener()

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun toggleDatePicker(){
        inputTanggal.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= inputTanggal.right - inputTanggal.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        if (datePicker.visibility==View.GONE){
                            setEditTextTanggalValue()

                            datePicker.visibility = View.VISIBLE
                       } else {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                                setEditTextTanggalValue()
                            }
                            datePicker.visibility = View.GONE

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
            datePicker.setOnDateChangedListener{ view, year, month, dayOfMonth ->


                val realWordMonth = month + 1
                val tanggal = "$dayOfMonth/$realWordMonth/$year"
                inputTanggal.setText(tanggal)

            }
        }
   }

    private fun checkInput(): Boolean {

        if (inputNamaBarang.text.isNullOrEmpty()) {
            inputNamaBarang.error = "is required!"
            inputNamaBarang.requestFocus()
            return false
        }

        if (inputTanggal.text.isNullOrEmpty()) {
            inputTanggal.error = "is required!"
            inputTanggal
            return false
        }

        if (inputJumlahBarang.text.isNullOrEmpty()) {
            inputJumlahBarang.error = "is required!"
            inputJumlahBarang.requestFocus()
            return false
        }


        if (inputPemasok.text.isNullOrEmpty()) {
            inputPemasok.error = "is required!"
            inputPemasok.requestFocus()
            return false
        }
        return true
    }


    private fun setEditTextTanggalValue(){
        val dayOfMonth = datePicker.dayOfMonth
        val month = (datePicker.month + 1)
        val year = datePicker.year

//        var day = "0"
//
//        if (dayOfMonth < 10){
//            day = "0$dayOfMonth"
//        } else {
//            day = dayOfMonth.toString()
//        }

        val tanggal = "$dayOfMonth/$month/$year"
        inputTanggal.setText(tanggal)
    }
}
