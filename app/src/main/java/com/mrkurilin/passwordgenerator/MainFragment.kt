package com.mrkurilin.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    private val charArrayWithSymbols: CharArray by lazy {
        initCharArray(withSymbols = true)
    }

    private val charArrayWithOutSymbols by lazy {
        initCharArray(withSymbols = false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()

        val view = requireView()

        val textView = view.findViewById<TextView>(R.id.textview_generated_password)

        val checkBox = view.findViewById<CheckBox>(R.id.checkbox_symbols)

        view.findViewById<ImageButton>(R.id.image_button_copy)?.setOnClickListener {
            val context = it.context
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", textView.text)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(requireContext(), "Password Copied", Toast.LENGTH_LONG).show()
        }

        view.findViewById<Button>(R.id.button_generate)?.setOnClickListener {
            textView.text = generatePassword(checkBox.isChecked)
        }
    }

    private fun generatePassword(checked: Boolean): String {
        val charArray = charArrayInstance(checked)
        return generateSequence { charArray.random() }.take(10).toList().joinToString("")
    }

    private fun charArrayInstance(checked: Boolean) =
        if (checked) {
            charArrayWithSymbols
        } else {
            charArrayWithOutSymbols
        }

    private fun initCharArray(withSymbols: Boolean): CharArray {
        val charList = mutableListOf<Char>()

        if (withSymbols) {
            charList.addAll((48..122).toMutableList().map { it.toChar() })
        } else {
            charList.addAll((48..57).toMutableList().map { it.toChar() })
            charList.addAll((65..90).toMutableList().map { it.toChar() })
            charList.addAll((97..122).toMutableList().map { it.toChar() })
        }

        return charList.toCharArray()
    }
}