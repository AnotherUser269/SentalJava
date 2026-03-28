package configs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Field;

public class ConfigInjector {
    private final JsonNode root;
    private final ObjectMapper mapper = new ObjectMapper();

    public ConfigInjector(String filePath) throws Exception {
        root = mapper.readTree(new File(filePath));
    }

    public void inject(Object target) throws Exception {
        injectInto(target, target.getClass());
    }

    private void injectInto(Object target, Class<?> cls) throws Exception {
        for (Field field : cls.getDeclaredFields()) {
            ConfigProperty ann = field.getAnnotation(ConfigProperty.class);
            if (ann == null) continue;

            String prop = ann.propertyName().isEmpty()
                    ? cls.getSimpleName() + "." + field.getName()
                    : ann.propertyName();

            JsonNode node = findNode(prop);
            if (node == null || node.isNull()) continue;

            field.setAccessible(true);

            Class<?> ftype = field.getType();

            Object value;
            if (ftype.isArray()) {
                Class<?> comp = ftype.getComponentType();
                value = mapper.convertValue(node, mapper.getTypeFactory().constructArrayType(comp));
            } else if (ftype == String.class || ftype.isPrimitive() || isWrapper(ftype)) {
                value = mapper.convertValue(node, ftype);
            } else {
                value = mapper.treeToValue(node, ftype);
                if (value != null) injectInto(value, ftype);
            }

            if (value != null) field.set(target, value);
        }
    }

    private JsonNode findNode(String propName) {
        String[] parts = propName.split("\\.");
        JsonNode node = root;
        for (String p : parts) {
            if (node == null) return null;
            node = node.get(p);
        }
        return node;
    }

    private static boolean isWrapper(Class<?> c) {
        return c == Integer.class || c == Long.class || c == Short.class ||
                c == Byte.class || c == Float.class || c == Double.class ||
                c == Boolean.class || c == Character.class;
    }
}
