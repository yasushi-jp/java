package di;

public class HelloController {
	@MyAutowired
	private HelloService helloService;

	public void execute() {
		helloService.sayHello();
	}
}
