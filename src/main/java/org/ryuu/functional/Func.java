package org.ryuu.functional;

public final class Func<TResult>
        extends Multicast<IFunc<TResult>>
        implements IFunc<TResult> {
    @Override
    public TResult invoke() {
        TResult result = null;
        for (IFunc<TResult> functional : this) {
            result = functional.invoke();
        }
        return result;
    }
}