package pers.ryuu.functional_interface;

import java.util.EventListener;

public interface IAction4Arg<T1, T2, T3, T4> extends IFunctionalInterface {
    void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
}