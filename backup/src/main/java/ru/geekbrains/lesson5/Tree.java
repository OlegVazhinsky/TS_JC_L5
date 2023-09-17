package ru.geekbrains.lesson5;

import java.io.File;

public class Tree {

    public static void main(String[] args) {

        print(new File("."), "", true);

    }

    /**
     * TODO: Доработать метод print, необходимо распечатывать директории и файлы
     * @param file
     * @param indent
     * @param isLast
     */
    public static void print(File file, String indent, boolean isLast){
        System.out.print(indent);
        if (isLast){
            System.out.print("└─");
            indent += "  ";
        }
        else{
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if (files == null)
            return;

        int subDirTotal = 0;
        int subFileTotal = 0;
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory())
                subDirTotal++;
            else
                subFileTotal++;
        }

        int subDirCounter = 0;
        int subFileCounter = 0;
        // fist print all directories
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory()){
                subDirCounter++;
                print(files[i], indent, subDirCounter == subDirTotal);
            }
        }
        // after that print all files that are included
        for (int i = 0; i < files.length; i++){
            if (files[i].isFile()){
                subFileCounter++;
                print(files[i], indent, subFileCounter == subFileTotal);
            }
        }

    }

}
