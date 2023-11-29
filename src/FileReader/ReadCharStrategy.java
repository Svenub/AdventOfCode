package FileReader;



import FileReader.GroupStrategy.GroupStrategy;

import java.io.*;
import java.util.ArrayList;

public class ReadCharStrategy implements ReadStrategy {

    ArrayList<Object> input = new ArrayList<>();
    GroupStrategy groupStrategy;

    @Override
    public void read(GroupStrategy groupStrategy, String pathname, boolean parseAsInt) {
        this.groupStrategy = groupStrategy;

        try {
            File file = new File(pathname);
            BufferedReader br = new BufferedReader(new FileReader(file));

            int c;

            while ((c = br.read()) != -1) {
                char character = (char) c;

                if (parseAsInt) {
                    try {
                        int number = Integer.parseInt(String.valueOf(character));
                        input.add(number);
                        groupStrategy.group(number);
                    } catch (NumberFormatException ignored) {
                    }
                } else {
                    input.add(character);
                    groupStrategy.group(character);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Object> getInput() {
        return input;
    }

    @Override
    public ArrayList<Object> getOutput() {
        return groupStrategy.getOutPut();
    }
}
