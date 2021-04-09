# FlickIntroScreen
[![](https://jitpack.io/v/CASH-IT-Developer/FlickShowCase.svg)](https://jitpack.io/#CASH-IT
-Developer/FlickIntroScreen)
![alt text](https://raw.githubusercontent.com/CASH-IT-Developer/FlickShowCase/main/flickintro.jpeg)

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
   
       // enabling data binding how to use this library
       
       dataBinding {
               enabled = true
           }
    
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

