package ru.ekipogh.sud;

import java.io.Serializable;

/**
 * Created by dedov on 16.01.2017.
 * licensed under WTFPL
 */
public class SudTimer extends Thread implements Serializable {
    public static final long serialVersionUID = 1L;
    private String timerName;
    private int step;
    private String script;
    private boolean isRunning;

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            try {
                Script.run(this.script, null);
                sleep(step * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public String toString() {
        return "Timer[ " + getTimerName() + " ]";
    }
}
