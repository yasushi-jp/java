package di;

public class MyController {
	@MyAutowired
	private HelloService helloService;	// 自動 DI

	public void execute() {
		helloService.sayHello();		// HelloService のメソッドを実行
	}
}