package com.subakstudio.javafx.widget

import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import java.net.URL
import java.util.*

/**
 * Created by yeoupooh on 4/12/16.
 */
class FilteredTableController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private val resources: ResourceBundle? = null

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private val location: URL? = null

    @FXML // fx:id="colName"
    private val colName: TableColumn<*, *>? = null // Value injected by FXMLLoader

    @FXML // fx:id="colType"
    private val colType: TableColumn<*, *>? = null // Value injected by FXMLLoader

    @FXML // fx:id="colOptions"
    private val colOptions: TableColumn<*, *>? = null // Value injected by FXMLLoader

    @FXML // fx:id="colAction"
    private val colAction: TableColumn<*, *>? = null // Value injected by FXMLLoader

    @FXML
    internal fun initialize() {
        assert(colName != null) { "fx:id=\"colName\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colType != null) { "fx:id=\"colType\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colOptions != null) { "fx:id=\"colOptions\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colAction != null) { "fx:id=\"colAction\" was not injected: check your FXML file 'FilteredTable.fxml'." }

    }// This method is called by the FXMLLoader when initialization is complete
}