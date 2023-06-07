import java.util.ArrayList;
import java.util.Optional;

public class GraphNode<E> {
    private E value;
    private ArrayList<GraphNode<E>> neighbours = new ArrayList<>();

    public GraphNode() {
    }

    public GraphNode(E val) {
        this.value = val;
    }

    public E getValue() {
        return this.value;
    }

    public void setValue(E val) {
        this.value = val;
    }

    public Boolean hasValue() {
        return this.value == null;
    }

    public Optional<GraphNode<E>> hasNeighbour(E target) {
        for (GraphNode<E> neighbour : this.neighbours) {
            if (neighbour.getValue().equals(target)) {
                return Optional.of(neighbour);
            }
        }
        return Optional.empty();
    }

    public GraphNode<E> addNeighbour(E new_neighbour) {
        GraphNode<E> new_node = new GraphNode<E>(new_neighbour);
        return this.addNeighbour(new_node);
    }

    public GraphNode<E> addNeighbour(GraphNode<E> new_neighbour) {
        this.neighbours.add(new_neighbour);
        return new_neighbour;
    }

    public ArrayList<GraphNode<E>> getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbours(ArrayList<GraphNode<E>> new_neighbours) {
        this.neighbours = new_neighbours;
    }
}
