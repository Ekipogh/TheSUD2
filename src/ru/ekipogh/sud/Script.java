package ru.ekipogh.sud;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by dedov_d on 22.05.2015.
 */
public class Script implements Serializable {
    private static Context context;
    private static Scriptable scope;
    private String scriptText;

    public boolean isEnabled() {
        return enabled;
    }

    private boolean enabled;

    public Script(String text, boolean enabled) {
        this.scriptText = text;
        this.enabled = enabled;
    }

    /*public Object run(Object caller) {
        if (enabled)
            return Script.run(scriptText, caller);
        return null;
    }*/

    public static void setProperty(String property, Object object) {
        Object wrappedOut = Context.javaToJS(object, scope);
        ScriptableObject.putProperty(scope, property, wrappedOut);
    }

    public static void init() {
        context = Context.enter();
        scope = context.initStandardObjects();
    }

    public static void initFunctions() {
        try {
            String functions = new Scanner(new File("src/lib/functions.js")).useDelimiter("\\Z").next();
            context.evaluateString(scope, functions, "functions.js", 1, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Object run(String script, Object caller) {
        setProperty("caller", caller);
        if (!script.isEmpty())
            return context.evaluateString(scope, script, "<cmd>", 1, null);
        return null;
    }

    public String getText() {
        return scriptText;
    }
}
