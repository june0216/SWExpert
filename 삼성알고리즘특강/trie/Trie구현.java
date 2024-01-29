package 삼성알고리즘특강.trie;

import java.util.*;

public class Trie구현 {

    public class Trie{ // 빈 문자열을 가지고 있는 루트노드로 시작 (insert로 문자를 넣어나갈 예정)
        private TrieNode rootNode;

        Trie(){
            rootNode = new TrieNode();
        }

        // 단어정보 저장
        void insert(String word){
            TrieNode thisNode = this.rootNode;
            for(int i = 0; i < word.length(); i++){ // 단어 하나씩 루트 노드부터 시작하여 저장하낟.
                thisNode = thisNode.getChildNodes().computeIfAbsent(word.charAt(i), c -> new TrieNode());
                /*
                현재 문자(word.charAt(i))를 키로 사용하여 자식 노드 맵에서 노드를 찾습니다.
                만약 해당 문자에 대한 자식 노드가 이미 존재한다면, 그 노드를 반환합니다.
                만약 자식 노드가 존재하지 않는다면, 새로운 TrieNode를 생성하여 맵에 추가하고, 이 새 노드를 반환합니다.
                 */

            }
            thisNode.setIsLastChar(true); // for문을 다 돌고 나오면 thisNode 변수는 마지막 위치 노드가 저장되어 있음
        }

        // 해당 단어 존재 여부 확인
        boolean contains(String word){
            TrieNode thisNode = this.rootNode;
            for(int i = 0; i < word.length(); i++){
                char character = word.charAt(i);
                TrieNode node = thisNode.getChildNodes().get(character); // 자식 중 해당 알파벳 노드를 가져온다.
                if(node == null) return false;
                thisNode = node; // 다음 노드
            }
            return thisNode.isLastChar();
        }

        // 특정 단어 삭제
        void delete(String word){
            delete(this.rootNode, word, 0);
        }


        // 삭제 로직 -> 재귀적으로
        private void delete(TrieNode thisNode, String word, int index){
            char key = word.charAt(index);
            if(!thisNode.getChildNodes().containsKey(key)){
                throw new Error("There is no ["+word+"] in this Trie");
            }
            TrieNode childNode = thisNode.getChildNodes().get(key);
            index++; // 다음 index
            if(index == word.length()){ // 마지막 index라면
                if(!childNode.isLastChar){ // 마지막 index임에도 lastchar이 아니라면 예외 처리
                    throw new Error("There is no ["+word+"] in this Trie");
                }
                // 삭제 가능한 상태
                childNode.setIsLastChar(false); // 마지막 노드가 이제 아니므로 false 처리 ( 다른 단어가 공유한다고 하더라도 리프 노드는 단어당 1개이기 때문에 해당 노드는 마지막 노드가 아니라는 것이 확실)
                if(childNode.getChildNodes().isEmpty()){ // 공유하고 있는 다른 단어가 없으면 물리적으로 지움
                    thisNode.getChildNodes().remove(key);
                }
            }else{
                delete(childNode, word, index);
                if(!childNode.isLastChar() && childNode.getChildNodes().isEmpty()){ // 마지막 노드가 아닌 중간에 있는 노드들 삭제 (자기 자식이 먼저 삭제 되었을 때 )
                    // isEmpty 조건을 비교하기 때문에 중복되어 노드를 공유하는 경우는 삭제하지 않음
                    thisNode.getChildNodes().remove(key);
                }
            }

        }


    }

    public class TrieNode{ // 자식노드맵과 현재 노드가 마지막 글자인지 여부
        private Map<Character, TrieNode> childNodes = new HashMap<>(); //  이어지는 자식 노드임, 자기의 부모는 모름
        // 노드에서 이어진 간선이 문자를 가지고 있다고 보면 된다.
        private boolean isLastChar;

        boolean isLastChar(){
            return this.isLastChar;
        }
        void setIsLastChar(boolean isLastChar){
            this.isLastChar = isLastChar;
        }

        public Map<Character, TrieNode> getChildNodes() {
            return childNodes;
        }
    }
}
