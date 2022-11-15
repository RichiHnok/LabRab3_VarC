import javax.swing.table.AbstractTableModel;

public class MyGornerTableModel extends AbstractTableModel{

    private Double[] coeffs;
    private Double from;
    private Double to;
    private Double step;

    public MyGornerTableModel(Double from, Double to, Double step, Double[] coeffs){
        this.coeffs = coeffs;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public Double getFrom(){
        return from;
    }
    public Double getTo(){
        return to;
    }
    public Double getStep(){
        return step;
    }
    public int getColumnCount(){
        // return 2;
        return 4;
    }
    public int getRowCount(){
        return Double.valueOf(Math.ceil((to - from)/step)).intValue() + 1;
    }

    public String getColumnName(int column){
        switch(column){
            case 0:
                return "Значение Х";
            case 1:
                return "Значение y1";
            case 2:
                return "Значение y2";
            default:
                return "Разница y1 и y2";
        }
    }

    public Class<?> getColumnClass(int column){
        return Double.class;
    }    

    public Object getValueAt(int row, int column){
        double x = from + step*row;
        int len = coeffs.length;
        double bn1 = coeffs[len -1];
        for(int i = len - 2; i >= 0; i--){
            bn1 = coeffs[i] + bn1 * x;
        }

        double bn2 = coeffs[0];
        for(int i = 1; i < len; i++){
            bn2 = coeffs[i] + bn2 * x;
        }
        switch(column){
            case 0:
                return x;
            case 1:
                return bn1;
            case 2:
                return bn2;
            default:
                return bn1 - bn2;
        }
    }        
}
