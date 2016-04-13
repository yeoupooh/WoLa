package com.subakstudio.wola

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException

/**
 * Created by Thomas on 4/12/2016.
 */
open class WoLa : Application() {
    companion object {
        fun main(vararg args: String) {
            launch(*args)
        }
    }

    override fun start(stage: Stage) {
        var root: Parent?
        try {
            val fxml = javaClass.getResource("/com/subakstudio/wola/WoLa.fxml")
            println(fxml)
            root = FXMLLoader.load<Parent>(fxml)
            val scene = Scene(root, 600.0, 400.0)
            stage.setTitle("WoLa")
            stage.setScene(scene)
            stage.show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}