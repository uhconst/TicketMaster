package com.uhc.presentation.event.favourite

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uhc.domain.model.Event
import com.uhc.presentation.R
import com.uhc.presentation.databinding.ActivityFavouriteEventsBinding
import com.uhc.presentation.event.EventRecyclerAdapter
import com.uhc.presentation.ui.base.BaseActivity
import com.uhc.presentation.ui.extensions.observeNotNull
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class FavouriteEventListActivity : BaseActivity<ActivityFavouriteEventsBinding>(),
    EventRecyclerAdapter.OnEventClickListener {

    private val viewModel by viewModel<FavouriteEventListViewModel>()

    private val eventRecyclerAdapter by inject<EventRecyclerAdapter>()

    override fun getLayoutRes(): Int = R.layout.activity_favourite_events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRecyclerView()

        observeData()

        viewModel.fetchFavouriteEvents()
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
        viewModel.favouriteEvents.observeNotNull(this) {
            eventRecyclerAdapter.notifyChanged(it)
        }

        viewModel.error.observeNotNull(this) {}
    }

    override fun onClickFavouriteEvent(event: Event) {
        viewModel.onClickFavouriteEvent(event)
    }
}