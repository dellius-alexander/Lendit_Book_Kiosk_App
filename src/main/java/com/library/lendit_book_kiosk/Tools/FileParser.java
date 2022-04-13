package com.library.lendit_book_kiosk.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public
class FileParser<T extends File> implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(FileParser.class);

    private T previous = null;
    private T next = null;
    public FileParser(T file){
        this.next = file;
        this.previous = null;
    }
    public FileParser(T previous, T next) {
        this.previous = previous;
        this.next = next;
    }
    public FileParser() {
    }

    public T getPrevious() {
        return this.previous;
    }

    public T getNext() {
        return this.next;
    }

    public void setPrevious(T previous) {
        this.previous = previous;
    }

    public void setNext(T next) {
        this.next = next;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FileParser)) return false;
        final FileParser<?> other = (FileParser<?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$previous = this.getPrevious();
        final Object other$previous = other.getPrevious();
        if (!Objects.equals(this$previous, other$previous)) return false;
        final Object this$next = this.getNext();
        final Object other$next = other.getNext();
        if (!Objects.equals(this$next, other$next)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FileParser;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $previous = this.getPrevious();
        result = result * PRIME + ($previous == null ? 43 : $previous.hashCode());
        final Object $next = this.getNext();
        result = result * PRIME + ($next == null ? 43 : $next.hashCode());
        return result;
    }

    public String toString() {
        return "FileParser(\nprevious=" + this.getPrevious() + ", next=" + this.getNext() + "\n)";
    }
}

