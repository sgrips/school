package it.brt.helloworld.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SearchUtils {

    final static List<String> SUFFIXES = List.of(
            "_eq",
            "_ieq",
            "_like",
            "_ilike",
            "_startsWith",
            "_istartsWith",
            "_endsWith",
            "_iendsWith"
    );

    final static List<String> FILTERS = List.of(
            "_limit",
            "_offset"
    );

    public static Integer loadLimit(Map<String, String> filters) {
        return filters.entrySet().stream().filter(e -> e.getKey().equals("_limit"))
                .filter(e -> e.getValue() != null && Integer.class.isAssignableFrom(e.getValue().getClass())).map(e -> Integer.valueOf(e.getValue())).findAny().orElse(-1);
    }

    public static Integer loadOffset(Map<String, String> filters) {
        return filters.entrySet().stream().filter(e -> e.getKey().equals("_offset"))
                .filter(e -> e.getValue() != null && isInteger(e.getValue())).map(e -> Integer.valueOf(e.getValue())).findAny().orElse(-1);
    }

    private static boolean isInteger(String str) {

        try {
            Integer.parseInt(str);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public static <T> ExampleMatcher loadMatcher(Map<String, String> filters, T obj, Class<T> clazz) {

        ExampleMatcher em = ExampleMatcher.matchingAll();
        filters.entrySet().stream().distinct().forEach(e -> {

            String key = loadKeyFilter(e.getKey(), clazz);
            PropertyAccessor pa = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            pa.setPropertyValue(key, e.getValue());

            if (key != null) {
                GenericPropertyMatcher mc = propertyMatcher(loadSuffix(key));
                em.withMatcher(key, mc);
            }

        });

        return em;

    }

    private static String loadKeyFilter(String key, Class<?> clazz) {
        String field = key.substring(0, key.indexOf("_"));
        PropertyDescriptor pd = Stream.of(BeanUtils.getPropertyDescriptors(clazz)).filter(p -> p.getName().equals(field)).findAny().orElse(null);

        try  {
            return pd != null && clazz.getDeclaredField(pd.getName()).isAnnotationPresent(Searchable.class) ? field : null;

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    private static String loadSuffix(String key) {
        String suffix = key.substring(key.indexOf("_") + 1);
        return SUFFIXES.contains(suffix) ? suffix : "_eq";
    }

    private static GenericPropertyMatcher propertyMatcher(String suffix) {

        switch (suffix) {
            case "_eq" -> { return ExampleMatcher.GenericPropertyMatchers.exact(); }
            case "_ieq" -> { return ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase(); }
            case "_like" -> { return ExampleMatcher.GenericPropertyMatchers.contains(); }
            case "_ilike" -> { return ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase(); }
            case "_startsWith" -> { return ExampleMatcher.GenericPropertyMatchers.startsWith(); }
            case "_istartsWith" -> { return ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase(); }
            case "_endsWith" -> { return ExampleMatcher.GenericPropertyMatchers.endsWith(); }
            case "_iendsWith" -> { return ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase(); }
            default -> throw new IllegalArgumentException();
        }
    }
}
