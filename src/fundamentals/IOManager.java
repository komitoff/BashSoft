package fundamentals;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.LinkedList;

public class IOManager {

    public static void main(String[] args) {
        traverseDirectory("D:\\SoftUni\\BashSoft\\BashSoft");
    }

    public static void traverseDirectory(String path) {
        LinkedList<File> subFolders = new LinkedList<>();
        File root = new File(path);

        subFolders.add(root);

        while (subFolders.size() != 0) {
            try {
                File currentFolder = subFolders.removeFirst();

                if (currentFolder.listFiles() != null) {
                    for (File file : currentFolder.listFiles()) {
                        if (file.isDirectory()) {
                            subFolders.add(file);
                        }
                        else {
                            OutputWriter.writeMessageOnNewLine(file.toString());
                        }
                    }
                }


            }
            catch (Exception e) {
                OutputWriter.displayException(e.getMessage());
            }


        }
    }
}
