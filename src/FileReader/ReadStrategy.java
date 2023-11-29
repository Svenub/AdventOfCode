package FileReader;

import FileReader.GroupStrategy.GroupStrategy;
import java.util.ArrayList;

public interface ReadStrategy {

    void read(GroupStrategy groupStrategy, String pathname, boolean parseAsInt);

    ArrayList<Object> getInput();
    ArrayList<Object> getOutput();
}
