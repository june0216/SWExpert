package 삼성알고리즘특강.trie;
import java.util.*;
public class trie구현연습 {

    class TrieNode{
        private Map<Character, TrieNode> childNode;
        private boolean isLast;

        public boolean isLastChar(){
            return this.isLast;
        }

    }

    class Trie{
        private TrieNode root = new TrieNode();

        public void insert(String word){
            TrieNode node = this.root;
            for(char c : word.toCharArray()){
                node = node.childNode.computeIfAbsent(c, x -> new TrieNode());
            }
            node.isLast = true;
        }

        public boolean contains(String word){
            TrieNode node = this.root;
            for(char c : word.toCharArray()){
                TrieNode child = node.childNode.get(c);
                if(child == null) return false;
                node = child;
            }
            return node.isLast;
        }

        public void delete(String word, TrieNode node, int index){
            char key = word.charAt(index);
            if(!node.childNode.containsKey(key)) return;
            TrieNode child = node.childNode.get(key);
            if(child.isLast){
                child.isLast = false;
                if(child.childNode.isEmpty()){
                    node.childNode.remove(key);
                }

            }else {
                delete(word, child, ++index);
                if(!child.isLast && child.childNode.isEmpty()){
                    node.childNode.remove(key);
                }
            }
        }

        public void delete(String word){
            delete(word, this.root, 0);

        }

    }
}
