package com.subakstudio.wola

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.MenuItem
import kotlin.system.exitProcess

/**
 * Created by yeoupooh on 4/12/16.
 */
class WoLaController {
    @FXML fun onAction(event: ActionEvent) {
        println("onAction: $event")
        if (event.source is MenuItem) {
            val id = (event.source as MenuItem).id
            println("id=$id")
            when (id ) {
                "menuItemQuit" -> exitProcess(0)
                "menuItemAbout" -> {
                    //show dialog}
                }
            }
        }
    }
}