package tools;

import java.util.ArrayList;

public class Table {
    private ArrayList<ArrayList<Float>> values;
    private ArrayList<String> columns;
    private ArrayList<String> rows;

    public Table(ArrayList<ArrayList<Float>> values, ArrayList<String> columns, ArrayList<String> rows) {
        this.values = values;
        this.columns = columns;
        this.rows = rows;
    }

    public Table(int rows, int columns) {
        values = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            values.add(new ArrayList<>());
            for (int column = 0; column < columns; column++) {
                values.get(row).add(Float.valueOf(0));
            }
        }
    }

    // проверка, что есть полоэительные числа в L(x)
    public boolean hasPositiveValuesInLx() {
        for (int column = 1; column < columns.size(); column++) {
            if (values.get(0).get(column) > 0) {
                return true;
            }
        }
        return false;
    }

    // проверка, что в L(x) есть отрицательные числа
    public boolean hasNegativeValuesInFreeVariables() {
        for (int row = 1; row < values.size(); row++) {
            if (getValueByIndex(row, 0) < 0) {
                return true;
            }
        }
        return false;
    }

    //нахождение минивального числа в столбце свободных чисел
    public int minValueInFirstColumn() {
        int min = 1;
        for (int row = 1; row < values.size(); row++) {
            if (getValueByIndex(row, 0) < getValueByIndex(min, 0)) {
                min = row;
            }
        }
        return min;
    }

    // нахождение разрешающего столбца
    public int findResolutionColumn(int row) {
        int max = 1;
        for (int i = 1; i < getNumberOfColumns(); i++) {
            if (getValueByIndex(row, i) > getValueByIndex(row, max)) {
                max = i;
            }
        }
        return max;
    }

    public int findModifiedResolutionColumn(int row) {
        int min = 1;
        for (int column = 1; column < values.get(0).size(); column++) {
            if (getValueByIndex(row, column) < getValueByIndex(row, min)) {
                min = column;
            }
        }
        return min;
    }


    //нахождение разрешающей строки
    public int findResolutionRow(int column) {
        int freeMembers = 0;
        float minValue = Float.MAX_VALUE;
        int minIndex = 1;
        for (int row = 1; row < values.size(); row++) {
            float currentValue = getValueByIndex(row, freeMembers) / getValueByIndex(row, column);
            if (sameSign(getValueByIndex(row, freeMembers), getValueByIndex(row, column)) && currentValue < minValue) {
                minValue = currentValue;
                minIndex = row;
            }
        }
        return minIndex;
    }

    //проверка, что числа одного знака
    private boolean sameSign(float a, float b) {
        return (a < 0 && b < 0) || (a >= 0 && b >= 0);
    }

    //меняем местами название строки и столбца
    public void swapNameColumnsAndRows(int row, int column) {
        String temp = columns.get(column);
        columns.set(column, rows.get(row));
        rows.set(row, temp);
    }

    public int getNumberOfRows() {
        return values.size();
    }

    public int getNumberOfColumns() {
        return values.get(0).size();
    }

    public void setValueByIndex(int row, int column, float value) {
        values.get(row).set(column, value);
    }

    public float getValueByIndex(int row, int column) {
        return values.get(row).get(column);
    }

    public void setValues(ArrayList<ArrayList<Float>> values) {
        this.values = values;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public void setRows(ArrayList<String> rows) {
        this.rows = rows;
    }

    public ArrayList<ArrayList<Float>> getValues() {
        return values;
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public ArrayList<String> getRows() {
        return rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%16s", "св/ч"));
        for (int i = 1; i < columns.size(); i++) {
            sb.append(String.format("%8s ", columns.get(i)));
        }
        sb.append("\n");
        for (int row = 0; row < values.size(); row++) {
            sb.append(String.format("%-5s", rows.get(row)));
            for (int column = 0; column < columns.size(); column++) {
                sb.append(String.format("%8.2f", values.get(row).get(column)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
