package ru.ekipogh.sud;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by dedov_d on 22.05.2015.
 */
public class Script implements Serializable {
    private static Context context;
    private static Scriptable scope;
    private String scriptText;
    private static HashMap<String, String> fileScipts;
    public static final long serialVersionUID = -5573809983647994548L;

    public static Scriptable getScope() {
        return scope;
    }

    public static Context getContext() {
        return context;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private boolean enabled;

    public Script(String text, boolean enabled) {
        this.scriptText = text;
        this.enabled = enabled;
    }

    public static void setProperty(String property, Object object) {
        Object wrappedOut = Context.javaToJS(object, scope);
        ScriptableObject.putProperty(scope, property, wrappedOut);
    }

    public static void init() {
        context = Context.enter();
        scope = context.initStandardObjects();
        fileScipts = new HashMap<>();
    }

    public static void initFunctions() {
        try {
            String functions = new Scanner(new File("lib/functions.js")).useDelimiter("\\Z").next();
            context.evaluateString(scope, functions, "functions.js", 1, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Object run(String script, Object caller) {
        if (caller != null) {
            setProperty("caller", caller);
        }
        //run file
        if (script.startsWith("file:")) {
            String scriptName = script.substring(script.indexOf(":") + 1);
            return context.evaluateString(scope, fileScipts.get(scriptName), scriptName, 1, null);
        }
        //run string
        if (!script.isEmpty())
            return context.evaluateString(scope, script, "<cmd>", 1, null);
        return null;
    }

    public String getText() {
        return scriptText;
    }

    public static void set(Map<String, Object> scopeObjects) {
        for (Map.Entry<String, Object> e : scopeObjects.entrySet()) {
            setProperty(e.getKey(), e.getValue());
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static void addScriptFile(String scriptName, String scriptText) {
        fileScipts.put(scriptName, scriptText);
    }
}
