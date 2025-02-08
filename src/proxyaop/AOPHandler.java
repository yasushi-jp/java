package proxyaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AOPHandler implements InvocationHandler {

	private final Object target;
	
	public AOPHandler(Object target) {
        this.target = target;
    }
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 実装クラスのメソッドを取得
		Method implMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
		
		// メソッドに @LogExecution アノテーションが付いているかチェック
        if (implMethod.isAnnotationPresent(LogExecution.class)) {
            // メソッド実行前のログ出力
        	System.out.println("[LOG] Method " + method.getName() + " is starting...");
            // メソッドを実行
        	Object result = method.invoke(target, args);
            // メソッド実行後のログ出力
            System.out.println("[LOG] Method " + method.getName() + " has finished.");
            return result;
        } else {
        	// メソッドを実行
            return method.invoke(target, args);
        }
	}
	
	@SuppressWarnings("unchecked")	// 型キャストの警告を抑制
    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(	// 動的プロキシを作成（実行時にインターフェースを実装）
            interfaceType.getClassLoader(),	// クラスローダーを設定（プロキシクラスのロードに必要）
            new Class<?>[]{interfaceType},	// 対象のインターフェースを指定
            new AOPHandler(target)			// メソッド呼び出しをフックし、AOPを適用
        );
    }
}
