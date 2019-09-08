public class OffByN implements CharacterComparator {

    private int differ;

    public OffByN(int N) {
        this.differ = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == differ || x - y == -differ) {
            return true;
        }
        return false;
    }
}

