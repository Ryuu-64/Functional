package pers.ryuu.event;

import java.util.EventListener;

public interface IFunc1Arg<T1, TResult> extends EventListener {
    TResult invoke(T1 arg1);
}