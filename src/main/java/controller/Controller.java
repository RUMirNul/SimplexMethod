package controller;

import model.Method;
import model.Model;
import tools.Table;
import view.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }


    public View getView() {
        return view;
    }

    public void setNumberOfVariables(int NumberOfVariables) {
        model.setNumberOfVariables(NumberOfVariables);
    }

    public int getNumberOfVariables() {
        return model.getNumberOfVariables();
    }

    public int getNumberOfLimit() {
        return model.getNumberOfLimit();
    }

    public void setNumberOfLimit(int numberOfLimit) {
        model.setNumberOfLimit(numberOfLimit);
    }

    public void setMax(boolean max) {
        model.setMax(max);
    }

    public void setTable(ArrayList<JTextField> paramsLEquation, ArrayList<ArrayList<JTextField>> paramsLimit, ArrayList<JComboBox> signs) {
        ArrayList<ArrayList<Float>> tableValues = new ArrayList<>();

        ArrayList<String> columns = new ArrayList<>();
        columns.add("св/ч");
        for (int i = 1; i <= model.getNumberOfVariables(); i++) {
            columns.add("x" + i);
        }

        ArrayList<String> rows = new ArrayList<>();
        rows.add("ц.ф.");
        for (int i = 1; i <= model.getNumberOfLimit(); i++) {
            rows.add("y" + i);
        }

        //заполнение L(x)
        tableValues.add(new ArrayList<>());
        for (int i = 0; i < paramsLEquation.size(); i++) {
            if (model.isMax()) {
                if (i == 0) {
                    tableValues.get(0).add(Float.valueOf(paramsLEquation.get(i).getText()) * (-1));
                } else {
                    tableValues.get(0).add(Float.valueOf(paramsLEquation.get(i).getText()));
                }
            }
            if (!model.isMax()) {
                if (i == 0) {
                    tableValues.get(0).add(Float.valueOf(paramsLEquation.get(i).getText()));
                } else {
                    tableValues.get(0).add(Float.valueOf(paramsLEquation.get(i).getText()) * (-1));
                }
            }

        }

        //заполнение свободных членов
        for (int i = 0; i < paramsLimit.size(); i++) {
            int coefficient = 1;
            if (signs.get(i).getSelectedIndex() == 1) coefficient = -1;
            tableValues.add(new ArrayList<>());

            //заполнение свободных членов
            tableValues.get(i + 1).add(Float.valueOf(paramsLimit.get(i).get(model.getNumberOfVariables()).getText()) * coefficient);

            //заполнение остальных ячеек
            for (int j = 0; j < paramsLimit.get(0).size() - 1; j++) {
                tableValues.get(i + 1).add(Float.valueOf(paramsLimit.get(i).get(j).getText()) * coefficient);
            }
        }

        model.setTable(tableValues, columns, rows);
    }

    public void output(Table table) {
        view.addOutputText(table.toString());
    }

    public void output(Method method) {
        String met = "";
        switch (method) {
            case MODIFIED:
                met = "модифицированный симплексный метод";
                break;
            case STANDART:
                met = "стандартный симплексный метод";
                break;
        }

        view.addOutputText(String.format("Метод решения: %s\n", met));
    }

    public void output(int resolutionRow, int resolutionColumn) {
        view.addOutputText(String.format("Разрешающая строка: %s\nРазрешающий столбец: %s\n", model.getTable().getRows().get(resolutionRow), model.getTable().getColumns().get(resolutionColumn)));
    }

    public void output(String msg) {
        view.addOutputText(msg + "\n");
    }

    public void outputError(String msg){
        view.setOutputText(msg);
    }

}
