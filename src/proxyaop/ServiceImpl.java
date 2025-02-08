package proxyaop;

public class ServiceImpl implements Service {
    @Override
    @LogExecution
    public void execute() {
        System.out.println("Service is executing...");
    }
}