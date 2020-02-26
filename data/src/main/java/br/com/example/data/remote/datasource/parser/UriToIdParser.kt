package br.com.example.data.remote.datasource.parser

interface UriToIdParser {
    fun parse(url:String): Int
}