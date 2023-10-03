// Copyright 2020. Explore in HMS. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.hms.commonmobileservices

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.hms.lib.commonmobileservices.R
import com.hms.lib.commonmobileservices.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val demos = mapOf(
        "Scan" to ScanActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.demosList.apply {
            adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1,
                demos.keys.toList()
            )
            onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    val name = demos.keys.toList()[position]
                    startActivity(Intent(this@MainActivity, demos[name]))
                }
        }
    }

}