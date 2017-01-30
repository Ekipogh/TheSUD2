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
 * Created by ekipogh on 22.05.2015.
 * licensed under WTFPL
 */
public class Script implements Serializable {
    //private static Context context;
    private static ScriptableObject scope;
    private String scriptText;
    private static HashMap<String, String> fileScipts;
    public static final long serialVersionUID = -5573809983647994548L;

    public static Scriptable getScope() {
        return scope;
    }

   /* public static Context getContext() {
        return context;
    }
    */

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
        Context context = Context.enter();
        scope = context.initStandardObjects();
        fileScipts = new HashMap<>();
        Context.exit();
    }

    public static void initFunctions() {
        Context context = Context.enter();
        try {
            String functions = new Scanner(new File("lib/functions.js")).useDelimiter("\\Z").next();
            context.evaluateString(scope, functions, "functions.js", 1, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Context.exit();
    }

    public static Object run(String script, Object caller) {
        Context cx = Context.enter();
        if (caller != null) {
            setProperty("caller", caller);
        }
        //run file
        if (script.startsWith("file:")) {
            String scriptName = script.substring(script.indexOf(":") + 1);
            return cx.evaluateString(scope, fileScipts.get(scriptName), scriptName, 1, null);
        }
        //run string
        if (!script.isEmpty())
            return cx.evaluateString(scope, script, "<cmd>", 1, null);

        Context.exit();
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
