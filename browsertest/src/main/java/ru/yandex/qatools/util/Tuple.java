package ru.yandex.qatools.util;

/**
 *
 */
public class Tuple {

    public static <A, B, C> Tuple3<A, B, C> tuple(A a, B b, C c){
        return new Tuple3<A, B, C>(a, b, c);
    }

}
