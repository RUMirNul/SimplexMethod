package controller;

import model.Model;
import view.View;

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

    public int getNumberOfVariables(){
        return model.getNumberOfVariables();
    }

    public int getNumberOfLimit() {
        return model.getNumberOfLimit();
    }

    public void setNumberOfLimit(int numberOfLimit) {
        model.setNumberOfLimit(numberOfLimit);
    }
}
