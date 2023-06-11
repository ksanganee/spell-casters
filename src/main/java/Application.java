import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Application {

    private static Pair<String, Integer> best_solution = new Pair<>("", -1);

    private static final Trie<String> word_trie = new Trie<>();

    public static void main(String[] args) {
        initialise_word_trie();

        String[][] board = create_board("kmneuqoplytasgbdeuiloknbe");
        print_board(board);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Pair<String, Integer> sol = new Pair<>(board[i][j], 1);
                ArrayList<Pair<Integer, Integer>> blacklist = new ArrayList<>();
                recursive_solve(board, i, j, word_trie.getNode(to_single_string_array(board[i][j]), word_trie.getRoot()).get(), blacklist, sol);
            }
        }

        System.out.println("\nbest: " + best_solution);
    }

    public static void recursive_solve(String[][] board, Integer pos_x, Integer pos_y, GraphNode<String> sub_trie, ArrayList<Pair<Integer, Integer>> blacklist, Pair<String, Integer> current_solution) {
        ArrayList<GraphNode<String>> current_neighbours = sub_trie.getNeighbours();
        if (current_neighbours.size() == 0) {
            if (sub_trie.getIsTerminal()) {
                System.out.println(current_solution);
                if (current_solution.getRight() > best_solution.getRight()) {
                    best_solution = current_solution;
                    return;
                }
            }
        }
        for (Pair<Integer, Integer> grid_neighbour_coord : get_board_neighbours(board, pos_x, pos_y, blacklist)) {
            for (GraphNode<String> trie_neighbour : current_neighbours) {
                if (trie_neighbour.getValue().equals(board[grid_neighbour_coord.getLeft()][grid_neighbour_coord.getRight()])) {
                    blacklist.add(new Pair<>(grid_neighbour_coord.getLeft(), grid_neighbour_coord.getRight()));
                    Pair<String, Integer> new_partial_solution = new Pair<>(current_solution.getLeft() + trie_neighbour.getValue(), current_solution.getRight() + 1);
                    recursive_solve(board, grid_neighbour_coord.getLeft(), grid_neighbour_coord.getRight(), trie_neighbour, blacklist, new_partial_solution);
                    blacklist.removeIf(element -> element.getLeft().equals(grid_neighbour_coord.getLeft()) && element.getRight().equals(grid_neighbour_coord.getRight()));
                }
            }
        }
    }

    public static void initialise_word_trie() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                word_trie.insert(line.split(""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] create_board(String letters) {
        return Stream.of(letters.split("(?<=\\G.{5})")).map(x -> x.split("")).toArray(String[][]::new);
    }

    public static void print_board(String[][] arr) {
        Arrays.stream(arr).forEach(x -> System.out.println(Arrays.toString(x)));
    }

    public static ArrayList<Pair<Integer, Integer>> get_board_neighbours(String[][] board, Integer x, Integer y, ArrayList<Pair<Integer, Integer>> blacklist) {
        ArrayList<Pair<Integer, Integer>> coords = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i < board.length && y + j >= 0 && y + j < board[0].length && !(x + i == 0 && y + j == 0)) {
                    if (!contains_pair(blacklist, x + i, y + j)) {
                        coords.add(new Pair<>(x + i, y + j));
                    }
                }
            }
        }
        return coords;
    }

    public static Boolean contains_pair(ArrayList<Pair<Integer, Integer>> arr, Integer x, Integer y) {
        for (Pair<Integer, Integer> element : arr) {
            if (element.getLeft().equals(x) && element.getRight().equals(y)) {
                return true;
            }
        }
        return false;
    }

    public static String[] to_single_string_array(String input) {
        String[] result = new String[1];
        result[0] = input;
        return result;
    }
}
