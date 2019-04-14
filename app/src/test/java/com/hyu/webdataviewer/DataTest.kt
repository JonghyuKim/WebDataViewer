package com.hyu.webdataviewer

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

class DataTest{

    @Test
    fun addition_isCorrect() {

        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun createDummy(){
        val factory = DummyPokeFactory()
        val dummyList = factory.makeDummyList()

        dummyList.forEach {
            System.out.println("data : $it")
        }
    }

    @Test
    fun dummyDataCreate(){
        val factory = DummyPokeFactory()

        System.out.println(Gson().toJson(factory.makeDummyList()))
    }
}