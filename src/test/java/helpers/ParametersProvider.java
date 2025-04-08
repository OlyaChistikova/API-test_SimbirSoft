package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public final class ParametersProvider {
    /**
     * Список параметров
     */
    private ArrayList<Properties> propertiesList = new ArrayList<>();

    /**
     * Получить название конфигурационного файла из system properties.
     * @return список название конфигурационных файлов
     */
    private static ArrayList<String> getConfigFileNames(){
        ArrayList<String> configFileNames = new ArrayList<>();
        for (String key: System.getProperties().stringPropertyNames()){
            if (key.startsWith("config.location")){
                String[] fileNames = System.getProperties().getProperty(key)
                        .split(";");
                configFileNames.addAll(Arrays.asList(fileNames));
            }
        }
        return configFileNames;
    }

    /**
     * helpers.ParametersProvider конструктор
     * @throws IOException когда невозможно получить конфиги
     */
    private ParametersProvider() throws IOException{
        loadProperties("src/test/resources/env_local.xml");

        ArrayList<String> configFileNames = getConfigFileNames();
        for (String fileName: configFileNames){
            loadProperties(fileName);
        }
    }

    /**
     * Загружает свойства из указанного XML файла и добавляет их
     * в список параметров.
     *
     * @param fileName имя файла конфигурации для загрузки
     * @throws IOException если происходит ошибка ввода-вывода при загрузке файла
     */
    private void loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream(fileName));
        propertiesList.add(properties);
    }

    /**
     * helpers.ParametersProvider instance holder
     */
    private static ParametersProvider instance;

    /**
     * helpers.ParametersProvider геттер
     * @return helpers.ParametersProvider instance
     * @throws IOException когда невозможно получить конфиги
     */
    private static ParametersProvider getInstance() throws IOException{
        if (instance == null){
            instance = new ParametersProvider();
        }
        return instance;
    }

    public static String getProperty(final String key) throws IOException{
        for (Properties properties: getInstance().propertiesList){
            String result = properties.getProperty(key, null);
            if (result != null){
                return result;
            }
        }
        return "";
    }
}