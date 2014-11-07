package pl.bazy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static pl.bazy.Settings.INPUT_BUFFER_SIZE;
import static pl.bazy.Settings.OUTPUT_BUFFER_SIZE;

/**
 * Created by Art on 2014-11-07.
 */
public class Tape {

    private File file;

    private InputBuffer inputBuffer;

    private OutputBuffer outputBuffer;

    public Tape(File file,int inputBufferSize,int outputBufferSize) {
        this.file = file;

        this.inputBuffer = new InputBuffer(this.file,inputBufferSize);

        this.outputBuffer = new OutputBuffer(this.file,outputBufferSize);
    }

    public Tape(File file){
        this(file,INPUT_BUFFER_SIZE,OUTPUT_BUFFER_SIZE);
    }

    //* Getters and setters*//

    public InputBuffer getInputBuffer() {
        return inputBuffer;
    }

    public void setInputBuffer(InputBuffer inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public OutputBuffer getOutputBuffer() {
        return outputBuffer;
    }

    public void setOutputBuffer(OutputBuffer outputBuffer) {
        this.outputBuffer = outputBuffer;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getCounter(){
        return inputBuffer.getCounter()+outputBuffer.getCounter();
    }

    public void clearConuter(){
        inputBuffer.setCounter(0);
        outputBuffer.setCounter(0);
    }

    public String writeFile(){
        String content = null;
        try {
            content = new Scanner(file).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(content);
        return content;
    }
}
