package Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CustomConfig {
    private final String fileName;
    private final String folderName;
    private File file;
    private Map<String, Object> config;
    private final Yaml yaml;

    public CustomConfig(String fileName, String folderName) {
        this.fileName = fileName;
        this.folderName = folderName;

        // YAML format settings
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);

        registerConfig();
    }

    private void registerConfig() {
        File folder = new File(folderName);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Could not create configuration folder: " + folderName);
        }

        file = new File(folder, fileName);
        if (!file.exists()) {
            try (InputStream defaultConfig = getClass().getResourceAsStream("/" + fileName)) {
                if (defaultConfig != null) {
                    copyDefaultConfig(defaultConfig);
                } else {
                    createEmptyConfig();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reloadConfig();
    }

    private void copyDefaultConfig(InputStream defaultConfig) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = defaultConfig.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private void createEmptyConfig() {
        try {
            if (!file.createNewFile()) {
                throw new IOException("Could not create configuration file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        try (InputStream inputStream = new FileInputStream(file)) {
            config = yaml.load(inputStream);
            if (config == null) {
                config = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YAMLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try (Writer writer = new FileWriter(file)) {
            yaml.dump(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a value using dot notation for nested keys.
     *
     * @param key the key in dot notation (e.g., "config.suggestion.add.suggestion_channel_id")
     * @return the value, or null if the key does not exist
     */
    public Object get(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = config;
        Object value = null;

        for (int i = 0; i < keys.length; i++) {
            value = currentMap.get(keys[i]);
            if (i < keys.length - 1) {
                if (value instanceof Map) {
                    currentMap = (Map<String, Object>) value;
                } else {
                    return null; // The chain is broken, key does not exist
                }
            }
        }
        return value;
    }

    public void set(String key, Object value) {
        String[] keys = key.split("\\.");
        Map<String, Object> currentMap = config;

        for (int i = 0; i < keys.length - 1; i++) {
            Object nested = currentMap.get(keys[i]);
            if (!(nested instanceof Map)) {
                nested = new HashMap<>();
                currentMap.put(keys[i], nested);
            }
            currentMap = (Map<String, Object>) nested;
        }

        currentMap.put(keys[keys.length - 1], value);
        saveConfig();
    }

    public Map<String, Object> getAll() {
        return config;
    }
}