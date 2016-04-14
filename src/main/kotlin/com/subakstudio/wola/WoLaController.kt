package com.subakstudio.wola

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.subakstudio.javafx.widget.FilteredTableController
import com.subakstudio.wola.config.WoLaConfig
import com.subakstudio.wola.model.WolRow
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.MenuItem
import javafx.scene.control.TableView
import java.io.File
import java.net.URL
import java.util.*
import kotlin.system.exitProcess

/**
 * Created by yeoupooh on 4/12/16.
 */
class WoLaController : Initializable {
    // NOTE: name of variable should be id + "Controller"
    // NOTE: val isn't suitable for injection
    @FXML private var filteredTablePaneController: FilteredTableController ? = null
    @FXML private var filteredTablePane: Parent? = null

    @FXML fun onAction(event: ActionEvent) {
        println("WoLaController: onAction: $event")
        if (event.source is MenuItem) {
            val id = (event.source as MenuItem).id
            println("WoLaController: id=$id")
            when (id ) {
                "menuItemQuit" -> exitProcess(0)
                "menuItemAbout" -> {
                    //show dialog}
                }
            }
        }
    }

    //@FXML
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        assert(filteredTablePane != null) { "fx:id=\"filteredTablePane\" was not injected: check your FXML file 'WoLa.fxml'." }

        var table: TableView<*> = filteredTablePane?.lookup("#table") as TableView<*>

        println("WoLaController: pane=$filteredTablePane")
        println("WoLaController: table=$table")
        //println("WoLaController: " + (filteredTablePane is FilteredTableController)
        println("WoLaController: child ctrl=$filteredTablePaneController")

        loadConfig()
    }

    private fun loadConfig() {
        var om: ObjectMapper = ObjectMapper()
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        var config = om.readValue(File(this.javaClass.getResource("/wola.config.json").file), WoLaConfig::class.java)
        filteredTablePaneController?.let {
            for (host in config.hosts) {
                (filteredTablePaneController as FilteredTableController)
                        .addRow(WolRow(host.name, host.type, host.options, "action"))
            }
        }
    }
}