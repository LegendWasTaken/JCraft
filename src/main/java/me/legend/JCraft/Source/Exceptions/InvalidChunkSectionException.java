package me.legend.JCraft.Source.Exceptions;

public class InvalidChunkSectionException extends Exception {

    int selection, min, max;

    public InvalidChunkSectionException(int selection, int min, int max){
        this.selection = selection;
        this.min = min;
        this.max = max;
    }

    public int getSelection() { return this.selection; }
    public int getMin() { return this.min; }
    public int getMax() { return this.max; }

}
