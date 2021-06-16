

import org.apache.commons.io.FileUtils;

import java.io.*;

import java.nio.file.*;
import java.util.List;

/*
CREATED BY JAKUB HAŁAS
 */


class Main {


    public static void main(String[] args) throws IOException, InterruptedException {


        Path pdfPath = Path.of("C:\\Users\\user\\Desktop\\pdf\\");
        Path zipPath = Path.of("C:\\Users\\user\\Desktop\\zipp\\");
        Path path = Path.of("C:\\Users\\user\\Desktop\\home\\");

        Files.createDirectory(pdfPath);
        Files.createDirectory(zipPath);
        Files.createDirectory(path);

        File zipDirectory = new File(String.valueOf(zipPath));
        File pdfDirectory = new File(String.valueOf(pdfPath));

        File dir = new File("C:\\Users\\user\\Desktop\\home\\");

        String[] extensions = new String[]{ "pdf", "zip"};
        int pdfCounter = 0;




        do {
            try {
                WatchService watcher = path.getFileSystem().newWatchService();
                path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
                System.out.println("Monitoring directory for changes...");
                WatchKey watchKey = watcher.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();

                for (WatchEvent event : events) {

                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
                        for (File file : files) {
                            if (file.getAbsolutePath().endsWith("pdf")) {
                                file.renameTo(new File(String.valueOf(pdfDirectory), file.getName()));
                                pdfCounter++;
                                System.out.println("completed pdf" + pdfCounter);
                            }
                        }


                        for (File file : files) {
                            if (file.getAbsolutePath().endsWith("zip")) {
                                file.renameTo(new File(String.valueOf(zipDirectory), file.getName()));
                                System.out.println("completed txt");
                            }
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);

    }
}

