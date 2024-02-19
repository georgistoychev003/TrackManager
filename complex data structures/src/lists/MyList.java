package lists;

import java.util.Iterator;

public interface MyList<E> extends Iterable<E> {
    boolean add(E e);
    boolean remove(E e);
    boolean contains(E e);
    boolean containsAll(MyList<E> elements);
    boolean containsSome(MyList<E> elements);
    int length();
    Iterator<E> iterator();
    boolean isEmpty();
    E searchByName(String name);
}
