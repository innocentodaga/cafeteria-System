plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.cafe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cafe"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //circular image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Lottie Animation
    implementation("com.airbnb.android:lottie:5.2.0")

    //=================database==========================
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))

    //for volley
    implementation("com.android.volley:volley:1.2.1")

    //========================database end============
    //animated bottom bar
    implementation("nl.joery.animatedbottombar:library:1.1.0")


    //smooth transitions
    implementation("com.daimajia.androidanimations:library:2.4@aar")

    //picasso for image loading from the database
    implementation ("com.squareup.picasso:picasso:2.8")

}