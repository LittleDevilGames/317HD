package info.demmonic.hdrs.node;

public class Link {

    public long identity;
    public Link next;
    public Link previous;

    public void detach() {
        if (this.previous != null) {
            this.previous.next = this.next;
            this.next.previous = this.previous;
            this.next = null;
            this.previous = null;
        }
    }

}
