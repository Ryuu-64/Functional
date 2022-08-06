package pers.ryuu.functional;

@FunctionalInterface
public interface IFunc4Arg<T1, T2, T3, T4, TResult> extends Unicast {
    TResult invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
}