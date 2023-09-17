package ru.geekbrains.lesson5;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Program {
    public static void main(String[] args) throws IOException {
        deleteFolder("./backup");
        backup(".", "./backup");
    }

    /**
     * Method checks if folder is empty.
     * @param folder "path" to folder.
     * @return "true" if folder is empty, "false" otherwise.
     * @throws IOException
     */
    public static boolean isFolderEmpty(String folder) throws IOException {
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(Path.of(folder))) {
            return !dirStream.iterator().hasNext();
        }
    }

    /**
     * Method deletes folder.
     * @param folder "path" to folder.
     * @throws IOException
     */
    public static void deleteFolder(String folder) throws IOException {
        File deletingFolder = new File(folder);
        while (deletingFolder.exists()) {
            File[] files = new File(folder).listFiles();
            if (files.length != 0) {
                for (File file: files) {
                    if (file.isDirectory()) {
                        if (isFolderEmpty(file.getPath())) {
                            Files.delete(file.toPath());
                            System.out.println("Folder " + file.getPath() + " deleted successfully.");
                        } else {
                            deleteFolder(file.getPath());
                        }
                    }
                    else {
                        Files.delete(file.toPath());
                        System.out.println("File " + file.getPath() + " deleted successfully.");
                    }
                }
            } else {
                Files.delete(deletingFolder.toPath());
            }
        }
    }

    /**
     * Method makes "backup" from "sourceFolder" to "targetFolder".
     * @param sourceFolder "path" from where to make backup.
     * @param targetFolder "path" where to make backup.
     * @throws IOException
     */
    public static void backup(String sourceFolder, String targetFolder) throws IOException {
        File[] files = new File(sourceFolder).listFiles();
        File target = new File(targetFolder);
        if(!target.exists()){
            target.mkdir();
        }
        for (File file: files) {
            System.out.println("Coping " + file.getPath() + " to " + targetFolder + "\\" + file.getName() + ".");
            Files.copy(Path.of(file.getCanonicalPath()),
                    Path.of(targetFolder,file.getName()),
                    StandardCopyOption.REPLACE_EXISTING);
            if (file.isDirectory()) {
                backup(file.getPath(), (target.getPath() + "\\" + file.getName()));
            }
        }
    }
}
