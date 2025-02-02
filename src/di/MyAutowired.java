package di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)		// 実行時にアノテーションを保持
@Target(ElementType.FIELD)				// フィールドに適用
public @interface MyAutowired {
}