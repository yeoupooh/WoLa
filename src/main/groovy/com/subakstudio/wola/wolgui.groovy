package com.subakstudio.wola

import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder

import javax.swing.*
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

swing = new SwingBuilder()
swing.edt {
    lookAndFeel 'nimbus'
}

model = []

class ButtonRenderer extends JButton implements TableCellRenderer {
    @Override
    Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText('Send')
        setOpaque(true)
        return this
    }
}

class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    int row
    int col
    protected JButton button
    private ITableCellClickListener actionListener

    ButtonEditor(actionListener) {
        this.actionListener = actionListener
        button = new JButton()
        button.setOpaque(true)
        button.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        stopCellEditing();
                        if (actionListener != null) {
                            actionListener.onClick(row, col)
                        }

                    }
                });
            }
        })
    }

    @Override
    Object getCellEditorValue() {
        return ""
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true
    }

    @Override
    Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setText('Send')
        this.row = row
        this.col = column
        return button
    }
}

interface ITableCellClickListener {
    void onClick(int row, int col)
}

swing.frame(title: 'Wake On Lan', size: [600, 300], location: [200, 200], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
    vbox {

        hbox {
            button('Refresh', actionPerformed: {
                loadHosts()
            })
        }
        scrollPane {
            t = table(rowHeight: 30) {
                tm = tableModel(list: model) {
                    closureColumn(header: 'Name', read: { row -> return row.name })
                    closureColumn(header: 'Type', read: { row -> return row.type }, maxWidth: 100)
                    closureColumn(header: 'Options', read: { row -> return row.options })
                    propertyColumn(
                            header: "Action",
                            propertyName: 'action',
                            maxWidth: 200,
                            cellRenderer: new ButtonRenderer(),
                            cellEditor: new ButtonEditor(new ITableCellClickListener() {
                                @Override
                                void onClick(int row, int col) {
                                    wakeUp(model[row])
                                    JOptionPane.showMessageDialog(t, 'WOL Packet Sent!')
                                }
                            })

                    )
                }

            }
        }
    }
}.setVisible(true)

def wakeUp(host) {
    new GroovyShell().parse(new File("wol_" + host.type + ".groovy")).with { wol ->
        wol.wakeUp(host.options)
    }
}

def loadHosts() {
    new Thread({
        def config = new JsonSlurper().parse(getClass().getResourceAsStream("/wol.config.json"))
        println config
        model.clear()
        config.hosts.each { host ->
            model.add(host)
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            void run() {
                tm.fireTableDataChanged()
            }
        })
    }).start()
}

loadHosts()
