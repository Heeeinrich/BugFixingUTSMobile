package com.example.pertemuan6

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan6.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){

            val monthList = resources.getStringArray(R.array.month)
            var selectedTime = "${timePicker.hour}:${timePicker.minute}"
            val tempCalendar: Calendar = Calendar.getInstance()
            tempCalendar.timeInMillis = System.currentTimeMillis()
            var selectedDate =
                "${tempCalendar.get(Calendar.DAY_OF_MONTH)} ${monthList[tempCalendar.get(Calendar.MONTH)]} ${tempCalendar.get(Calendar.YEAR)}"


            val stringKehadiran = resources.getStringArray(R.array.presensi)
            val adapterKehadiran = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, stringKehadiran)
            kehadiranSpinner.adapter = adapterKehadiran


            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedKehadiran = parent?.getItemAtPosition(position).toString()
                        if (selectedKehadiran == "Hadir Tepat Waktu") {
                            keteranganEdittext.visibility = View.GONE
                        } else {
                            keteranganEdittext.visibility = View.VISIBLE
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            }

            // Date Picker =======================================
            datepicker.init(
                tempCalendar.get(Calendar.YEAR),
                tempCalendar.get(Calendar.MONTH),
                tempCalendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth ${monthList[monthOfYear]} $year"
            }

            submitButton.setOnClickListener {
                Toast.makeText(this@MainActivity, "Presensi Berhasil $selectedDate jam $selectedTime", Toast.LENGTH_SHORT).show()
            }

        }
    }
}