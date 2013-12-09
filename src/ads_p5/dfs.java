/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ads_p5;

// dfs.java
import java.util.ArrayList;
import java.util.List;

// demonstrates depth-first search
// to run this program: C>java DFSApp
////////////////////////////////////////////////////////////////
class StackX {

    private final int SIZE = 25;
    private int[] st;
    private int top;

    public StackX() // constructor
    {
        st = new int[SIZE]; // make array
        top = -1;
    }

    public void push(int j) // put item on stack
    {
        st[++top] = j;
    }

    public int pop() // take item off stack
    {
        return st[top--];
    }

    public int peek() // peek at top of stack
    {
        return st[top];
    }

    public boolean isEmpty() // true if nothing on stack-
    {
        return (top == -1);
    }
} // end class StackX
////////////////////////////////////////////////////////////////

class Vertex {

    public int label; // label (e.g. 'A')
    public boolean wasVisited;

    public Vertex(int lab) // constructor
    {
        label = lab;
        wasVisited = false;
    }
} // end class Vertex
////////////////////////////////////////////////////////////////

class Graph {

    private final int MAX_VERTS = 25;
    public Vertex vertexList[]; // list of vertices
    private int adjMat[][]; // adjacency matrix
    private int nVerts; // current number of vertices
    private StackX theStack;
    private int[] moveOrder;

    public Graph() // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
// adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
        {
            for (int k = 0; k < MAX_VERTS; k++) // matrix to 0
            {
                adjMat[j][k] = 0;
            }
        }
        theStack = new StackX();
        moveOrder = new int[MAX_VERTS];
    } // end constructor

    public void addVertex(int lab) {
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.println("to: "+vertexList[v].label);
    }

    public boolean knightTour(int depth, int v, int limit) {
        vertexList[v].wasVisited = true;

        theStack.push(v);

        if (depth < limit) {
            for (int i = 0; i < vertexList.length; i++) {
                if (vertexList[i].wasVisited == false && getAdjUnvisitedVertex(i, depth) != -1) {
                    
                    knightTour(depth + 1, getAdjUnvisitedVertex(i, depth), limit);
                    vertexList[i].wasVisited = true;

                } else if (!theStack.isEmpty()) {

                    moveOrder[i] = theStack.pop();
                    vertexList[v].wasVisited = false;

                }
            }
        }

        return true;
    }

    public void printMoveOrder() {
        for (int i = 0; i < moveOrder.length; i++) {
            System.out.println(moveOrder[i]);
        }
    }

    public void dfs(int start) // depth-first search
    { // begin at vertex 0
        System.out.println("Starting at: " + start);
        vertexList[start].wasVisited = true; // mark it
        //displayVertex(start); // display it
        theStack.push(start); // push it
        int depth = 0;
        while (!theStack.isEmpty() && depth != vertexList.length) // until stack empty,
        {
// get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek(), depth);
            depth++;
            if (v == -1) // if no such vertex,
            {
                vertexList[theStack.peek()].wasVisited = false;
                theStack.pop();
                depth--;

            } else // if it exists,
            {
                vertexList[v].wasVisited = true; // mark it
                displayVertex(v); // display it
                theStack.push(v); // push it 
                //depth++;
            }
        } // end while
// stack is empty, so we're done
        for (int j = 0; j < nVerts; j++) // reset flags
        {
            vertexList[j].wasVisited = false;
        }
    } // end dfs

    // returns an unvisited vertex adj to v
    public int getAdjUnvisitedVertex(int v, int depth) {
        for (int j = depth; j < nVerts; j++) {
            if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false) {
                return j;
            }
        }
        return -1;
    } // end getAdjUnvisitedVertex()
} // end class Graph
////////////////////////////////////////////////////////////////

class DFSApp {

    private final static int BOARD_SIZE = 5;
    private static Graph graph = new Graph();
    private static int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    public static void main(String[] args) {

        fillBoard();
        fillGraph();
        printBoard();
//        graph.knightTour(0, 0, 25);
//        graph.printMoveOrder();

//        theGraph.addVertex(1); // 0 (start for dfs)
//        theGraph.addVertex(2); // 1
//        theGraph.addVertex(3); // 2
//        theGraph.addVertex(4); // 3
//        theGraph.addVertex(5); // 4
//        theGraph.addEdge(0, 1); // AB
//        theGraph.addEdge(1, 2); // BC
//        theGraph.addEdge(0, 3); // AD
//        theGraph.addEdge(3, 4); // DE

        
        graph.dfs(0); // depth-first search
        System.out.println();
    } // end main()

    public static void fillGraph() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int vertexId = getVertexId(i, j);

                for (int vertex : getMoves(i, j)) {
                    int movedTo = vertex;
                    //System.out.println(vertexId+", "+ movedTo);
                    graph.addEdge(vertexId, movedTo);
                }
            }
        }
    }

    public static void printBoard() {
        int v = 0;
        for (int i = 0; i < board.length; i++) {
            System.out.println();
            for (int j = 0; j < board.length; j++) {
                if(Integer.toString(v).length() == 1){
                    System.out.print(v + "    ");
                }
                else{
                    System.out.print(v + "   ");
                }
                
                v++;
            }
            System.out.println();
        }
    }

    public static void fillBoard() {
        int v = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = v;
                graph.addVertex(v);
                v++;
            }
        }
    }

    public static Vertex getVertex(int v) {
        return graph.vertexList[v];
    }

    public static int getVertexId(int x, int y) {
        return board[x][y];
    }

    public static List<Integer> getMoves(int x, int y) {
        List<Integer> allowedMoves = new ArrayList<>();

        int i = 0;

        int[][] possibleMoves = {{-1, -2}, {-1, 2}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};

        for (int[] m : possibleMoves) {
            int newX = x + m[0];
            int newY = y + m[1];

            if (moveAllowed(newX) && moveAllowed(newY)) {
                allowedMoves.add(getVertexId(newX, newY));
                i++;
            }
        }

        return allowedMoves;
    }

    public static boolean moveAllowed(int x) {
        if (x >= 0 && x < BOARD_SIZE) {
            return true;
        }
        return false;
    }
} // end class DFSApp
////////////////////////////////////////////////////////////////
