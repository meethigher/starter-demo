package top.meethigher.starter.importbean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * 配合spring.factories实现自动装配
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/6/5 00:49
 */
@Configuration
@Import(ImportSelectorImpl.class)
public class AutoImportConfig {
}

/**
 * 导入
 *
 * @author chenchuancheng github.com/meethigher
 * @since 2023/6/5 00:45
 */
class ImportSelectorImpl implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        ResourceBundle rb = ResourceBundle.getBundle("importSelector");
        String serviceImplItems = rb.getString("serviceImplClass");
        String[] serviceImplArr = serviceImplItems.split(",");
        return serviceImplArr;
    }

    public String[] mergeArrays(String[] arr1, String[] arr2, String[] arr3) {
        String[] result = new String[arr1.length + arr2.length + arr3.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        System.arraycopy(arr3, 0, result, arr1.length + arr2.length, arr3.length);
        return result;
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return ImportSelector.super.getExclusionFilter();
    }
}
