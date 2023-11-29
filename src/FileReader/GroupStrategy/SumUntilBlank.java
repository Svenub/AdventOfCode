package FileReader.GroupStrategy;

import java.util.ArrayList;

public class SumUntilBlank implements GroupStrategy {

    private ArrayList<Object> output = new ArrayList<>();
    private ArrayList<Object> group = new ArrayList<>();



    @Override
    public void group(Object data) {
        if (data.equals("\n") || data.equals("")) {
            output.add(sumList(group));
            group.clear();
        } else {
            group.add(data);
        }

    }

    @Override
    public ArrayList<Object> getOutPut() {
        return output;
    }

    private int sumList(ArrayList<Object> list){
        int res = 0;
        for (Object o : list) {
            res += (Integer) o;
        }
        return res;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

}
