package pers.ryuu.functional_interface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DelegateConcurrentTest {

    @Test
    void addMulticast() {
        Action1Arg<Integer> action1 = new Action1Arg<>();
        action1.add(System.out::println);
        action1.add(($int) -> System.out.println($int + 1));
        action1.add(($int) -> System.out.println($int + 2));
        action1.invoke(0);
        System.out.println();

        Action1Arg<Integer> action2 = new Action1Arg<>();
        action2.add(System.out::println);
        action2.add(($int) -> System.out.println($int + 1));
        action2.add(($int) -> System.out.println($int + 2));
        action2.invoke(0);
        System.out.println();

        action1.add(action2);
        action1.invoke(0);
    }

    @Test
    void removeAfterTargetInvoke() {
        ArrayList<Integer> res = new ArrayList<>();

        Action action = new Action();
        IAction test1 = () -> res.add(1);
        IAction test2 = () -> res.add(2);
        IAction test3 = () -> res.add(3);
        IAction test4 = () -> res.add(4);
        IAction test5 = () -> res.add(5);
        IAction removeTest3 = () -> action.remove(test3);

        action.add(test1);
        action.add(test2);
        action.add(test3);
        action.add(removeTest3);
        action.add(test4);
        action.add(test5);

        action.invoke();

        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
            }
        });
        System.out.println(res);

        res.clear();
        action.invoke();
        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(4);
                add(5);
            }
        });
        System.out.println(res);
    }

    @Test
    void removeBeforeTargetInvoke() {
        ArrayList<Integer> res = new ArrayList<>();

        Action action = new Action();
        IAction test1 = () -> res.add(1);
        IAction test2 = () -> res.add(2);
        IAction test3 = () -> res.add(3);
        IAction test4 = () -> res.add(4);
        IAction test5 = () -> res.add(5);
        IAction removeTest3 = () -> action.remove(test3);

        action.add(test1);
        action.add(test2);
        action.add(removeTest3);
        action.add(test3);
        action.add(test4);
        action.add(test5);

        action.invoke();

        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(4);
                add(5);
            }
        });
        System.out.println(res);
    }

    @Test
    void remove() {
        ArrayList<Integer> res = new ArrayList<>();

        Action action = new Action();
        IAction test1 = () -> res.add(1);
        IAction test2 = () -> res.add(2);
        IAction test3 = () -> res.add(3);
        IAction test4 = () -> res.add(4);
        IAction test5 = () -> res.add(5);
        IAction removeTest3 = () -> action.remove(test3);

        action.add(test1);
        action.add(test2);
        action.add(removeTest3);
        action.add(test3);
        action.add(test3);
        action.add(removeTest3);
        action.add(test4);
        action.add(test5);

        action.invoke();
        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
            }
        });
        System.out.println(res);
    }

    @Test
    void removeReturnValue() {
        Action action = new Action();
        IAction test1 = () -> System.out.println("remove return value test");

        action.add(test1);
        boolean removeResult1 = action.remove(test1);
        boolean removeResult2 = action.remove(test1);
        assert removeResult1;
        assert !removeResult2;
    }

    @Test
    void removeNull() {
        Action action = new Action();
        boolean removeNullResult = action.remove(null);
        assert !removeNullResult;
    }

    @Test
    void addMethodMoreMultiTime() {
        ArrayList<Integer> res = new ArrayList<>();
        Action action = new Action();
        IAction test1 = () -> res.add(1);

        action.add(test1);
        action.add(test1);
        action.add(test1);
        action.add(test1);
        action.add(test1);

        action.invoke();
        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(1);
                add(1);
                add(1);
                add(1);
            }
        });
        System.out.println(res);
    }

    @Test
    void add() {
        ArrayList<Integer> res = new ArrayList<>();

        Action action = new Action();
        IAction test1 = () -> res.add(1);
        IAction test2 = () -> res.add(2);
        IAction test3 = () -> res.add(3);
        IAction test4 = () -> res.add(4);
        IAction test5 = () -> res.add(5);
        IAction addTest3 = () -> action.add(test3);

        action.add(test1);
        action.add(test2);
        action.add(addTest3);
        action.add(test3);
        action.add(test4);
        action.add(test5);

        action.invoke();

        System.out.println(res);
        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(3);
            }
        });
    }

    @Test
    void addLast() {
        ArrayList<Integer> res = new ArrayList<>();

        Action action = new Action();
        IAction test1 = () -> res.add(1);
        IAction test2 = () -> res.add(2);
        IAction test3 = () -> res.add(3);
        IAction test4 = () -> res.add(4);
        IAction test5 = () -> res.add(5);
        IAction addTest3 = () -> action.add(test3);

        action.add(test1);
        action.add(test2);
        action.add(test3);
        action.add(test4);
        action.add(test5);
        action.add(addTest3);

        action.invoke();

        System.out.println(res);
        assert res.equals(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(3);
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void addNull() {
        Action action = new Action();
        boolean addNullResult = action.add((IAction) null);
        assert !addNullResult;
        assert action.count() == 0;
    }
}
