import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;

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

        // for(Double c : coeffs)
        //     System.out.println(c + " ");

        MyMainFrame frame = new MyMainFrame(coeffs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        console.close();
        enter.close();
    }

    private final int WIDTH = 700;
    private final int HEIGHT = 400;
    private Double[] coeffs;

    private JTextField fromField;
    private JTextField toField;
    private JTextField stepField;

    public MyMainFrame (Double[] coeffs){
        super("Табулирование многочлена на отрезке по схеме Гонрнера");
        this.coeffs = coeffs;
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();

        setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);

    //@ Создание основных элементов
        //^ Текстовые поля для начала, конца интервала и шага табулирования
        fromField = new JTextField("0", 10);
        fromField.setMaximumSize(fromField.getPreferredSize());
        toField = new JTextField("0", 10);
        toField.setMaximumSize(toField.getPreferredSize());
        stepField = new JTextField("0", 10);
        stepField.setMaximumSize(stepField.getPreferredSize());

        //^ Кнопка вычисления
        JButton calculateButton = new JButton("Вычислить");

        //^ Кнопка очистки полей
        JButton clearButton = new JButton("Очистить поля");

    //@ Создание интерфейса
        //^ Область для ввода отрезка и длины шага табулирования
        JLabel fromLabel = new JLabel("Х изменяется от:");
        JLabel toLabel = new JLabel("до:");
        JLabel stepLabel = new JLabel("с шагом:");

        Box boxArguments = Box.createHorizontalBox();
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

        //^ Область для кнопок вычисления и сброса
        Box boxCalculateClearButtons = Box.createHorizontalBox();
        boxCalculateClearButtons.add(Box.createHorizontalGlue());

        boxCalculateClearButtons.add(calculateButton);
        boxCalculateClearButtons.add(Box.createHorizontalStrut(20));
        boxCalculateClearButtons.add(clearButton);

        boxCalculateClearButtons.add(Box.createHorizontalGlue());
        
        //^ Конечная Компоновка
        Box contentBox = Box.createVerticalBox();
        contentBox.add(boxArguments);
        contentBox.add(boxCalculateClearButtons);
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }
}
