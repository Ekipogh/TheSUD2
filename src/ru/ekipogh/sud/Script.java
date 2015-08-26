package ru.ekipogh.sud;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by dedov_d on 22.05.2015.
 */
public class Script {
    private static Context context;
    private static Scriptable scope;

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

    public static void run(String script, Object caller) {
        setProperty("caller", caller);
        if (!script.isEmpty())
            context.evaluateString(scope, script, "<cmd>", 1, null);
    }
}
