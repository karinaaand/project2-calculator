package com.example.project2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Deklarasi variabel untuk menyimpan input dan hasil
    private lateinit var AngkaMasuk: TextView
    private lateinit var AngkaKeluar: TextView

    private var angkaPertama = ""
    private var angkaKedua = ""
    private var operator = ""
    private var isOperatorPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menghubungkan variabel dengan elemen UI
        AngkaMasuk = findViewById(R.id.AngkaMasuk)
        AngkaKeluar = findViewById(R.id.AngkaKeluar)

        // Menyiapkan tombol dengan fungsionalitas masing-masing
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onNumberClicked((it as Button).text.toString()) }
        }

        // Mengatur operasi pada tombol operator
        findViewById<Button>(R.id.btnTambah).setOnClickListener { onOperatorClicked("+") }
        findViewById<Button>(R.id.btnKurang).setOnClickListener { onOperatorClicked("-") }
        findViewById<Button>(R.id.btnKali).setOnClickListener { onOperatorClicked("*") }
        findViewById<Button>(R.id.btnBagi).setOnClickListener { onOperatorClicked("/") }

        // Mengatur operasi pada tombol hasil dan tombol hapus
        findViewById<Button>(R.id.btnHasil).setOnClickListener { onEqualClicked() }
        findViewById<Button>(R.id.btnC).setOnClickListener { onClearClicked() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { onDeleteClicked() }
    }

    private fun onNumberClicked(number: String) {
        if (isOperatorPressed) {
            angkaKedua += number
        } else {
            angkaPertama += number
        }
        AngkaMasuk.text = angkaPertama + operator + angkaKedua
    }

    private fun onOperatorClicked(op: String) {
        if (angkaPertama.isNotEmpty()) {
            operator = op
            isOperatorPressed = true
            AngkaMasuk.text = angkaPertama + operator
        }
    }

    private fun onEqualClicked() {
        if (angkaKedua.isEmpty()) {
            Toast.makeText(this, "Masukkan angka kedua", Toast.LENGTH_SHORT).show()
            return
        }

        val hasil = when (operator) {
            "+" -> angkaPertama.toDouble() + angkaKedua.toDouble()
            "-" -> angkaPertama.toDouble() - angkaKedua.toDouble()
            "*" -> angkaPertama.toDouble() * angkaKedua.toDouble()
            "/" -> {
                if (angkaKedua == "0") {
                    Toast.makeText(this, "Tidak dapat membagi dengan nol", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    angkaPertama.toDouble() / angkaKedua.toDouble()
                }
            }
            else -> 0.0
        }

        // Menghindari hasil dengan .0 jika hasil adalah bilangan bulat
        val hasilText = if (hasil % 1 == 0.0) hasil.toInt().toString() else hasil.toString()

        // Menampilkan hasil pada TextView AngkaKeluar
        AngkaKeluar.text = hasilText  // Update this TextView for the result
        Toast.makeText(this, "Hasil: $hasilText", Toast.LENGTH_SHORT).show()

        // Jangan hapus input sebelumnya di AngkaMasuk
        resetCalculator(hasilText, shouldClearInput = false)
    }

    private fun onClearClicked() {
        resetCalculator()
    }

    private fun onDeleteClicked() {
        if (isOperatorPressed && angkaKedua.isNotEmpty()) {
            angkaKedua = angkaKedua.dropLast(1)
        } else if (operator.isNotEmpty()) {
            operator = ""
            isOperatorPressed = false
        } else if (angkaPertama.isNotEmpty()) {
            angkaPertama = angkaPertama.dropLast(1)
        }
        AngkaMasuk.text = angkaPertama + operator + angkaKedua
    }

    private fun resetCalculator(newAngkaPertama: String = "", shouldClearInput: Boolean = true) {
        angkaPertama = newAngkaPertama
        angkaKedua = ""
        operator = ""
        isOperatorPressed = false

        if (shouldClearInput) {
            AngkaMasuk.text = ""
        }

        if (newAngkaPertama.isEmpty()) {
            AngkaKeluar.text = ""
        }
    }
}
