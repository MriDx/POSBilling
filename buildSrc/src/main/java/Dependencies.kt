object LibVersions {

    const val androidxCoreKTX = "1.7.0"
    const val appcompat = "1.5.1"
    const val androidMaterial = "1.9.0"
    const val constraintLayout = "2.1.4"
    const val splashScreenCore = "1.0.1"
    const val swipeRefreshLayout = "1.1.0"


    const val playServiceLocation = "21.0.1"
    const val playServiceMap = "18.1.0"

    const val playCoreKTX = "1.8.1"
    const val collectionKTX = "1.2.0"
    const val fragmentKTX = "1.5.4"
    const val lifecycleKTX = "2.6.0-alpha02"
    const val navigationKTX = "2.5.3"
    const val pagingKTX = "3.1.1"
    const val workRuntime = "2.8.1"


    const val firebaseBOM = "31.1.0"

    const val glide = "4.13.0"

    const val hilt = "2.44"
    const val hiltWorker = "1.0.0"
    const val hiltAndroidx = "1.0.0"

    const val retrofit = "2.9.0"
    const val okHttp = "5.0.0-alpha.2"
    const val gson = "2.9.0"

    const val room = "2.5.2"

    const val premadeRecyclerView = "1.3"
    const val morseUI = "1.1"
    const val waterMarkDialog = "1.7"

    const val imageCropper = "4.2.1"

    const val izzyParser = "1.0.0"

    const val lottie = "5.2.0"

    const val loupe = "1.2.2"
    const val loupeExtension = "1.0.1"


}

object Dep {

    const val androidxCore = "androidx.core:core-ktx:${LibVersions.androidxCoreKTX}"
    const val appCompat = "androidx.appcompat:appcompat:${LibVersions.appcompat}"
    const val androidMaterial =
        "com.google.android.material:material:${LibVersions.androidMaterial}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${LibVersions.constraintLayout}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${LibVersions.swipeRefreshLayout}"

    const val splashScreenCore = "androidx.core:core-splashscreen:${LibVersions.splashScreenCore}"

}

object Retrofit {

    const val retrofit = "com.squareup.retrofit2:retrofit:${LibVersions.retrofit}"
    const val gsonConvertor = "com.squareup.retrofit2:converter-gson:${LibVersions.gson}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${LibVersions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${LibVersions.okHttp}"

}

object KTX {

    const val collectionKTX = "androidx.collection:collection-ktx:${LibVersions.collectionKTX}"
    const val fragmentKTX = "androidx.fragment:fragment-ktx:${LibVersions.fragmentKTX}"
    const val lifecycleRuntimeKTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${LibVersions.lifecycleKTX}"
    const val lifecycleLiveDataKTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${LibVersions.lifecycleKTX}"
    const val lifecycleViewModelKTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibVersions.lifecycleKTX}"
    const val playCoreKTX = "com.google.android.play:core-ktx:${LibVersions.playCoreKTX}"
    const val pagingRuntimeKTX = "androidx.paging:paging-runtime-ktx:${LibVersions.pagingKTX}"

    const val workRuntimeKTX = "androidx.work:work-runtime-ktx:${LibVersions.workRuntime}"

    const val navigationRuntimeKTX =
        "androidx.navigation:navigation-runtime-ktx:${LibVersions.navigationKTX}"
    const val navigationFragmentKTX =
        "androidx.navigation:navigation-fragment-ktx:${LibVersions.navigationKTX}"
    const val navigationUIKTX = "androidx.navigation:navigation-ui-ktx:${LibVersions.navigationKTX}"

}

object PlayService {

    const val playServiceMap =
        "com.google.android.gms:play-services-maps:${LibVersions.playServiceMap}"
    const val playServiceLocation =
        "com.google.android.gms:play-services-location:${LibVersions.playServiceLocation}"

}

object DaggerHilt {

    const val hilt = "com.google.dagger:hilt-android:${LibVersions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${LibVersions.hilt}"
    const val hiltWorkManager = "androidx.hilt:hilt-work:${LibVersions.hiltWorker}"
    const val hiltAndroidxCompiler = "androidx.hilt:hilt-compiler:${LibVersions.hiltAndroidx}"

}

object Firebase {

    const val firebaseBOM = "com.google.firebase:firebase-bom:${LibVersions.firebaseBOM}"
    const val firebaseAnalyticsKTX = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlyticsKTX = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseMessagingKTX = "com.google.firebase:firebase-messaging-ktx"
    const val firebasePerformanceKTX = "com.google.firebase:firebase-perf-ktx"
    const val firebaseInAppMessagingTX = "com.google.firebase:firebase-inappmessaging-display-ktx"

}

object Glide {

    const val glide = "com.github.bumptech.glide:glide:${LibVersions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${LibVersions.glide}"

}

object Room {

    const val roomRuntime = "androidx.room:room-runtime:${LibVersions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${LibVersions.room}"
    const val roomKTX = "androidx.room:room-ktx:${LibVersions.room}"

}

object Misc {

    const val premadeRecyclerView =
        "io.github.mridx:PremadeRecyclerView:${LibVersions.premadeRecyclerView}"
    const val morseUI = "io.github.mridx:Morse-UI:${LibVersions.morseUI}"
    const val waterMarkDialog = "io.github.mridx:WaterMarkDialog:${LibVersions.waterMarkDialog}"

    const val imageCropper = "com.github.CanHub:Android-Image-Cropper:${LibVersions.imageCropper}"

    const val izzyRetrofitConverter =
        "com.undabot.izzy-json-api-android:retrofit-converter:${LibVersions.izzyParser}"
    const val izzyGsonAdapter =
        "com.undabot.izzy-json-api-android:gson-adapter:${LibVersions.izzyParser}"

    const val lottie = "com.airbnb.android:lottie:${LibVersions.lottie}"

    const val loupe = "io.github.igreenwood.loupe:loupe:${LibVersions.loupe}"
    const val loupeExtensions =
        "io.github.igreenwood.loupe:extensions:${LibVersions.loupeExtension}"

}