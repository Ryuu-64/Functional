package pers.ryuu.functional;

@FunctionalInterface
public interface IFunc3Arg<T1, T2, T3, TResult> extends Unicast {
    TResult invoke(T1 arg1, T2 arg2, T3 arg3);
}