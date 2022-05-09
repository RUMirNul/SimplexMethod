package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class View extends JFrame {


    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel inputPane = new JPanel();
    private JPanel outputPane = new JPanel();
    private JTextArea outputTextArea = new JTextArea();
    private JFrame mainFrame = new JFrame();
    private static JTextArea forUpdateIGNORE = new JTextArea("");
    private int NX;
    private int NLIMIT;

    private ArrayList<JTextField> paramsLEquation;
    private ArrayList<JLabel> Xlabels;
    private ArrayList<ArrayList<JTextField>> paramsLimit;
    private ArrayList<JComboBox> signs;
    private JComboBox aspiration;

    static {
        forUpdateIGNORE.setEditable(false);
        forUpdateIGNORE.setVisible(false);
    }


    public View(Controller controller) {
        this.controller = controller;
        initView();
        mainFrame.setTitle("Simplex Method");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(1920, 1080));
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    private void initView() {
        tabbedPane.removeAll();
        inputPane = new JPanel();
        outputPane = new JPanel();
        outputTextArea = new JTextArea("");
        outputTextArea.setEditable(false);
        inputPane.setPreferredSize(new Dimension(1920, 1080));
        outputPane.setPreferredSize(new Dimension(1920, 720));
        outputTextArea.setPreferredSize(new Dimension(1920, 720));
        outputPane.add(outputTextArea);


        JScrollPane jScrollPaneInput = new JScrollPane(inputPane);
        tabbedPane.add("Ввод", jScrollPaneInput);
        JScrollPane jScrollPaneOutput = new JScrollPane(outputPane);
        tabbedPane.add("Вывод", jScrollPaneOutput);

        jScrollPaneInput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPaneOutput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPaneInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        inputPane.setLayout(new BorderLayout());
        inputPane.setBackground(Color.WHITE);

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

        JTextArea errorInputParam = new JTextArea("Значения должны быть больше 0!");
        errorInputParam.setBounds(380, 10, 300, 25);
        errorInputParam.setFont(new Font("Dialog", Font.PLAIN, 14));
        errorInputParam.setEditable(false);
        errorInputParam.setVisible(false);

        JButton okButton = new JButton("Применить");
        okButton.setBounds(250, 10, 120, 25);
        okButton.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(nParams.getText()) >= 1 && Integer.parseInt(nLimits.getText()) >= 1) {
                    NX = Integer.parseInt(nParams.getText());
                    NLIMIT = Integer.parseInt(nLimits.getText());
                    controller.setNumberOfVariables(NX);
                    controller.setNumberOfLimit(NLIMIT);
                    nParams.setEditable(false);
                    nLimits.setEditable(false);
                    errorInputParam.setVisible(false);
                    initTableOfVariables();
                    okButton.setVisible(false);
                } else {
                    errorInputParam.setVisible(true);
                    return;
                }
                inputPane.revalidate();
                mainFrame.revalidate();
            }
        });

        JButton resetButton = new JButton("Сброс");
        resetButton.setBounds(250, 40, 120, 25);
        resetButton.setVisible(true);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });


        inputPane.add(textEnterNParams);
        inputPane.add(textEnterNLimits);
        inputPane.add(okButton);
        inputPane.add(nParams);
        inputPane.add(nLimits);
        inputPane.add(errorInputParam);
        inputPane.add(resetButton);
        inputPane.add(forUpdateIGNORE);

        mainFrame.getContentPane().add(tabbedPane);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    private void restart() {
        initView();
    }

    private void initTableOfVariables() {
        JTextArea LEquation = new JTextArea("L(x..)=");
        LEquation.setFont(new Font("Dialog", Font.PLAIN, 14));
        LEquation.setBounds(5, 72, 40, 20);
        LEquation.setVisible(true);
        LEquation.setEditable(false);

        paramsLEquation = new ArrayList<>();
        Xlabels = new ArrayList<>();
        for (int i = 0; i < NX + 1; i++) {
            paramsLEquation.add(new JTextField("0"));

            if (i == 0) {
                Xlabels.add(new JLabel("   +"));
            } else {
                if (i == NX) {
                    Xlabels.add(new JLabel("X" + i));
                } else {
                    Xlabels.add(new JLabel("X" + i + " +"));
                }
            }
        }
        for (int i = 0; i < paramsLEquation.size(); i++) {
            paramsLEquation.get(i).setVisible(true);
            paramsLEquation.get(i).setEditable(true);
            paramsLEquation.get(i).setBounds(50 + 60 * i, 70, 25, 25);
            Xlabels.get(i).setVisible(true);
            Xlabels.get(i).setBounds(75 + 60 * i, 70, 30, 25);
            Xlabels.get(i).setFont(new Font("Dialog", Font.PLAIN, 12));
            inputPane.add(Xlabels.get(i));
            inputPane.add(paramsLEquation.get(i));

        }

        JTextArea arrow = new JTextArea("->");
        arrow.setEditable(false);
        arrow.setFont(new Font("Dialog", Font.BOLD, 15));
        arrow.setBounds(40 + 60 * paramsLEquation.size(), 70, 20, 25);

        aspiration = new JComboBox(new String[]{"MIN", "MAX"});
        aspiration.setBounds(65 + 60 * paramsLEquation.size(), 70, 75, 25);

        inputPane.setPreferredSize(new Dimension(200 + 60 * paramsLEquation.size(), 200 + 30 * NLIMIT));


        JTextArea textLimitEnter = new JTextArea("Ограничения: ");
        textLimitEnter.setFont(new Font("Dialog", Font.BOLD, 14));
        textLimitEnter.setVisible(true);
        textLimitEnter.setEditable(false);
        textLimitEnter.setBounds(5, 100, 100, 20);

        paramsLimit = new ArrayList<>();
        signs = new ArrayList<>();
        for (int i = 0; i < NLIMIT; i++) {
            paramsLimit.add(new ArrayList<>());
            signs.add(new JComboBox(new String[]{"<=", ">="}));
            for (int j = 0; j < NX + 1; j++) {
                paramsLimit.get(i).add(new JTextField("0"));
            }
        }

        for (int i = 0; i < NLIMIT; i++) {
            for (int j = 0; j < NX + 1; j++) {
                paramsLimit.get(i).get(j).setVisible(true);
                paramsLimit.get(i).get(j).setEditable(true);
                JTextArea temp = new JTextArea();
                if (j != NX) {

                    if (j != NX - 1) {
                        temp.setText("X" + (j + 1) + " + ");
                    } else {
                        temp.setText("X" + (j + 1));
                    }
                    temp.setEditable(false);
                    temp.setVisible(true);
                    temp.setBounds(135 + 60 * j, 155 + 30 * i, 30, 25);
                    paramsLimit.get(i).get(j).setBounds(110 + 60 * j, 150 + 30 * i, 25, 25);

                } else {
                    paramsLimit.get(i).get(j).setBounds(170 + 60 * j, 150 + 30 * i, 25, 25);
                }


                inputPane.add(paramsLimit.get(i).get(j));
                inputPane.add(temp);

            }
            signs.get(i).setBounds(110 + 60 * NX, 150 + 30 * i, 50, 25);
            inputPane.add(signs.get(i));
        }

        JButton solved = new JButton("Решить");
        solved.setBounds(400, 40, 120, 25);
        solved.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //blockEditInputValue();
                if (aspiration.getSelectedIndex() == 0) {
                    controller.setMax(false);
                } else {
                    controller.setMax(true);
                }
                outputTextArea.setText("");

                controller.setTable(paramsLEquation, paramsLimit, signs);
                tabbedPane.setSelectedIndex(1);

            }

        });


        inputPane.add(LEquation);
        inputPane.add(textLimitEnter);
        inputPane.add(arrow);
        inputPane.add(aspiration);
        inputPane.add(solved);

        inputPane.add(forUpdateIGNORE);

    }

    void blockEditInputValue() {
        for (int i = 0; i < NLIMIT; i++) {
            for (int j = 0; j < NX + 1; j++) {
                paramsLimit.get(i).get(j).setEditable(false);
            }
            signs.get(i).setEnabled(false);
        }
        for (int i = 0; i < paramsLEquation.size(); i++) {
            paramsLEquation.get(i).setEditable(false);
        }

        aspiration.setEnabled(false);
    }

    public void addOutputText(String text) {
        outputTextArea.append(text);
        outputTextArea.setPreferredSize(new Dimension(1920, outputTextArea.getHeight() + 43));
        outputPane.setPreferredSize(new Dimension(1920, outputTextArea.getHeight()));
        outputTextArea.revalidate();
        outputPane.revalidate();
        mainFrame.revalidate();
    }

    public void setOutputText(String text) {
        outputTextArea.setText(text);
    }

}
