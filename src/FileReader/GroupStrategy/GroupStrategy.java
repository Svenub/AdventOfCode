package FileReader.GroupStrategy;

import java.util.ArrayList;

public interface GroupStrategy {

    void group(Object data);

    ArrayList<Object> getOutPut();
}
