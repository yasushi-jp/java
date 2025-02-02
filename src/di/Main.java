package di;

public class Main {
	public static void main(String[] args) {
		MyDIContainer container = new MyDIContainer();		// DI コンテナを作成
		MyController controller = new MyController();	// コントローラを作成
		
		container.injectDependencies(controller);			// 依存関係を注入

		controller.execute(); 								// 実行
	}
}