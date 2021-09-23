object Deps {
    //Core deps
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val gradleSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.gradleSafeArgs}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradlePlugin}"

    //Project deps
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinCore = "androidx.core:core-ktx:${Versions.kotlinCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
    const val lifeCycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExtensions}"
    const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleViewModel}"
    const val lifeCycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleLiveData}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}"
    const val navigationUI= "androidx.navigation:navigation-ui-ktx:${Versions.navigationUI}"
    const val legacySupportV4= "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"
    const val hiltAndroid= "com.google.dagger:hilt-android:${Versions.hiltAndroid}"

    //Test deps
    const val junit= "junit:junit:${Versions.junit}"
    const val truth= "com.google.truth:truth:${Versions.truth}"
    const val androidXJUnit= "androidx.test.ext:junit:${Versions.androidXJUnit}"
    const val espresso= "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val coreTesting= "androidx.arch.core:core-testing:${Versions.coreTesting}"

    //Compilers
    const val roomCompiler= "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val hiltCompiler= "com.google.dagger:hilt-android-compiler:${Versions.hiltCompiler}"
}