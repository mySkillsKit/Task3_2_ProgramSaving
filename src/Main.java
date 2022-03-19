import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        //Создать три экземпляра класса GameProgress.
        GameProgress oneGame = new GameProgress(60, 3, 9, 126.60);
        GameProgress twoGame = new GameProgress(85, 16, 14, 522.86);
        GameProgress threeGame = new GameProgress(30, 8, 12, 436.00);

        // Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
        saveGame("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/save1.dat",
                oneGame);
        saveGame("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/save2.dat",
                twoGame);
        saveGame("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/save3.dat",
                threeGame);

        //Созданные файлы сохранений из папки savegames запаковать в архив zip.
        ArrayList<String> listSaveGame = new ArrayList<>();
        listSaveGame.add("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/save1.dat");
        listSaveGame.add("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/save2.dat");
        listSaveGame.add("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/save3.dat");

        zipFiles("/Users/avas/IdeaProjects/Task3_1_ProgramInstallation/Games/savegames/zip.zip",
                listSaveGame);


        //Удалить файлы сохранений, лежащие вне архива.
        deleteSaveFile(listSaveGame);

    }

    private static void deleteSaveFile(ArrayList<String> listSaveGame) {
        for (String list : listSaveGame) {
            File fileSave = new File(list);
            if (fileSave.delete()) {
                System.out.println("File deleted " + list);
            }

        }
    }

    private static void zipFiles(String zipPath, ArrayList<String> listSaveGame) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (int i = 0; i < listSaveGame.size(); i++) {
                FileInputStream fis = new FileInputStream(listSaveGame.get(i));
                ZipEntry entry = new ZipEntry(listSaveGame.get(i));
                zout.putNextEntry(entry);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                // закрываем текущую запись для новой записи
                zout.closeEntry();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void saveGame(String dirPath, GameProgress numberGame) {
        // откроем выходной поток для записи в файл
        try (FileOutputStream fos = new FileOutputStream(dirPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(numberGame);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }
}
