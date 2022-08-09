package org.ryuu.functional;

@FunctionalInterface
public interface IFunc<TResult> extends Unicast {
    TResult invoke();
}