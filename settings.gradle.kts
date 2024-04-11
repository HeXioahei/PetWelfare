pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // 添加高德SDK的Maven仓库地址
        //maven ( "https://maven.amap.com/repo" )
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 添加高德SDK的Maven仓库地址
        //maven ( "https://maven.amap.com/repo" )
    }
}

rootProject.name = "PetWelfare"
include(":app")
 