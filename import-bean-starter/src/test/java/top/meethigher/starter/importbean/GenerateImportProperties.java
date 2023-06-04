package top.meethigher.starter.importbean;

import org.junit.Test;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * 生成import Bean配置文件
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/6/5 00:35
 */
public class GenerateImportProperties {

    private final static String serviceImplPath;

    static {

        String serviceImplRoot = "/target/classes/top/meethigher/starter/importbean/impl";
        String root = System.getProperty("user.dir").replace("\\", "/");
        serviceImplPath = root + serviceImplRoot;
    }


    @Test
    public void generateImportProperties() throws Exception {
        String serviceImplPackageName = "top.meethigher.starter.importbean.impl";
        String serviceImplClazz = "serviceImplClass=";
        List<Class<?>> serviceImplClasses = walkDir(serviceImplPath, serviceImplPackageName);

        StringBuilder sb = new StringBuilder();
        sb.append(generateConfig(serviceImplClazz, serviceImplClasses));
        System.out.println(sb.toString());
    }

    private List<Class<?>> walkDir(String root, String packageName) throws Exception {
        List<Class<?>> list = new LinkedList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(root), "*.class")) {
            for (Path path : stream) {
                String className = packageName + "." + path.getFileName().toString().replace(".class", "");
                Class<?> clazz = Class.forName(className);
                list.add(clazz);
            }
        }
        return list;
    }

    private String generateConfig(String prefix, List<Class<?>> classes) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (int i = 0; i < classes.size(); i++) {
            Class<?> clazz = classes.get(i);
            if (i == classes.size() - 1) {
                sb.append(clazz.getName())
                        .append("\n");
            } else {
                sb.append(clazz.getName())
                        .append(",")
                        .append("\\")
                        .append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }


}