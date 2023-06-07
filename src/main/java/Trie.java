import java.util.Optional;

public class Trie<E> {
    private GraphNode<E> root;

    public Trie() {
        root = new GraphNode<E>();
    }

    public Optional<GraphNode<E>> find(E[] target) {
        return this.find(target, this.root);
    }

    public Optional<GraphNode<E>> find(E[] target, GraphNode<E> current) {
        for (E subtarget : target) {
            Optional<GraphNode<E>> subtarget_node = root.hasNeighbour(subtarget);
            if (subtarget_node.isPresent()) {
                current = subtarget_node.get();
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(current);
    }

    public void insert(E[] new_values) {
        GraphNode<E> current = root;
        for (E new_value : new_values) {
            Optional<GraphNode<E>> next_node = current.hasNeighbour(new_value);
            if (next_node.isPresent()) {
                current = next_node.get();
            } else {
                current = current.addNeighbour(new_value);
            }
        }
    }

    public void setRoot(GraphNode<E> root) {
        this.root = root;
    }

    public GraphNode<E> getRoot() {
        return root;
    }
}