import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.text.DecimalFormat;
// import java.awt.Image;
// import javax.swing.BorderFactory;
public class MyMainFrame extends JFrame {
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        Scanner enter = new Scanner(console.nextLine());
        
        ArrayList<Double> coef = new ArrayList<>();
        while(enter.hasNextDouble()){
            coef.add(enter.nextDouble());
        }

        Double[] coeffs = new Double[coef.size()];
        coeffs = coef.toArray(coeffs);

        MyMainFrame frame = new MyMainFrame(coeffs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        console.close();
        enter.close();
    }

    private final int WIDTH = 700;
    private final int HEIGHT = 400;
    private Double[] coeffs;
    
    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem saveToCSVFileMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem searchValuesFromIntervalMenuItem;

    private JTextField fromField;
    private JTextField toField;
    private JTextField stepField;

    private MyGornerTableModel data;
    private MyGornerTableCellRenderer renderer = new MyGornerTableCellRenderer();

    private Box boxResult;

    public MyMainFrame (Double[] coeffs){
        super("Табулирование многочлена на отрезке по схеме Гонрнера");
        this.coeffs = coeffs;
        
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        
        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);

        boxResult = Box.createHorizontalBox();
        boxResult.add(new JPanel());
        
    //@ Реализация основного функционала
        
        //^ Главное меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu helpMenu = new JMenu("Справка");
        menuBar.add(helpMenu);
        
