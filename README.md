# FlickIntroScreen
[![](https://jitpack.io/v/CASH-IT-Developer/FlickIntroScreen.svg)](https://jitpack.io/#CASH-IT-Developer/FlickIntroScreen)
<img src="https://raw.githubusercontent.com/CASH-IT-Developer/FlickIntroScreen/master/flickintro.jpeg" width="250" height="500">

   How to implementation
   
   
   add in build.gradle application
   
     allprojects {
          repositories {
             ...
             ...
             maven { url "https://jitpack.io" }

          }
      }
    
   add in build.gradle module
   
    dependencies {
        ...
        ...
        implementation 'com.github.CASH-IT-Developer:FlickIntroScreen:Tag'
    }
    
    
  Usage this library
  
     class MainActivity : FlickIntro() {
         override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)
             
             //REMOVE SET CONTENVIEW
     //        setContentView(R.layout.activity_main)
     
     
     //Adding Slide with custom layout
             addSlide(
                 FlickIntroCustomLayoutFragment.newInstance(R.layout.pos_intro_1)
             )
     
     //Adding Slide
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
     
     
     //Set Done Button
             setDoneText("Selesai")
             
     //Callback Button Finished Clicked
             addFinishedClickListener(object : OnFinishedClick {
                 override fun onFinishedClick() {
                     Toast.makeText(this@MainActivity, "Selesaiiii", Toast.LENGTH_LONG).show()
                 }
             })
     
     //Set Transition Sliding
             setTransformer(FlickIntroAnimationType.Flow)
         }
     }

