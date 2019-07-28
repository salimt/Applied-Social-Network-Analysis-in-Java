/**
 * @author: salimt
 */

package graph;
import java.util.*;
public class MDS {

    /**
     * creates the matrix for the Graph and sorts the users by their number of followers into LinkedList
     * @param G
     * @return the exact cover set
     */
    public static HashSet <Integer> MDS(Graph G) {

        int maxVal = Collections.max(G.exportGraph().keySet())+1;
        int[][] matrix = new int[maxVal][maxVal];

        // create matrix according to Graph
        for (Integer v : G.exportGraph().keySet()) {
            for (Integer neighbor : G.exportGraph().get(v)) {
                matrix[v][neighbor] = 1;
            }
        }

        HashMap<Integer, Integer> sortedMap = calcMapByColumn(matrix); // calc keys by their number of 1s on the column
        LinkedList<Integer> sortedKeys = sortMap(sortedMap);    // sort the keys highest first

        HashSet <Integer> exactCoverSet = new HashSet <>();
        HashSet <Integer> dominatingSet = new HashSet <>();
        while (sortedKeys.size() > 0) {
            // deepcopy of the matrix so original will not be changed from DLX
            int[][] matrixCopy = deepCopyMatrix(matrix);

            LinkedList<Integer> sortedKeysCopy = new LinkedList <>();
            sortedKeysCopy.addAll(sortedKeys); // copy of the keys so original will not be changed from DLX

            exactCoverSet = DLX(G, sortedKeysCopy, dominatingSet, matrixCopy);

            // if all columns are removed from the matrix then found
            if (exactCoverSet != null) { break; }
            sortedKeys.removeLast();

        } return exactCoverSet;
    }

    public static HashSet<Integer> DLX(Graph G, LinkedList<Integer> keys,
                                       HashSet<Integer> dominatingSet, int[][] matrix) {
        // if no keys are left so MDS not found -> return null
        if (keys.size()==0) { return null; }

        // choose/remove the column that has the least 1s
        Integer col = keys.removeLast();

        // rows that include the chosen column
        LinkedList<Integer> selectedRows = new LinkedList <>();
        for (int key: keys) {
            if (G.exportGraph().get(key).contains(col)) {
                selectedRows.add(key);
            }
        }

        if (selectedRows.size()==0) { return DLX(G, keys, dominatingSet, matrix); }

        // choose a row from the selectedRows
        Integer row = selectedRows.removeFirst();

        // columns that are to removed from matrix
        HashSet<Integer> columnsToDelete = G.exportGraph().get(row);

        // add the row into the dominating set ( partial solution )
        dominatingSet.add(row);

        // remove the key/rowNumber from matrix
        for (int rowNum=0; rowNum<matrix.length; rowNum++) {
            for (int colNum: columnsToDelete) {
                if (matrix[rowNum][colNum] == 1) {                    ////colNum-1
                    keys.removeFirstOccurrence(rowNum);       ////rowNum+1
                    break;
                }
            }
        }

       // change the value of a column to the 2 which will be considered removed
        int[][] updatedMatrix = matrix;
        for (int colNum: columnsToDelete) {
            updatedMatrix = removeCol(updatedMatrix, colNum); ////////////colNum-1
        }

        //if there is 1 left row check if its all elements are 1s if so MDS found, else return null
        if (keys.size() == 1) {
            int key = keys.getFirst();
            for (int j = 0; j < updatedMatrix[key].length; j++) {
                if (updatedMatrix[key][j] == 0) {
                    return null;
                }
            }
        }

        //if remaining columns are more than 0 then select/remove the column with the least 1s
        if (keys.size() > 0) {
            return DLX(G, keys, dominatingSet, updatedMatrix);
        } return dominatingSet;
    }

    /**
     *  calculate the number 1s in every column
     * @param ary
     * @return
     */
    public static HashMap<Integer, Integer> calcMapByColumn(int[][] ary) {
        HashMap<Integer, Integer> sortedMap = new HashMap <>();
        for (int i=0; i<ary.length; i++) {
            for (int j=0; j<ary[i].length; j++) {
                if (ary[j][i]==1) {
                    if (!sortedMap.containsKey(i)) {  ////i+1
                        sortedMap.put(i, 1);////i+1
                    } else {
                        sortedMap.put(i, sortedMap.get(i)+1);////i+1
                    }
                }
            }
        } return sortedMap;
    }

    /**
     * sort the map by their value
     * @param map
     * @return
     */
    public static LinkedList <Integer> sortMap(HashMap<Integer, Integer> map) {
        LinkedList <Integer> keys = new LinkedList <>(map.keySet());
        keys.sort(new Comparator <Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(map.get(o2), map.get(o1));
            }
        });
        return keys;
    }

    /**
     * changes the values of tiles into 2
     * @param table
     * @param colNumber
     * @return new int array without the column given in the parameter
     */
    public static int[][] removeCol(int[][] table, int colNumber) {
        int[][] result = table;
        for (int i = 0; i < table.length; i++) {
            for (int j = colNumber; j < colNumber+1; j++) {
                result[i][j] = 2;
            }
        }
        return result;
    }

    /**
     * makes a deep copy of a 2D matrix
     * @param matrix
     * @return the copy
     */
    public static int[][] deepCopyMatrix(int[][] matrix) {
        int [][] myInt = new int[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            myInt[i] = matrix[i].clone();
        return myInt;
    }

}



