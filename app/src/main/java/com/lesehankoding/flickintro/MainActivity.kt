package com.lesehankoding.flickintro

import android.os.Bundle
import android.widget.Toast
import com.lesehankoding.library.FlickIntro
import com.lesehankoding.library.FlickIntroCustomLayoutFragment
import com.lesehankoding.library.FlickIntroFragment
import com.lesehankoding.library.FlickIntroAnimationType

class MainActivity : FlickIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)


        addSlide(
            FlickIntroCustomLayoutFragment.newInstance(R.layout.pos_intro_1)
        )

        addSlide(FlickIntroFragment.newInstance(
            title = "Test2",
            description = "Desctiption"
        ))

        addSlide(FlickIntroFragment.newInstance(
            title = "Test3",
            description = "Desctiption"
        ))

        addSlide(FlickIntroFragment.newInstance(
            title = "Test4",
            description = "Desctiption"
        ))


        setDoneText("Selesai")
        showStatusBar(false)
        addFinishedClickListener(object : OnFinishedClick {
            override fun onFinishedClick() {
                Toast.makeText(this@MainActivity, "Selesaiiii", Toast.LENGTH_LONG).show()
            }
        })


        setTransformer(FlickIntroAnimationType.Flow)
    }
}