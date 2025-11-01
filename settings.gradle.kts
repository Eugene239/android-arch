rootProject.name = "android-arch"

include(":userState:domain")
include(":userState:data")
include(":onboarding:domain")
include(":onboarding:data")

project(":userState:domain").projectDir = file("userState/domain")
project(":userState:data").projectDir = file("userState/data")
project(":onboarding:domain").projectDir = file("onboarding/domain")
project(":onboarding:data").projectDir = file("onboarding/data")

project(":userState:domain").name = "userstate-domain"
project(":userState:data").name = "userstate-data"
project(":onboarding:domain").name = "onboarding-domain"
project(":onboarding:data").name = "onboarding-data"