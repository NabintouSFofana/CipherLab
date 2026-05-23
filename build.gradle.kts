plugins {
    application
}

application {
    mainClass.set("com.nabintou.caesarcipher.Main")
}

tasks.register<JavaExec>("runTests") {
    description = "Runs the hand-rolled assertion tests."
    group = "verification"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.nabintou.caesarcipher.Test")
}
