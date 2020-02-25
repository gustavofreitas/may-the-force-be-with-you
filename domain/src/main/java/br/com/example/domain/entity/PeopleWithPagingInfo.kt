package br.com.example.domain.entity

data class PeopleWithPagingInfo(
    val total: Int,
    val peoples: List<People>
)