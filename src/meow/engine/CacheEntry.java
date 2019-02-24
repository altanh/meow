package meow.engine;

public final class CacheEntry {
    public final String id;
    public final String expr;

    public CacheEntry(String id, String expr) {
        this.id = id;
        this.expr = expr;
    }
}
