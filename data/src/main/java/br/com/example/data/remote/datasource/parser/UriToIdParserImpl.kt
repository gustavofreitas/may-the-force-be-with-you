package br.com.example.data.remote.datasource.parser

import android.net.Uri

class UriToIdParserImpl: UriToIdParser {
    override fun parse(url:String): Int {
        val uri: Uri = Uri.parse(url)
        return uri.pathSegments.last().toInt()
    }
}