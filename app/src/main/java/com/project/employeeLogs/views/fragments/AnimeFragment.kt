package com.project.employeeLogs.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.employeeLogs.R
import com.project.employeeLogs.databinding.FragmentAnimeBinding
import com.project.employeeLogs.views.adapter.AnimeAdapter
import com.project.employeeLogs.views.viewState.AnimeState
import com.project.employeeLogs.views.viewmodel.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeFragment : Fragment() {
    lateinit var fragmentAnimeBinding: FragmentAnimeBinding
    val animeViewModel: AnimeViewModel by viewModels()
    val animeAdapter = AnimeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View=
        DataBindingUtil.inflate<FragmentAnimeBinding?>(layoutInflater,R.layout.fragment_anime,container,false).also {
         fragmentAnimeBinding =it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObserver()
    }

    private fun setObserver() {
        animeViewModel.title.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenStarted {
                animeViewModel.getAnimeList()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                animeViewModel.state.collect {
                    when (it) {
                        is AnimeState.IDLE -> {
                            fragmentAnimeBinding.recyclerView.visibility = View.GONE
                        }
                        is AnimeState.LOADING -> {
                            with(fragmentAnimeBinding){
                                progress.visibility=View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }

                        is AnimeState.SUCCESS -> {
                            fragmentAnimeBinding.run {
                                progress.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            }

                            with(animeAdapter) {
                                setDataList(it.animes)
                                notifyDataSetChanged()
                            }
                        }
                        is AnimeState.ERROR -> {
                            fragmentAnimeBinding.progress.visibility = View.GONE
                            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }


    private fun setViews() {
        with(fragmentAnimeBinding) {
            back.setOnClickListener {
                findNavController().navigate(R.id.homescreen)
            }
            idSV.setOnQueryTextListener(animeViewModel.onQueryTextListener)
            recyclerView.run {
                addItemDecoration(DividerItemDecoration(recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation))
                adapter=animeAdapter
            }
        }
    }
}