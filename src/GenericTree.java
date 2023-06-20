import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericTree<T> {
    private final TreeNode<T> root;
    private final Map<T, TreeNode<T>> nodeMap;

    /**
     * Konstruktor, der einen Baum mit dem angegebenen Wurzelelement erstellt.
     *
     * @param rootData das Wurzelelement des Baumes
     */
    public GenericTree(T rootData) {
        root = new TreeNode<>(rootData);
        nodeMap = new HashMap<>();
        nodeMap.put(rootData, root);
    }

    /**
     * Gibt das Wurzelelement des Baumes zurück.
     *
     * @return das Wurzelelement
     */
    public T getRoot() {
        return root.data;
    }

    /**
     * Gibt eine Liste der direkten Kinder des angegebenen Elternknotens zurück.
     *
     * @param parent der Elternknoten
     * @return eine Liste der direkten Kinder des Elternknotens
     * @throws IllegalArgumentException wenn der Elternknoten nicht im Baum gefunden wird
     */
    public List<T> getChildren(T parent) {
        TreeNode<T> parentNode = nodeMap.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent node not found in the tree.");
        }
        List<T> children = new ArrayList<>();
        for (TreeNode<T> childNode : parentNode.children) {
            children.add(childNode.data);
        }
        return children;
    }

    /**
     * Gibt den Elternknoten des angegebenen Knotens zurück.
     *
     * @param child der Knoten
     * @return der Elternknoten des angegebenen Knotens, oder null, wenn es keinen Elternknoten gibt (für die Wurzel)
     * @throws IllegalArgumentException wenn der Knoten nicht im Baum gefunden wird
     */
    public T getParent(T child) {
        TreeNode<T> childNode = nodeMap.get(child);
        if (childNode == null) {
            throw new IllegalArgumentException("Child node not found in the tree.");
        }
        if (childNode.parent == null) {
            return null;
        }
        return childNode.parent.data;
    }

    /**
     * Fügt dem angegebenen Elternknoten einen neuen Knoten als Kind hinzu.
     *
     * @param element das Element des neuen Knotens
     * @param parent  der Elternknoten, zu dem der neue Knoten hinzugefügt werden soll
     * @throws IllegalArgumentException wenn der Elternknoten nicht im Baum gefunden wird
     */
    public void add(T element, T parent) {
        TreeNode<T> parentNode = nodeMap.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent node not found in the tree.");
        }
        TreeNode<T> newNode = new TreeNode<>(element);
        newNode.parent = parentNode;
        parentNode.children.add(newNode);
        nodeMap.put(element, newNode);
    }

    /**
     * Entfernt das angegebene Blatt aus dem Baum.
     *
     * @param leaf das zu entfernende Blatt
     * @throws IllegalArgumentException wenn das Blatt nicht im Baum gefunden wird
     */
    public void removeLeaf(T leaf) {
        TreeNode<T> leafNode = nodeMap.get(leaf);
        if (leafNode == null) {
            throw new IllegalArgumentException("Leaf node not found in the tree.");
        }
        TreeNode<T> parentNode = leafNode.parent;
        if (parentNode != null) {
            parentNode.children.remove(leafNode);
            nodeMap.remove(leaf);
        }
    }

    /**
     * Entfernt einen Knoten und den zugehörigen Teilbaum aus dem Baum.
     *
     * @param parent der Knoten, der entfernt werden soll
     * @throws IllegalArgumentException wenn der Knoten nicht im Baum gefunden wird
     */
    public void removeSubtree(T parent) {
        TreeNode<T> parentNode = nodeMap.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Node not found in the tree.");
        }
        TreeNode<T> grandparentNode = parentNode.parent;
        if (grandparentNode != null) {
            grandparentNode.children.remove(parentNode);
            nodeMap.remove(parent);
        }
    }

    /**
     * Gibt die Anzahl der Elemente im Baum zurück.
     *
     * @return die Anzahl der Elemente im Baum
     */
    public int size() {
        return nodeMap.size();
    }

    /**
     * Gibt die Höhe des Baumes zurück.
     *
     * @return die Höhe des Baumes
     */
    public int height() {
        return getHeight(root);
    }

    // Hilfsmethode zur Berechnung der Höhe des Baumes
    private int getHeight(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        int maxChildHeight = 0;
        for (TreeNode<T> child : node.children) {
            int childHeight = getHeight(child);
            maxChildHeight = Math.max(maxChildHeight, childHeight);
        }
        return 1 + maxChildHeight;
    }

    private static class TreeNode<T> {
        private final T data;
        private final List<TreeNode<T>> children;
        private TreeNode<T> parent;

        TreeNode(T data) {
            this.data = data;
            this.parent = null;
            this.children = new ArrayList<>();
        }
    }
}
