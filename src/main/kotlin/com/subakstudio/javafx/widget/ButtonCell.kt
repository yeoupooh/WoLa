package com.subakstudio.javafx.widget

import com.subakstudio.wola.model.WolRow
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn

/**
 * Created by yeoupooh on 4/13/16.
 */
class ButtonCell(column: TableColumn<WolRow, String>) : TableCell<WolRow, String>() {
    var cellButton: Button = Button("Wake Up")
    // Anonymous function to get cell
    var cell: () -> WolRow? = { tableView.items[tableRow.index] }

    init {
        println("ButtonCell: init: column=$column tableRow=$tableRow")
        cellButton.onAction = EventHandler<ActionEvent> { event ->
            println("ButtonCell: onAction: tableRow=${tableRow.index} ${cell()?.name} ${cell()?.options} $event")
        }
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty);
        if (!empty) {
            graphic = cellButton;
        }
    }
}