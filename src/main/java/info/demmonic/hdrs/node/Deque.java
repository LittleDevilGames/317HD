package info.demmonic.hdrs.node;

public class Deque {

    public CacheLink current;
    public CacheLink first;

    public Deque() {
        this.first = new CacheLink();
        this.first.nextCache = this.first;
        this.first.previousCache = this.first;
    }

    public int count() {
        int i = 0;
        for (CacheLink n = first.nextCache; n != first; n = n.nextCache) {
            i++;
        }
        return i;
    }

    public CacheLink next() {
        CacheLink c = current;
        if (c == first) {
            current = null;
            return null;
        }
        current = c.nextCache;
        return c;
    }

    public CacheLink pop() {
        CacheLink c = first.nextCache;
        if (c == first) {
            return null;
        }
        c.uncache();
        return c;
    }

    public void push(CacheLink c) {
        c.uncache();
        c.previousCache = this.first.previousCache;
        c.nextCache = this.first;
        c.previousCache.nextCache = c;
        c.nextCache.previousCache = c;
    }

    public CacheLink top() {
        CacheLink c = first.nextCache;
        if (c == first) {
            current = null;
            return null;
        }
        current = c.nextCache;
        return c;
    }
}
