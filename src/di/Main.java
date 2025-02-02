package di;

public class Main {
	public static void main(String[] args) {
		MyDIContainer container = new MyDIContainer();

		HelloController controller = new HelloController();
		container.injectDependencies(controller);

		controller.execute(); // "Hello, Custom DI!" と出力
	}
}
