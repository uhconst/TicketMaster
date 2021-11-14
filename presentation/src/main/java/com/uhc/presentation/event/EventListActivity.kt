package com.uhc.presentation.event

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhc.domain.model.Event
import com.uhc.presentation.R
import com.uhc.presentation.databinding.ActivityEventsBinding
import com.uhc.presentation.event.favourite.FavouriteEventListActivity
import com.uhc.presentation.ui.base.BaseActivity
import com.uhc.presentation.ui.extensions.observeNotNull
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class EventListActivity : BaseActivity<ActivityEventsBinding>(),
    EventRecyclerAdapter.OnEventClickListener {

    val viewModel by viewModel<EventListViewModel>()

    private val eventRecyclerAdapter by inject<EventRecyclerAdapter>()

    override fun getLayoutRes(): Int = R.layout.activity_events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRecyclerView()

        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchEvents()
    }

    private fun initRecyclerView() {
        eventRecyclerAdapter.listener = this
        binding.rvEvents.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvEvents.adapter = eventRecyclerAdapter
    }

    private fun observeData() {
        viewModel.events.observeNotNull(this) {
            eventRecyclerAdapter.notifyChanged(it)
        }

        viewModel.error.observeNotNull(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.state.observeNotNull(this) {
            when (it) {
                EventListViewModel.State.GoToFavourite -> startActivity(
                    Intent(
                        this,
                        FavouriteEventListActivity::class.java
                    )
                )
            }
        }
    }

    override fun onClickFavouriteEvent(event: Event) {
        viewModel.onClickFavouriteEvent(event)
    }
}