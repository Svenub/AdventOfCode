package AoC2022.Puzzles.Day7;

import FileReader.GroupStrategy.GroupStrategy;
import FileReader.GroupStrategy.SpaceNewLine;
import FileReader.ReadLineStrategy;
import FileReader.ReadStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day7 {

    private static ReadStrategy fileReader = new ReadLineStrategy();
    private static GroupStrategy groupStrategy = new SpaceNewLine(false, false);

    private interface IDir {
        IDir cd_move_in(String cd);

        IDir cd_move_out();

        String getName();

        void setPreviousDir(IDir dir);

        void addDir(IDir dir);

        void addFile(File file);

        int getSize();

        Set<IDir> getDirsLessThan(Set<IDir> dirs, int size);

        Set<IDir> getDirsMoreThan(Set<IDir> dirs, int size);

        IDir getCurrentDir();

    }

    private static class Dir implements IDir {

        String name;
        int size = 0;
        IDir previousDir;
        ArrayList<IDir> content = new ArrayList<>();


        public Dir(String name) {
            this.name = name;
        }

        @Override
        public IDir cd_move_in(String cd) {
            for (IDir dir : content) {
                if (dir.getName().equals(cd)) {
                    dir.setPreviousDir(this);
                    return dir;
                }
            }
            return null;
        }

        @Override
        public IDir cd_move_out() {
            return this.previousDir;
        }


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setPreviousDir(IDir dir) {
            this.previousDir = dir;
        }


        @Override
        public void addDir(IDir dir) {
            content.add(dir);
        }

        @Override
        public void addFile(File file) {
            content.add(file);
        }

        @Override
        public int getSize() {
            int totalSize = 0;

            for (IDir dir : content) {
                totalSize += dir.getSize();
            }

            this.size = totalSize;
            return size;
        }

        @Override
        public Set<IDir> getDirsLessThan(Set<IDir> dirs, int size) {
            for (IDir dir : content) {
                if(dir.getSize() <= size && dir instanceof Dir){
                    dirs.add(dir.getCurrentDir());
                    dir.getDirsLessThan(dirs, size);
                } else {
                    dir.getDirsLessThan(dirs, size);
                }

            }
            return dirs;
        }

        @Override
        public Set<IDir> getDirsMoreThan(Set<IDir> dirs, int size) {
            for (IDir dir : content) {
                if(dir.getSize() >= size && dir instanceof Dir){
                    dirs.add(dir.getCurrentDir());
                    dir.getDirsMoreThan(dirs, size);
                } else {
                    dir.getDirsMoreThan(dirs, size);
                }

            }
            return dirs;
        }

        @Override
        public IDir getCurrentDir() {
            return this;
        }


        @Override
        public String toString() {
            getSize();
            return "{Dir " + name + ":" + ", content: " + content + ", dirSize: " + size + '}';
        }
    }

    private static class File implements IDir {
        String name;
        int size;
        IDir belongsToDir;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public void setBelongsToDir(IDir belongsToDir) {
            this.belongsToDir = belongsToDir;
        }

        @Override
        public IDir cd_move_in(String cd) {
            return null;
        }

        @Override
        public IDir cd_move_out() {
            return null;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setPreviousDir(IDir dir) {
        }


        @Override
        public void addDir(IDir dir) {
        }

        @Override
        public void addFile(File file) {
        }

        @Override
        public int getSize() {
            return this.size;
        }

        @Override
        public Set<IDir> getDirsLessThan(Set<IDir> dirs, int size) {
            if(belongsToDir.getSize() <= size){
                dirs.add(belongsToDir);
                return dirs;
            }
            return null;
        }

        @Override
        public Set<IDir> getDirsMoreThan(Set<IDir> dirs, int size) {
            if(belongsToDir.getSize() >= size){
                dirs.add(belongsToDir);
                return dirs;
            }
            return null;
        }

        @Override
        public IDir getCurrentDir() {
            return belongsToDir;
        }


        @Override
        public String toString() {
            return "File {" + "name: " + name + ", size=" + size + '}';
        }
    }

    private static boolean isCommand(String string) {
        for (String s : string.split(" ")) {
            if (s.equals("$")) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        fileReader.read(groupStrategy, "src/AoC2022/puzzles/Day7/input", false);
        IDir system = new Dir("/");
        IDir currentDir = system;
        for (int i = 1; i < fileReader.getInput().size(); i++) {
            String command = (String) fileReader.getInput().get(i);
            String[] commandSplit = command.split(" ");

            if (isCommand(command)) {
                if (command.matches("\\$ cd [a-z]+")) {
                    currentDir = currentDir.cd_move_in(commandSplit[2]);
                } else if (command.matches("\\$ cd ..")) {
                    currentDir = currentDir.cd_move_out();
                }

            } else {
                if (command.matches("dir [a-z]+")) {
                    currentDir.addDir(new Dir(commandSplit[1]));
                } else {
                    File file = new File(commandSplit[1], Integer.parseInt(commandSplit[0]));
                    file.setBelongsToDir(currentDir);
                    currentDir.addFile(file);
                }
            }
        }


        // --------------- Part 1 -----------------------
        Set<IDir> dirs1 = new HashSet<>();
        system.getDirsLessThan(dirs1, 100000);

        int sum = 0;
        for(IDir dir : dirs1){
            sum += dir.getSize();
        }
        System.out.println("Part 1: " +sum);


        // ---------------------- Part 2 ----------------

        int totalSize = 70000000;
        int updateSize = 30000000;
        int spaceNeeded = updateSize - (totalSize - system.getSize());

        Set<IDir> dirs2 = new HashSet<>();
        system.getDirsMoreThan(dirs2, spaceNeeded);

        int sum2 = 0;
        for(IDir dir : dirs2){
            if(sum2 == 0 || (dir.getSize() <= sum2 && dir.getSize() >= spaceNeeded)){
                sum2 = dir.getSize();
            }

        }
        System.out.println("Part 2: " + sum2);
    }
}
