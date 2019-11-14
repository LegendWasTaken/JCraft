package me.legend.JCraft.Source.Exceptions;

public class InvalidSectionSizeException extends Exception {

    int size, expected;

    public InvalidSectionSizeException(int size, int expected){
        this.size = size;
        this.expected = expected;
    }

    public int getExpected() {
        return expected;
    }

    public int getSize() {
        return size;
    }
}
