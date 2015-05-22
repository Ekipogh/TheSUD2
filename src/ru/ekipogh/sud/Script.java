package ru.ekipogh.sud;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

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

    public static void run(String script) {
        context.evaluateString(scope, script, "<cmd>", 1, null);
    }
}
