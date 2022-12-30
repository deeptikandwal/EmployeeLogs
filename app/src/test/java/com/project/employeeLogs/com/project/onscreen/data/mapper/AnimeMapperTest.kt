package com.project.employeeLogs.com.project.onscreen.data.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.employeeLogs.data.db.entity.AnimeEntity
import com.project.employeeLogs.data.mapper.AnimeMapper
import com.project.employeeLogs.data.response.AnimeDto
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AnimeMapperTest {
    private lateinit var animeDto: List<AnimeDto>
    private lateinit var animeEntity: List<AnimeEntity>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mapper: AnimeMapper

    @Before
    fun setUp(){
        animeDto= listOf(
            AnimeDto(1,"Naruto","pain",""),
            AnimeDto(2,"Death Note","Light Yagami","")
        )
        animeEntity= listOf(
            AnimeEntity(1,"Bleach","",""),
            AnimeEntity(2,"Fruits Basket","kyo","")
        )
        mapper= AnimeMapper()
    }

       @Test
    fun mapToAnimeDomainTest(){
        Assert.assertEquals(mapper.mapToAnimeDomainFromDto(animeDto).get(0).anime,"Naruto-pain")
    }

    @Test
    fun mapToAnimeEntityTest() {
        Assert.assertEquals(mapper.mapToAnimeEntity(animeDto).get(0).character, "pain")
    }

    @Test
    fun mapToAnimeDomainFromEntityTest() {
        Assert.assertEquals(mapper.mapToAnimeDomainFromEntity(animeEntity).get(0).quote, "")
    }

}