package meow.engine;

public class NameFactory {
    private long counter;

    public NameFactory() {
        counter = 0;
    }

    public String withPrefix(String prefix) {
        return prefix + (counter++);
    }

    public String node() {
        return "n$" + (counter++);
    }
}
