package com.m4ykey.data.domain.repository

import androidx.paging.Pager
import com.m4ykey.data.local.ArticleEntity

interface NewsRepository {

    fun getNewsPager(): Pager<Int, ArticleEntity>
}