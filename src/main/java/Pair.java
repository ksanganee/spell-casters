public class Pair<L, R> {
    private L left;
    private R right;

    public Pair(L l_value, R r_value) {
        this.left = l_value;
        this.right = r_value;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    public void setLeft(L new_left) {
        this.left = new_left;
    }

    public void setRight(R new_right) {
        this.right = new_right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + ", " + right.toString() + ")";
    }
}
