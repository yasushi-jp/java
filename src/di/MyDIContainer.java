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
        Class<?> clazz = object.getClass();							// 渡されたオブジェクトのクラス情報を取得
        for (Field field : clazz.getDeclaredFields()) {				// クラスのフィールドを取得
            if (field.isAnnotationPresent(MyAutowired.class)) {		// フィールドに @MyAutowired があるかチェック
                Class<?> fieldType = field.getType();				// フィールドの型を取得
                Object dependency = beans.get(fieldType);			// 依存オブジェクトを beans から取得
                if (dependency != null) {
                    field.setAccessible(true);						// private フィールドでもアクセスできるようにする
                    try {
                        field.set(object, dependency);				// フィールドにインスタンスをセット
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to inject dependency", e);
                    }
                }
            }
        }
    }
}