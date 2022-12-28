package com.project.employeeLogs.com.project.onscreen.data.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.employeeLogs.data.db.entity.AnimeEntity
import com.project.employeeLogs.data.db.entity.EmployeeEntity
import com.project.employeeLogs.data.mapper.EmployeeMapper
import com.project.employeeLogs.data.response.AnimeDto
import com.project.employeeLogs.data.response.EmployeeListDto
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MapperTest {

    private  lateinit var employeeDto: List<EmployeeListDto>
    private lateinit var employeeEntity: List<EmployeeEntity>
    private lateinit var animeDto: List<AnimeDto>
    private lateinit var animeEntity: List<AnimeEntity>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mapper: EmployeeMapper

    @Before
    fun setUp(){
        employeeDto= listOf(EmployeeListDto(1,"James","james@gmail.com",""),
            EmployeeListDto(2,"Purna","","")
        )
        employeeEntity= listOf(EmployeeEntity(1,"James","james@gmail.com",""),
            EmployeeEntity(2,"Purna","","")
        )
        animeDto= listOf(AnimeDto(1,"Naruto","pain",""),
            AnimeDto(2,"Death Note","Light Yagami","")
        )
        animeEntity= listOf(AnimeEntity(1,"Bleach","",""),
            AnimeEntity(2,"Fruits Basket","kyo","")
        )
        mapper= EmployeeMapper()
    }

    @Test
    fun mapToEmployeeDomainTest(){
        Assert.assertEquals(mapper.mapToEmployeeDomainFromDto(employeeDto).get(1).name,"Purna")
    }

    @Test
    fun mapToEmployeeDomainFromEntityTest(){
        Assert.assertEquals(mapper.mapToEmployeeDomainFromEntity(employeeEntity).get(0).name,"James")
    }

   @Test
    fun mapToEmployeeEntityTest(){
        Assert.assertEquals(mapper.mapToEmployeeEntity(employeeDto).get(0).name,"James")
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