        //^ Сохранение в читабельном виде
        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MyMainFrame.this) == JFileChooser.APPROVE_OPTION)
                    saveToTextFile(fileChooser.getSelectedFile());
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);

        //^ Сохранение в двоичном формате 
        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика"){
            public void actionPerformed(ActionEvent event){
                if(fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(MyMainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToGraphicsFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

        //^ Сохранение данных для построения таблицы
        Action saveToGrahicsAction = new AbstractAction("сохранить  в CSV формате"){
            public void actionPerformed(ActionEvent event){
                if(fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(MyMainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToCSVFile(fileChooser.getSelectedFile(), renderer.getFormatter());
                }
            }
        };
        saveToCSVFileMenuItem = fileMenu.add(saveToGrahicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

        //^ Задать искомое значение
        Action searchValueAction = new AbstractAction("Найти значение многочлена"){
            public void actionPerformed(ActionEvent event){
                String value = JOptionPane.showInputDialog(
                    MyMainFrame.this,
                    "Введите значение для поиска",
                    "Поиск значения",
                    JOptionPane.QUESTION_MESSAGE
                );
                    
                renderer.setNeedle(value);
                renderer.clearNeedles();
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);

        Action searchValuesFromInterval = new AbstractAction("Найти значения из промежутка"){
            public void actionPerformed(ActionEvent event){
                JOptionPaneN p = new JOptionPaneN(data, renderer, getContentPane());
                p.setSize(500, 100);
            }
        };
        searchValuesFromIntervalMenuItem = tableMenu.add(searchValuesFromInterval);
        searchValuesFromIntervalMenuItem.setEnabled(false);
        Action aboutProgram = new AbstractAction("Об авторе"){
            public void actionPerformed(ActionEvent event){
                JDialog helpWindow = new JDialog(MyMainFrame.this, "Информация");
                helpWindow.setLocation((kit.getScreenSize().width - 300)/2, (kit.getScreenSize().height - 120)/2);
                
                JLabel  info = new JLabel("Вязовский Артемий, 9 группа");
                
                JLabel image = new JLabel();
                ImageIcon photo = new ImageIcon("фото.png");
                image.setIcon(photo);
                
                Box box = Box.createHorizontalBox();
                box.add(Box.createHorizontalGlue());
                box.add(info);
                box.add(Box.createHorizontalStrut(5));
                box.add(image);
                box.add(Box.createHorizontalGlue());
                
                
                helpWindow.setSize(300,120);
                helpWindow.add(box);
                helpWindow.setVisible(true);
            }
        };
        JMenuItem helpMenuItem = helpMenu.add(aboutProgram);
        helpMenuItem.setEnabled(true);
    
        //^ Текстовые поля для начала, конца интервала и шага табулирования
        fromField = new JTextField("0", 10);
        fromField.setMaximumSize(fromField.getPreferredSize());
        toField = new JTextField("1", 10);
        toField.setMaximumSize(toField.getPreferredSize());
        stepField = new JTextField("0.1", 10);
        stepField.setMaximumSize(stepField.getPreferredSize());

        //^ Кнопка вычисления
        JButton calculateButton = new JButton("Вычислить");

        calculateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                try{
                    Double from = Double.parseDouble(fromField.getText());
                    Double to = Double.parseDouble(toField.getText());
                    Double step = Double.parseDouble(stepField.getText());
                    data = new MyGornerTableModel(from, to, step, MyMainFrame.this.coeffs);

                    JTable table = new JTable(data); 
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setRowHeight(30);

                    boxResult.removeAll();
                    boxResult.add(new JScrollPane(table));

                    getContentPane().validate();

                    saveToTextMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    saveToCSVFileMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                    searchValuesFromIntervalMenuItem.setEnabled(true);

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(
                        MyMainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой",
                        "Ошибочный формат числа",
                        JOptionPane.WARNING_MESSAGE
                    );    
                }    
            }    
        });    

        //^ Кнопка очистки полей
        JButton clearButton = new JButton("Очистить поля");
        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                fromField.setText("0.0");
                toField.setText("1.0");
                stepField.setText("0.1");

                boxResult.removeAll();
                boxResult.add(new JPanel());

                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                saveToCSVFileMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                searchValuesFromIntervalMenuItem.setEnabled(false);

                getContentPane().validate();
            }            
        });            

        //^ 
        //^
        //^
    //@ Создание интерфейса
        //^ Область для ввода отрезка и длины шага табулирования
        JLabel fromLabel = new JLabel("Х изменяется от:");
        JLabel toLabel = new JLabel("до:");
        JLabel stepLabel = new JLabel("с шагом:");

        Box boxArguments = Box.createHorizontalBox();

        boxArguments.setBorder(BorderFactory.createBevelBorder(1));
        boxArguments.add(Box.createHorizontalGlue());

        boxArguments.add(fromLabel);
        boxArguments.add(Box.createHorizontalStrut(10));
        boxArguments.add(fromField);
        boxArguments.add(Box.createHorizontalStrut(15));

        boxArguments.add(toLabel);
        boxArguments.add(Box.createHorizontalStrut(10));
        boxArguments.add(toField);
        boxArguments.add(Box.createHorizontalStrut(15));

        boxArguments.add(stepLabel);
        boxArguments.add(Box.createHorizontalStrut(10));
        boxArguments.add(stepField);

        boxArguments.add(Box.createHorizontalGlue());

        boxArguments.setPreferredSize(new Dimension(
            Double.valueOf(boxArguments.getMaximumSize().getWidth()).intValue(),
            Double.valueOf(boxArguments.getMinimumSize().getHeight()).intValue() * 2
        ));

        
        //^ Область для таблицы
        //^ Область для кнопок вычисления и сброса
        Box boxCalculateClearButtons = Box.createHorizontalBox();
        boxCalculateClearButtons.add(Box.createHorizontalGlue());
        
        boxCalculateClearButtons.add(calculateButton);
        boxCalculateClearButtons.add(Box.createHorizontalStrut(20));
        boxCalculateClearButtons.add(clearButton);
        
        boxCalculateClearButtons.add(Box.createHorizontalGlue());
        
        boxCalculateClearButtons.setPreferredSize(new DimensionUIResource(
            Double.valueOf(boxCalculateClearButtons.getMaximumSize().getWidth()).intValue(), 
            Double.valueOf(boxCalculateClearButtons.getMinimumSize().getHeight()).intValue() * 2
        ));
        
        //^ Конечная Компоновка
        getContentPane().add(boxArguments, BorderLayout.NORTH);
        getContentPane().add(boxResult, BorderLayout.CENTER);
        getContentPane().add(boxCalculateClearButtons, BorderLayout.SOUTH);
    }

    protected void saveToTextFile(File selectedFile){
        DecimalFormat dF = new DecimalFormat("#.#####");
        try{
            PrintStream out = new PrintStream(selectedFile);
            out.println("Результаты табулирования многочлена по схеме Горнера");
            out.print("Многочлен: ");
            for(int i = 0; i < coeffs.length; i++){
                out.print(dF.format(coeffs[i]) + "x^" + (coeffs.length - i - 1));
                if(i != coeffs.length - 1)
                    out.print(" + ");
            }
            out.println("");
            out.println("Интервал от " + dF.format(data.getFrom()) + " до " + dF.format(data.getTo()) + " с шагом " + dF.format(data.getStep()));
            out.println("===================================================");

            for(int i = 0; i < data.getRowCount(); i++){
                out.println("Значение в точке " + dF.format(data.getValueAt(i, 0)) + " равно " + dF.format(data.getValueAt(i, 1)));
            }
            out.close();
        }catch(FileNotFoundException ex){}
    }

    protected void saveToGraphicsFile(File selectedFile){
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile))){
            for(int i = 0; i < data.getRowCount(); i++){
                out.writeDouble((Double)data.getValueAt(i,0));
                out.writeDouble((Double)data.getValueAt(i,2));
            }
        }catch(Exception ex){}
    }

    protected void saveToCSVFile(File selectedFile, DecimalFormat formatter){
        try{
            PrintStream out = new PrintStream(selectedFile);
            for(int i = 0; i < data.getRowCount(); i++){
                out.println(formatter.format(data.getValueAt(i, 0)) + ";" + formatter.format(data.getValueAt(i, 1)));
            }
            out.close();
        }catch(FileNotFoundException ex){}
    }
}
