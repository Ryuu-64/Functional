package org.ryuu.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class Multicast<F extends Unicast> implements Unicast, Iterable<F> {
    protected final List<F> unicastList = new ArrayList<>();
    protected Iterator<F> iterator = new Iterator<>(this);

    @SuppressWarnings("unchecked")
    public final boolean add(F functional) {
        if (functional == null) {
            return false;
        } else if (functional instanceof Multicast) {
            return addMulticast((Multicast<F>) functional);
        } else /* if (functional instanceof Unicast) */ {
            return addUnicast(functional);
        }
    }

    @SuppressWarnings("unchecked")
    public final boolean remove(F functional) {
        if (functional == null) {
            return false;
        } else if (functional instanceof Multicast) {
            return removeMulticast((Multicast<F>) functional);
        } else /* if (functional instanceof Unicast) */ {
            return removeUnicast(functional);
        }
    }

    @SuppressWarnings("unchecked")
    public final boolean contains(F functional) {
        if (functional == null) {
            return false;
        } else if (functional instanceof Multicast) {
            return containsMulticast((Multicast<F>) functional);
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

    public List<F> getUnicastList() {
        return new ArrayList<>(unicastList);
    }

    private boolean addUnicast(F unicast) {
        return unicastList.add(unicast);
    }

    private boolean addMulticast(Multicast<F> multicast) {
        boolean isAdd = false;
        for (F unicast : multicast.unicastList) {
            isAdd = addUnicast(unicast);
        }
        return isAdd;
    }

    private boolean removeUnicast(F unicast) {
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

    private boolean removeMulticast(Multicast<F> multicast) {
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

    private boolean containsUnicast(F unicast) {
        return unicastList.contains(unicast);
    }

    private boolean containsMulticast(Multicast<F> multicast) {
        int sourceCount = count();
        int targetCount = multicast.count();

        for (int i = sourceCount - targetCount; i >= 0; i--) {
            if (equal(multicast.unicastList, i, targetCount)) {
                return true;
            }
        }

        return false;
    }

    private boolean equal(List<F> unicastList, int start, int count) {
        for (int i = 0; i < count; i++) {
            if (!this.unicastList.get(i + start).equals(unicastList.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void delete(List<F> unicastList, int start, int count) {
        for (int i = 0; i < count; i++) {
            unicastList.remove(start);
        }
    }

    @Override
    public java.util.Iterator<F> iterator() {
        iterator.reset();
        return iterator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Multicast<?> multicast = (Multicast<?>) obj;
        return unicastList.equals(multicast.unicastList) && iterator.equals(multicast.iterator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unicastList, iterator);
    }

    private static class Iterator<E extends Unicast> implements java.util.Iterator<E> {
        private final Multicast<E> multicast;
        private int cursor;

        private Iterator(Multicast<E> multicast) {
            this.multicast = multicast;
        }

        private void reset() {
            cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return this.cursor != multicast.unicastList.size();
        }

        @Override
        public E next() {
            E next = multicast.unicastList.get(cursor);
            cursor++;
            return next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Iterator<?> that = (Iterator<?>) o;
            return cursor == that.cursor;
        }

        @Override
        public int hashCode() {
            return Objects.hash(cursor);
        }
    }
}