package model;

import controller.Controller;
import tools.Table;

import java.util.ArrayList;

public class Model {
    private Controller controller;
    private int NumberOfVariables;
    private int NumberOfLimit;
    private boolean Max = true;

    private Method method = Method.MODIFIED;

    private Table table;

    public void setNumberOfVariables(int numberOfVariables) {
        NumberOfVariables = numberOfVariables;

    }

    public int getNumberOfVariables() {
        return NumberOfVariables;
    }

    public int getNumberOfLimit() {
        return NumberOfLimit;
    }

    public void setNumberOfLimit(int numberOfLimit) {
        NumberOfLimit = numberOfLimit;
    }

    public void setMax(boolean max) {
        Max = max;
    }

    public boolean isMax() {
        return Max;
    }

    public void setTable(ArrayList<ArrayList<Float>> tableValues, ArrayList<String> columns, ArrayList<String> rows) {
        this.table = new Table(tableValues, columns, rows);
        start();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        controller.output("Исходная таблица: ");
        controller.output(table);
        controller.output("\n\n");
        controller.output("Решение:\n");

        int iter = 0;

        while (table.hasPositiveValuesInLx()) {
            iter++;
            int resolutionColumn = -1;
            int resolutionRow = -1;

            if (method == Method.MODIFIED) {
                if (table.hasNegativeValuesInFreeVariables()) {
                    int observeRow = table.minValueInFirstColumn();

                    resolutionColumn = table.findModifiedResolutionColumn(observeRow);
                } else {
                    method = Method.STANDART;
                }
            }
            if (method == Method.STANDART) {
                resolutionColumn = table.findResolutionColumn(0);
            }

            resolutionRow = table.findResolutionRow(resolutionColumn);
            recalculation(resolutionRow, resolutionColumn);

            controller.output(table);
            controller.output(method);
            controller.output(resolutionRow, resolutionColumn);
            controller.output("\n\n");

            if (iter > 100) {
                controller.outputError("\nНе удалось найти решение за 100 итераций!\n");
                //controller.output("\nНе удалось найти решение за 100 итераций!\n");
                break;
            }

        }
        if (iter <= 100) {
            controller.output(solutionOutput(table));
        }

    }

    public void recalculation(int resolutionRow, int resolutionColumn) {
        //меняем местами имена строки и столбеца
        table.swapNameColumnsAndRows(resolutionRow, resolutionColumn);

        Table newTable = new Table(table.getNumberOfRows(), table.getNumberOfColumns());

        newTable.setValueByIndex(resolutionRow, resolutionColumn, 1 / table.getValueByIndex(resolutionRow, resolutionColumn));
        float oldResValue = table.getValueByIndex(resolutionRow, resolutionColumn);

        // заполняем значениями разрешающй столбец и строку
        for (int row = 0; row < table.getNumberOfRows(); row++) {
            for (int column = 0; column < table.getNumberOfColumns(); column++) {
                if (row != resolutionRow && column == resolutionColumn) {
                    newTable.setValueByIndex(row, column, table.getValueByIndex(row, column) / oldResValue * (-1));
                }
                if (row == resolutionRow && column != resolutionColumn) {
                    newTable.setValueByIndex(row, column, table.getValueByIndex(row, column) / oldResValue);
                }
            }

        }
        // заполняем остальны ячейки
        for (int row = 0; row < table.getNumberOfRows(); row++) {
            for (int column = 0; column < table.getNumberOfColumns(); column++) {
               if (row != resolutionRow && column != resolutionColumn) {
                   float oldRowValue = table.getValueByIndex(resolutionRow, column);
                   float newColumnValue = newTable.getValueByIndex(row, resolutionColumn);
                   float newValue = table.getValueByIndex(row, column) + (oldRowValue * newColumnValue);
                   newTable.setValueByIndex(row, column, newValue);
               }
            }

        }

        table.setValues(newTable.getValues());
    }

    public Table getTable() {
        return table;
    }

    private String solutionOutput(Table table){
        controller.output("Ответ:");
        StringBuilder sb = new StringBuilder();
        String Lx = "L";
        //добавить домножение на минус 1 для Lx
        if (isMax()) {
            Lx +="max = " + table.getValueByIndex(0,0) * (-1);
        } else {
            Lx +="min = " + table.getValueByIndex(0,0);
        }
        ArrayList<String> lines = new ArrayList<String>();
        lines.add(Lx);
        for (int i = 1; i <= NumberOfVariables; i++) {
            lines.add("x" + i);
        }
        for (int i = 1; i <= NumberOfLimit; i++) {
            lines.add("y" + i);
        }
        for (int i = 1; i < table.getNumberOfRows(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if (lines.get(j).contains(table.getRows().get(i))) {
                    lines.set(j, table.getRows().get(i) + " = " + table.getValueByIndex(i, 0));
                }
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).contains("=")) {
                lines.set(i, lines.get(i) + " = " + "0");
            }
        }
        for (String line : lines) {
            sb.append(line + "   ");
        }

        return sb.toString();

    }
}
