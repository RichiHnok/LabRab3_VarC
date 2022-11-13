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
        return 2;
    }
    public int getRowCount(){
        return Double.valueOf(Math.ceil((to - from)/step)).intValue() + 1;
    }

    public String getColumnName(int column){
        switch(column){
            case 0:
                return "Значение Х";
            default:
                return "Значение многочлена";
        }
    }

    public Class<?> getColumnClass(int column){
        return Double.class;
    }    

    public Object getValueAt(int row, int column){
        double x = from + step*row;
        if(column == 0){
            return x;
        }else{
            int len = coeffs.length;
            double bn = coeffs[len - 1];
            for(int i = len - 2; i >= 0 ; i--){
                bn = coeffs[i] + bn * x;
            }
            return bn;
        }
    }        
}
