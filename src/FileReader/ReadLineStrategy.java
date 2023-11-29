package FileReader;

import FileReader.GroupStrategy.GroupStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadLineStrategy implements ReadStrategy {

    private GroupStrategy groupStrategy;

    ArrayList <Object> input = new ArrayList<>();

    @Override
    public void read(GroupStrategy groupStrategy, String pathname, boolean parseAsInt) {
        this.groupStrategy = groupStrategy;

        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();

                if(parseAsInt){
                    try{
                        int number = Integer.parseInt(data);
                        input.add(number);
                        groupStrategy.group(number);
                    }
                    catch (NumberFormatException ignored){
                        groupStrategy.group(data);
                    }
                } else {
                    input.add(data);
                    groupStrategy.group(data);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
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
