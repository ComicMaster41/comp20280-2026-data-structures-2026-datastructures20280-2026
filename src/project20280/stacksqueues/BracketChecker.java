package project20280.stacksqueues;

import project20280.interfaces.Deque;
import project20280.interfaces.Queue;
import project20280.list.DoublyLinkedList;

class BracketChecker {
    private String input;

    public BracketChecker(String in) {
        input = in;
    }

    public boolean check() {
        // TODO
        Deque<Character> deque = new LinkedDeque<>();
        for (char ch: input.toCharArray()) {
            if (ch == '{' || ch == '[' || ch == '(') {
                deque.addFirst(ch);
            }

            else if (ch == '}' || ch == ']' || ch == ')') {
                // check some edge cases
                // 1) do we have a match?
                if (deque.isEmpty()) {

                    return false;
                }
                // what if we ahve () for example
                char openBrace = deque.first();


                // check if we can make a match
                // beginning would be deque.first
                if (!((openBrace == '{' && ch == '}')
                                || (openBrace == '[' && ch == ']')
                                || (openBrace == '(' && ch == ')'))) {
                        // if we don't ahve a maatch
                    return false;
                }

                deque.removeFirst(); // gets rid of the match
            }
        }
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            System.out.println(checker.check());
        }
    }
}