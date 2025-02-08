package proxyaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 実行時までアノテーションを保持
@Target(ElementType.METHOD)         // メソッドに適用可能
public @interface LogExecution{
}
