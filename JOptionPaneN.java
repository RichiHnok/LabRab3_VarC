import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JOptionPaneN extends JFrame {
    private JTextField begin;
    private JTextField end;
    private JButton okButton;
    // private JButton canselButton;
    
    public JOptionPaneN(MyGornerTableModel data, MyGornerTableCellRenderer renderer, Container mmm){
        super("Поиск значений из промежутка");
        setSize(200, 30);
        Toolkit kit = Toolkit.getDefaultToolkit();

        this.setLocation((kit.getScreenSize().width - 200)/2, (kit.getScreenSize().height - 30)/2);

        // setDefaultCloseOperation(EXIT_ON_CLOSE);

        begin = new JTextField(10);
        begin.setMaximumSize(begin.getPreferredSize());
        end = new JTextField(10);
        end.setMaximumSize(end.getPreferredSize());

        JLabel findFrom = new JLabel("Поиск значений от:");
        JLabel findTo = new JLabel("дo:");

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                String value1 = begin.getText();
                String value2 = end.getText();

                Double v1 = Double.parseDouble(value1);
                Double v2 = Double.parseDouble(value2);
                
                renderer.clearNeedles();
                for(int i = 1; i <= 2; i++){
                    for(int u = 0; u < data.getRowCount(); u++){
                        Double temp = (Double) data.getValueAt(u, i);
                        // System.out.println("temp= " + temp + "  , val1= " + Double.parseDouble(value1) + "  , val2= " + Double.parseDouble(value2));
                        if(temp >= v1 && temp <= v2){
                            renderer.addNeedle(renderer.getFormatter().format(temp));
                        }
                    }
                }
                renderer.setNeedle(null);
                mmm.repaint();  
            }
        });

        Box boxFields = Box.createHorizontalBox();
        boxFields.add(Box.createHorizontalGlue());
        boxFields.add(findFrom);
        boxFields.add(Box.createHorizontalStrut(10));
        boxFields.add(begin);
        boxFields.add(Box.createHorizontalStrut(15));
        boxFields.add(findTo);
        boxFields.add(Box.createHorizontalStrut(10));
        boxFields.add(end);
        boxFields.add(Box.createHorizontalStrut(20));
        boxFields.add(okButton);
        boxFields.add(Box.createHorizontalGlue());

        this.setVisible(true);
        this.setContentPane(boxFields);
    }    
}
