package com.saifer.storyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.remote.model.LoginModel
import com.saifer.storyapp.data.remote.model.RegisterModel
import com.saifer.storyapp.data.remote.responses.ListStoryItem
import com.saifer.storyapp.data.remote.responses.Story

object DataDummy {
    fun generateDummyDetailStory(): Story {
        return Story(
            "https://story-api.dicoding.dev/images/stories/photos-1671195724414_Wa0Bymh1.jpg",
            "2022-12-16T13:02:04.417Z",
            "sandi",
            "ini deskripsi",
            7.7840231,
            "story-dasdannjnvd",
            110.3005352
        )
    }

    fun generateDummyToken(): String{
        return "sandajskdsdadansjdkaaskdbsad"
    }

    fun generateDummyDataLogin() : LoginModel{
        return LoginModel(
            "sandifaisal@gmail.com",
            "123456"
        )
    }

    fun generateDummyDataRegister() : RegisterModel{
        return RegisterModel(
            "Sandi",
            "sandifaisal@gmail.com",
            "123456"
        )
    }

    fun generateDummyDataMap() : List<ListStoryItem>{
        val list = ArrayList<ListStoryItem>()
        for (i in 0..10){
            val story = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1671195724414_Wa0Bymh1.jpg",
                "2022-12-16T13:02:04.417Z",
                "sandi",
                "ini deskripsi",
                7.7840231,
                "story-$i",
                110.3005352
            )
            list.add(story)
        }
        return list
    }

    fun generateDummyStory() : List<ListStoryItem>{
        val list = ArrayList<ListStoryItem>()
        for (i in 0..10){
            val story = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1671195724414_Wa0Bymh1.jpg",
                "2022-12-16T13:02:04.417Z",
                "sandi",
                "ini deskripsi",
                7.7840231,
                "story-$i",
                110.3005352
            )
            list.add(story)
        }
        return list
    }
}