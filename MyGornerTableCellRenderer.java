import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.table.TableCellRenderer;
import java.util.HashSet;

public class MyGornerTableCellRenderer implements TableCellRenderer {
    
    private String needle = null;

    private HashSet<String> setOfNeedles;

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public DecimalFormat getFormatter(){
        return formatter;
    }

    public MyGornerTableCellRenderer(){
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        setOfNeedles = new HashSet<>();
    }    
    
    public void setNeedle(String needle){
        this.needle = needle;
    }

    public void clearNeedle(){
        needle = null;
    }

    public void addNeedle(String needle){
        setOfNeedles.add(needle);
        // System.out.println(setOfNeedles.toString());
    }

    public String getNeedle(){
        return needle;
    }

    public void clearNeedles(){
        setOfNeedles = new HashSet<>();
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        String formattedDouble = formatter.format(value);


        label.setText(formattedDouble);
        if((column == 1 || column == 2) && ((needle != null && needle.equals(formattedDouble)) || (setOfNeedles != null && setOfNeedles.contains(formattedDouble)) )){
            panel.setBackground(Color.RED);
            label.setForeground(Color.WHITE);
        }else if((column + row) % 2 == 0){
            panel.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }else{            
            panel.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);
        }
        // clearNeedles();
        return panel;
    }
}
