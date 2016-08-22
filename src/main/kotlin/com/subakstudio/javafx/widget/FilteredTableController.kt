package com.subakstudio.javafx.widget

import com.subakstudio.wola.config.WolHost
import com.subakstudio.wola.model.WolRow
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.Callback
import java.net.URL
import java.util.*

/**
 * Created by yeoupooh on 4/12/16.
 */
class FilteredTableController : Initializable {
//    val observable: rx.Observable<String> = rx.Observable.from(arrayOf("a", "b", "c"))

    @FXML // ResourceBundle that was given to the FXMLLoader
    private var resources: ResourceBundle? = null

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private var location: URL? = null

    @FXML // fx:id="table"
    var table: TableView<WolRow>? = null  // Value injected by FXMLLoader

    @FXML // fx:id="colName"
    private var colName: TableColumn<WolRow, String>? = null // Value injected by FXMLLoader

    @FXML // fx:id="colType"
    private var colType: TableColumn<WolRow, String>? = null // Value injected by FXMLLoader

    @FXML // fx:id="colOptions"
    private var colOptions: TableColumn<WolRow, String>? = null // Value injected by FXMLLoader

    @FXML // fx:id="colAction"
    private var colAction: TableColumn<WolRow, String>? = null // Value injected by FXMLLoader

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        assert(table != null) { "fx:id=\"table\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colName != null) { "fx:id=\"colName\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colType != null) { "fx:id=\"colType\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colOptions != null) { "fx:id=\"colOptions\" was not injected: check your FXML file 'FilteredTable.fxml'." }
        assert(colAction != null) { "fx:id=\"colAction\" was not injected: check your FXML file 'FilteredTable.fxml'." }

        println("FilteredTableController: initialize")
        setupTable()
    }// This method is called by the FXMLLoader when initialization is complete

    private fun setupTable() {
        println("FilteredTableController: table=$table")
        println("FilteredTableController: colName=$colName")
        colName?.cellValueFactory = PropertyValueFactory<WolRow, String>("name")
        colType?.cellValueFactory = PropertyValueFactory<WolRow, String>("type")
        colOptions?.cellValueFactory = PropertyValueFactory<WolRow, String>("options")
        colAction?.cellFactory = Callback <TableColumn<WolRow, String>, TableCell<WolRow, String>>() { column -> ButtonCell(column) }
    }

    fun setItems(rows: ObservableList<WolRow>) {
        table?.items = rows
    }

    fun addRow(row: WolRow) {
        table?.items?.add(row)
    }

    fun setHosts(hosts: List<WolHost>) {
        table?.let {
            for (host in hosts) {
                table?.items?.add(WolRow(host.name, host.type, host.options))
            }
        }
    }
}




