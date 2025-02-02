package di;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MyDIContainer {

	private Map<Class<?>, Object> beans = new HashMap<>();

    public MyDIContainer() {
        // 依存オブジェクトを手動で登録（実際のフレームワークでは自動スキャン）
        beans.put(HelloService.class, new HelloService());
    }

    public void injectDependencies(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = beans.get(fieldType);
                if (dependency != null) {
                    field.setAccessible(true);
                    try {
                        field.set(object, dependency);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to inject dependency", e);
                    }
                }
            }
        }
    }
}
