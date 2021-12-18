package ru.filit.mdma.dm.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class YamlUtils {
    @SneakyThrows
    public <T> Collection<T> parse(String path, Function<Map<String, String>, T> function) {
        Yaml yaml = new Yaml();
        var resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        var reader = new InputStreamReader(resource.getInputStream());

        ArrayList<Map<String, String>> objects = yaml.load(reader);

        var result = new ArrayList<T>();
        for (var map : objects) {
            result.add(function.apply(map));
        }
        return result;
    }
}
