package br.com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.People

interface PeopleRepository {
    fun getPeople(): LiveData<PagedList<People>>
}