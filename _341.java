import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*interface NestedInteger {
    // @return true if this NestedInteger holds a single integer,
    // rather than a nested list.
    boolean isInteger();

    // @return the single integer that this NestedInteger holds,
    // if it holds a single integer
    // return null if this NestedInteger holds a nested list
    Integer getInteger();

    // @return the nested list that this NestedInteger holds,
    // if it holds a nested list
    // return empty list if this NestedInteger holds a single integer
    List<NestedInteger> getList();
}*/

public class _341 {

    public static class NestedInteger {
        private Integer val;
        private List<NestedInteger> list;

        NestedInteger(Integer val) {
            this.val = val;
            this.list = null;
        }

        NestedInteger(List<NestedInteger> nestedList) {
            this.val = null;
            this.list = nestedList;
        }

        boolean isInteger() {
            return val != null;
        }

        Integer getInteger() {
            return val;
        }

        public List<NestedInteger> getList() {
            return list;
        }
    }

    public static class Solution1 {
        public class NestedIterator implements Iterator<Integer> {

            private Iterator<Integer> itr;

            private void traverse(NestedInteger node, List<Integer> ls) {
                if (node.isInteger()) {
                    ls.add(node.getInteger());
                    return;
                }
                for (NestedInteger child : node.getList()) {
                    traverse(child, ls);
                }
            }

            public NestedIterator(List<NestedInteger> nestedList) {
                List<Integer> res = new LinkedList<>();
                for (NestedInteger node : nestedList) {
                    traverse(node, res);
                }
                itr = res.iterator();
            }

            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }

            @Override
            public Integer next() {
                return itr.next();
            }
        }
    }

    public static class Solution2 {
        public class NestedIterator implements Iterator<Integer> {

            private LinkedList<NestedInteger> ls;

            public NestedIterator(List<NestedInteger> nestedList) {
                ls = new LinkedList<>(nestedList);
            }

            @Override
            public boolean hasNext() {
                while (!ls.isEmpty() && !ls.getFirst().isInteger()) { // if the 1st element is nested integer
                    List<NestedInteger> temp = ls.removeFirst().getList();
                    for (int i = temp.size() - 1; i >= 0; i--) {
                        ls.addFirst(temp.get(i));
                    }
                }
                return !ls.isEmpty();
            }

            @Override
            public Integer next() {
                return ls.removeFirst().getInteger();
            }
        }
    }
}
