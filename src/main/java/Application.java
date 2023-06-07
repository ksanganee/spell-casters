import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Trie<String> word_trie = new Trie<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                word_trie.insert(line.split(""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
