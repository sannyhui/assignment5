package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.simplecalculator.ui.main.MainFragment

/**
 * Main process to call MainFragment class
 */

class MainActivity : FragmentActivity(), MainFragment.ToolbarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    //Implement abstract method of seekbar
    override fun onButtonClick(fontsize: Int, text: String) {
    }
}