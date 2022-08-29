package dev.dasischbims

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

// consts

data class PluginConfig(val texturePackURL: String, val texturePackHash: String)
private val DEFAULT_CONFIG = PluginConfig("", "")
lateinit var CONFIG: PluginConfig

internal fun loadPluginConfig(){
    if(!File(INSTANCE.dataFolder.path).exists()) File(INSTANCE.dataFolder.path).mkdirs()
    val configFile = File(INSTANCE.dataFolder.path + "/config.json")
    CONFIG = if(configFile.exists()){
        try {
            jacksonObjectMapper().readValue(configFile)
        } catch (ex: MismatchedInputException){
            jacksonObjectMapper().readTree(configFile).run {
                PluginConfig(
                    DEFAULT_CONFIG.texturePackURL,
                    DEFAULT_CONFIG.texturePackHash
                )
            }
        }
    } else {
        configFile.createNewFile()
        DEFAULT_CONFIG
    }
    jacksonObjectMapper().writeValue(configFile, CONFIG)
}