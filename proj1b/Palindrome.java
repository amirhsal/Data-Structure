public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> newDeque = new LinkedListDeque();
        int i = 0;
        while (i != word.length()) {
            char wordChar = word.charAt(i);
            newDeque.addLast(wordChar);
            i += 1;

        }
        return newDeque;
    }
    private boolean helperIsPalindrome(Deque<Character> w) {
        if (w.size() == 0 || w.size() == 1) {
            return true;
        } else {
            char firstLetter = w.removeFirst();
            char lastLetter = w.removeLast();
            if (firstLetter == lastLetter) {
                return helperIsPalindrome(w);
            } else {
                return false;
            }

        }
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return helperIsPalindrome(wordDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int i = 0;
        int a = word.length();
        while (i < (int) a / 2) {
            char firstLetter = word.charAt(i);
            char lastLetter = word.charAt(a - i - 1);
            if (!cc.equalChars(firstLetter, lastLetter)) {
                return false;
            }
            i++;
        }
        return true;
    }

}


