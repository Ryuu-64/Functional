package pers.ryuu.functional_interface;

import java.util.EventListener;

public interface IFunc5Arg<T1, T2, T3, T4, T5, TResult> extends EventListener {
    TResult invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
}