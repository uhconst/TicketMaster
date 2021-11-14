package com.uhc.presentation.event

import android.os.Bundle
import com.uhc.presentation.R
import com.uhc.presentation.databinding.ActivityEventsBinding
import com.uhc.presentation.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class EventListActivity : BaseActivity<ActivityEventsBinding>() {

    val viewModel by viewModel<EventListViewModel>()

    override fun getLayoutRes(): Int = R.layout.activity_events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }
}