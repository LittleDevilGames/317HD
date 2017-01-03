package rs.node.impl;

import rs.node.CacheLink;

public class OnDemandRequest extends CacheLink {

    public int archive;
    public int cycle;
    public int file;
    public boolean immediate;
    public byte payload[];

    public OnDemandRequest() {
        immediate = true;
    }

    @Override
    public String toString() {
        return "[Request: Archive " + archive + ", File" + file + "])";
    }
}
