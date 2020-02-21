package br.com.example.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import br.com.example.data.remote.model.PeoplePayload

interface PeopleRepository {
    fun getPeople(): LiveData<PagedList<PeoplePayload>>
}