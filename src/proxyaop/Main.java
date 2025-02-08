package proxyaop;

public class Main {
    public static void main(String[] args) {
    	// ServiceImplのインスタンスを作成
    	// （ServiceImplはServiceインターフェースを実装している）
        Service service = new ServiceImpl();
        // createProxyメソッドを呼び出して、AOP用の動的プロキシを作成
        Service proxyService = AOPHandler.createProxy(service, Service.class);	
        // プロキシ経由でexecute()を呼び出す
        // AOPHandler（InvocationHandler）のinvoke()が実行される
        proxyService.execute();	
    }
}
