## NewsAppV2
This is a simple app to view list of news using:
* Kotlin
* MVP Architecture
* Repository Pattern
* Retrofit
* RxJava
* Glide
* Room Persistence **(in progress)**
* Dagger2
* **(NEW)** View Binding to replace Kotlin Android Extensions library

#### NewsApp functions:
* View list of news from **https://newsapi.org**
* When user click a news, the app will open news URL using Intent to WebViewActivity
* App theme (3 settings: system, light, and dark)

#### API used:
* Indonesian news: https://newsapi.org/v2/top-headlines?country=id&apiKey=API_KEY

(**NOTE**: use your own **API_KEY** from News Api, then define it inside your gradle.properties)

![gradle.properties](https://drive.google.com/uc?export=view&id=16b3xaHPw-VFOESRSlUNhZKG2u17tKlrI)