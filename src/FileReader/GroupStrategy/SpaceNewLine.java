package FileReader.GroupStrategy;

import java.util.ArrayList;

public class SpaceNewLine implements GroupStrategy {

    private ArrayList<Object> output = new ArrayList<>();
    private ArrayList<Object> group = new ArrayList<>();

    private boolean includeWhiteSpace;
    private boolean includeNewLine;

    public SpaceNewLine(boolean includeWhiteSpace, boolean includeNewLine) {
        this.includeWhiteSpace = includeWhiteSpace;
        this.includeNewLine = includeNewLine;
    }

    @Override
    public void group(Object data) {
        output.add(data);
        if (!includeWhiteSpace && (data.equals(" ") || data.equals(' '))) {
            output.remove(output.size() - 1);
        } else if (!includeNewLine && (data.equals("\n") || data.equals('\n'))) {
            output.remove(output.size() - 1);
        }
    }

    @Override
    public ArrayList<Object> getOutPut() {
        return output;
    }

}
