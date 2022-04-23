package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JPanel {

    private Controller controller;


    public View(Controller controller) {
        //setFocusable(true);
        this.controller = controller;
        initView();
    }

    private void initView() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JTextArea textEnterNParams = new JTextArea("Введите колличество переменных: ");

        textEnterNParams.setFont(new Font("Dialog", Font.PLAIN, 12));
        textEnterNParams.setBounds(5, 10, 200, 25);
        textEnterNParams.setVisible(true);
        textEnterNParams.setEditable(false);

        JTextField nParams = new JTextField("0");
        nParams.setBounds(210, 10, 25, 25);
        nParams.setVisible(true);


        JTextArea textEnterNLimits = new JTextArea("Введите количество ограничений: ");
        textEnterNLimits.setBounds(5, 40, 200, 25);
        textEnterNLimits.setVisible(true);
        textEnterNLimits.setEditable(false);

        JTextField nLimits = new JTextField("0");
        nLimits.setBounds(210, 40, 25, 25);
        nLimits.setVisible(true);


        JButton okButton = new JButton("Ok");
        okButton.setBounds(250, 10, 50, 30);
        okButton.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(nParams.getText()) < 1) return;
                controller.setNumberOfVariables(Integer.parseInt(nParams.getText()));
                nParams.setEditable(false);
            }
        });



        JTextArea area3 = new JTextArea("");
        area3.setEditable(false);
        area3.setVisible(false);

        this.add(textEnterNParams);
        this.add(textEnterNLimits);
        this.add(okButton);
        this.add(nParams);
        this.add(nLimits);
        this.add(area3);

    }
}
