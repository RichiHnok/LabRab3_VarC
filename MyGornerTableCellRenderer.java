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

public class MyGornerTableCellRenderer implements TableCellRenderer {
    
    private String needle = null;

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();


    public MyGornerTableCellRenderer(){
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }    
    
    public void setNeedle(String needle){
        this.needle = needle;
    }    
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        String formattedDouble = formatter.format(value);

        label.setText(formattedDouble);
        if(column == 1 && needle != null && needle.equals(formattedDouble)){
            panel.setBackground(Color.RED);
        }else{
            panel.setBackground(Color.WHITE);
        }
        return panel;
    }
}
