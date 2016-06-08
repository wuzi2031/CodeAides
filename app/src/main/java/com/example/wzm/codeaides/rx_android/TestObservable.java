package com.example.wzm.codeaides.rx_android;

/**
 * Created by wzm on 2016/6/8.
 */
public class TestObservable {
    public interface Fun {
        String call(String string);
    }

    public interface Action {
        void call(String string);
    }

    private static String s;
    private static TestObservable testObservable = new TestObservable();

    public static TestObservable from(String string) {
        s = string;
        return testObservable;
    }

    public static TestObservable map(Fun fun) {
        if (s == null || s.length() == 0)
            return null;
        s = fun.call(s);
        return testObservable;
    }

    public static TestObservable subscribe(Action action) {
        action.call(s);
        return testObservable;
    }
}
