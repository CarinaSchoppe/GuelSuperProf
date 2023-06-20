import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Erstellen des Baums mit dem Wurzelelement "A"
        GenericTree<String> tree = new GenericTree<>("A");

        // Hinzufügen von Knoten zum Baum
        tree.add("B", "A");
        tree.add("C", "A");
        tree.add("D", "B");
        tree.add("E", "B");
        tree.add("F", "C");

        // Ausgabe des Wurzelelements
        System.out.println("Root: " + tree.getRoot());  // Ausgabe: Root: A

        // Ausgabe der direkten Kinder von "A"
        List<String> childrenA = tree.getChildren("A");
        System.out.println("Children of A: " + childrenA);  // Ausgabe: Children of A: [B, C]

        // Ausgabe des Elternknotens von "E"
        String parentE = tree.getParent("E");
        System.out.println("Parent of E: " + parentE);  // Ausgabe: Parent of E: B

        // Entfernen des Knotens "D"
        tree.removeLeaf("D");

        // Ausgabe der Anzahl der Elemente im Baum
        int size = tree.size();
        System.out.println("Size of tree: " + size);  // Ausgabe: Size of tree: 4

        // Ausgabe der Höhe des Baumes
        int height = tree.height();
        System.out.println("Height of tree: " + height);  // Ausgabe: Height of tree: 2
    }
}
