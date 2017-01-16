package ru.ekipogh.sud;

import java.io.Serializable;

/**
 * Created by dedov on 16.01.2017.
 * licensed under WTFPL
 */
public class SudTimer implements Runnable, Serializable {
    public static final long serialVersionUID = 1L;
    private String name;
    private int step;
    private Script script;
    private boolean isRunning;

    public SudTimer() {
        script = new Script("", true);
        isRunning = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(String scriptText) {
        this.script = new Script(scriptText, true);
    }

    public void start() {
        this.isRunning = true;
        this.run();
    }

    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Script.run(this.script.getText(), null);
                wait(step * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
