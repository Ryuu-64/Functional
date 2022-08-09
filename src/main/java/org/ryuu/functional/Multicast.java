package org.ryuu.functional;

import java.util.ArrayList;
import java.util.List;

public class Multicast<T extends Unicast> implements Unicast, Iterable<T> {

    public class Iterator implements java.util.Iterator<T> {
        private int cursor;

        @Override
        public boolean hasNext() {
            return this.cursor != unicastList.size();
        }

        @Override
        public T next() {
            T next = unicastList.get(cursor);
            cursor++;
            return next;
        }

        public void reset() {
            cursor = 0;
        }
    }

    protected final List<T> unicastList = new ArrayList<>();

    protected Iterator iterator = new Iterator();

    @Override
    public java.util.Iterator<T> iterator() {
        iterator.reset();
        return iterator;
    }

    @SuppressWarnings("unchecked")
    public final boolean add(T functional) {
        if (functional == null) {
            return false;
        } else if (functional instanceof Multicast) {
            return addMulticast((Multicast<T>) functional);
        } else /* if (functional instanceof Unicast) */ {
            return addUnicast(functional);
        }
    }

    @SuppressWarnings("unchecked")
    public final boolean remove(T functional) {
        if (functional == null) {
            return false;
        } else if (functional instanceof Multicast) {
            return removeMulticast((Multicast<T>) functional);
        } else /* if (functional instanceof Unicast) */ {
            return removeUnicast(functional);
        }
    }

    @SuppressWarnings("unchecked")
    public final boolean contains(T functional) {
        if (functional == null) {
            return false;
        } else if (functional instanceof Multicast) {
            return containsMulticast((Multicast<T>) functional);
        } else /* if (functional instanceof Unicast) */ {
            return containsUnicast(functional);
        }
    }

    public final void clear() {
        unicastList.clear();
        iterator.reset();
    }

    public final int count() {
        return unicastList.size();
    }

    public List<T> getUnicastList() {
        return new ArrayList<>(unicastList);
    }

    private boolean addUnicast(T unicast) {
        return unicastList.add(unicast);
    }

    private boolean addMulticast(Multicast<T> multicast) {
        boolean isAdd = false;
        for (T unicast : multicast.unicastList) {
            isAdd = addUnicast(unicast);
        }
        return isAdd;
    }

    private boolean removeUnicast(T unicast) {
        int index = unicastList.indexOf(unicast);
        if (index == -1) {
            return false;
        } else {
            if (index < iterator.cursor) {
                iterator.cursor--;
            }
            return unicastList.remove(unicast);
        }
    }

    private boolean removeMulticast(Multicast<T> multicast) {
        int sourceCount = count();
        int targetCount = multicast.count();

        for (int i = sourceCount - targetCount; i >= 0; i--) {
            if (equal(multicast.unicastList, i, targetCount)) {
                delete(unicastList, i, targetCount);
                return true;
            }
        }

        return false;
    }

    private boolean containsUnicast(T unicast) {
        return unicastList.contains(unicast);
    }

    private boolean containsMulticast(Multicast<T> multicast) {
        int sourceCount = count();
        int targetCount = multicast.count();

        for (int i = sourceCount - targetCount; i >= 0; i--) {
            if (equal(multicast.unicastList, i, targetCount)) {
                return true;
            }
        }

        return false;
    }

    private boolean equal(List<T> unicastList, int start, int count) {
        for (int i = 0; i < count; i++) {
            if (!this.unicastList.get(i + start).equals(unicastList.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void delete(List<T> unicastList, int start, int count) {
        for (int i = 0; i < count; i++) {
            unicastList.remove(start);
        }
    }
}