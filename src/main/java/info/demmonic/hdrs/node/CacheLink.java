package info.demmonic.hdrs.node;

public class CacheLink extends Link {

    public static final CacheLink EMPTY = new CacheLink();

    public CacheLink nextCache;
    public CacheLink previousCache;

    public void uncache() {
        if (this.previousCache != null) {
            this.previousCache.nextCache = this.nextCache;
            this.nextCache.previousCache = this.previousCache;
            this.nextCache = null;
            this.previousCache = null;
        }
    }

}
