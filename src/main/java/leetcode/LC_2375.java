package leetcode;

public class LC_2375 {

    public static void main(String[] args) {
//        System.out.println(new LC_2375().smallestNumber("IIIDIDDD"));
        System.out.println(new LC_2375().smallestNumber("IID"));
    }

    public String smallestNumber(String pattern) {
        int[] visited = new int[10];
        StringBuilder stringBuilder = new StringBuilder();
        backtrack(pattern, visited, 0, stringBuilder);
        return stringBuilder.toString();
    }

    public boolean backtrack(String pattern, int[] visited, int index, StringBuilder current) {
        if (current.length() == pattern.length() + 1) {
            return true;
        }
        for (int i = 1; i <= 9; i++) {
            if (visited[i] == 1) {
                continue;
            }
            if (index > 0) {
                char lastElem = current.charAt(current.length() - 1);
                char curr = pattern.charAt(index - 1);
                if ((curr == 'I' && lastElem - '0' > i) || curr == 'D' && lastElem - '0' < i) {
                    continue;
                }
            }
            visited[i] = 1;
            current.append(i);
            if(backtrack(pattern, visited, index+1, current)){
                return true;
            }
            current.deleteCharAt(current.length()-1);
            visited[i]=0;
        }
        return false;
    }
}
