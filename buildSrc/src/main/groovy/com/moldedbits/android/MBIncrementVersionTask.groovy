package com.moldedbits.android

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Increments version name and version code on each release build
 * Naming convention looks like this
 * <Major>.<Minor>.<PatchNo>
 * Major and Minor are user defined and PatchNo represents version code
 */
class MBIncrementVersionTask extends DefaultTask {
    def versionFileName = "version.properties"

    class VersionNo {
        int major=1
        int minor=0
        int patch=1
    }

    @TaskAction
    def incrementVersionCode() {
        // check if version file exists

        def versionFile = new File(versionFileName)
        if(versionFile.canRead()) {
            println("Yay! we have a version file")
            VersionNo version = new JsonSlurper().parse(versionFile)

            version.patch += 1
            versionFile.write(JsonOutput.toJson(version))
        } else {
            initVersionFile()
        }
    }

    def File initVersionFile() {
        def versionFile = new File(versionFileName)
        // lets create a file
        println("Creating a new version file")
        // and put default values in it
        versionFile.write(JsonOutput.toJson(new VersionNo()))
        return versionFile
    }

    def String getCurrentVersionName() {
        VersionNo version = getVersionFromFile()
        // naming convention is
        // major(2char).minor(2char).patch(2char)
        return "" + version.major + "." + version.minor + "." + version.patch
    }

    def int getCurrentVersionCode() {
        VersionNo version = getVersionFromFile()
        return version.patch;
    }

    private def VersionNo getVersionFromFile() {
        def versionFile = new File(versionFileName)
        if(!versionFile.canRead()) {
            versionFile = initVersionFile()
        }

        VersionNo version = new JsonSlurper().parse(versionFile)
        return version
    }
}
