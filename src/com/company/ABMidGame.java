package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ABMidGame {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static char[] board = new char[21];
    static int position_evaluated = 0;

    public static void main(String[] args){

        String inputFile = args[0];
        String outputFile = args[1];
        int depth = Integer.parseInt(args[2]);

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String st; int i=0;
            while ((st = br.readLine()) != null) {
                board = st.toCharArray();
            }
            display(board);
            fancyBoard(board);
            ABMidGame mm = new ABMidGame();
            Node res = mm.ABMaxMinMidEndGame(depth, board, (-1*((int) Math.pow(10,8)))+2, ((int) Math.pow(10,8))-2);
            System.out.println("Postions Evaluated: "+position_evaluated);
            System.out.println("Res. Estimate: "+res.staticEstimate);
            System.out.println("Res Board: ");
            display(res.board);
            fancyBoard(res.board);

            String outputBoard = String.valueOf(res.board);
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(outputBoard);
            bufferedWriter.close();

        }catch (Exception e){
            System.out.println("Exception: "+e);
        }

    }

    public Node ABMaxMinMidEndGame(int depth, char[] board, int a, int b) {
        int minMaxEstimate = 0;
        List<char[]> childrenNodes = new ArrayList<>();
        if(depth ==0){
            minMaxEstimate = staticEstimationMidEnd(board);
            Node n = new Node();
            n.staticEstimate = minMaxEstimate;
//            n.board = board;
            position_evaluated++;
            return n;
        }
        else {
            Node minBoard;
            childrenNodes = generateMovesMidgameEndgame(board);

            Node v = new Node();
            v.board = new char[]{'x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x'};
            v.staticEstimate = (-1*((int) Math.pow(10,8)))-2 ;
            for(int i=0; i<childrenNodes.size(); i++) {
                minBoard = ABMinMaxMidEndGame(depth-1, childrenNodes.get(i), a, b);
                int u = minBoard.staticEstimate;
                if( v.staticEstimate < u ){
                    v.staticEstimate = u;
                    v.board = childrenNodes.get(i);
                }
                if(v.staticEstimate >= b) return v;
                else a = Math.max(v.staticEstimate, a);
            }
            return v;
        }

    }

    public Node ABMinMaxMidEndGame(int depth, char[] board, int a, int b) {
        int minMaxEstimate = 0;
        List<char[]> childrenNodes;
        if(depth == 0){
            minMaxEstimate = staticEstimationMidEnd(board);
            Node n = new Node();
            n.staticEstimate = minMaxEstimate;
//            n.board = board;
            position_evaluated++;
            return n;
        }
        else {
            Node maxBoard;
            childrenNodes = generateMovesMidgameEndgame(swap(board));
            Node v = new Node();
            v.staticEstimate = ((int) Math.pow(10,8))+2;
            v.board = new char[]{'x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x'};

            for(int i=0; i<childrenNodes.size(); i++) {
                maxBoard = ABMaxMinMidEndGame(depth-1, swap(childrenNodes.get(i)),a ,b);
                int u = maxBoard.staticEstimate;
                if( v.staticEstimate > u){
                    v.staticEstimate = u;
                    v.board = childrenNodes.get(i);
                }
                if(v.staticEstimate <= a) return v;
                else b = Math.min(v.staticEstimate, b);
            }
            return v;
        }

    }

    public int staticEstimationMidEnd(char[] board){

        int numBlackPieces = pieceEstimator(board)[0];
        int numWhitePieces = pieceEstimator(board)[1];
        int numBlackMoves = 0;
        List<char[]> blackMoves;
        blackMoves = generateMovesMidgameEndgame(swap(board));

        numBlackMoves = blackMoves.size();

        if (numBlackPieces <= 2) return((int) Math.pow(10,8));
        else if (numWhitePieces <= 2) return((-1*((int) Math.pow(10,8))));
        else if (numBlackMoves==0) return((int) Math.pow(10,8));
        else return ( 1000*(numWhitePieces - numBlackPieces) - numBlackMoves);

    }

    public boolean closeMill(int j, char[] board) {
        char C = board[j];
        if(C=='x') return false;
        switch (j) {
            case 0:
                if ((board[6] == C && board[18] == C) || (board[2] == C && board[4] == C)) return true;
                break;
            case 1:
                if (board[11] == C && board[20] == C) return true;
                break;
            case 2:
                if ((board[7] == C && board[15] == C) || (board[0] == C && board[4] == C)) return true;
                break;
            case 3:
                if (board[10] == C && board[17] == C) return true;
                break;
            case 4:
                if ((board[0] == C && board[2] == C) || (board[8] == C && board[12] == C)) return true;
                break;
            case 5:
                if (board[9] == C && board[14] == C) return true;
                break;
            case 6:
                if ((board[0] == C && board[18] == C) || (board[7] == C && board[8] == C)) return true;
                break;
            case 7:
                if ((board[6] == C && board[8] == C) || (board[2] == C && board[15] == C)) return true;
                break;
            case 8:
                if ((board[6] == C && board[7] == C) || (board[4] == C && board[12] == C)) return true;
                break;
            case 9:
                if ((board[10] == C && board[11] == C) || (board[5] == C && board[14] == C)) return true;
                break;
            case 10:
                if ((board[9] == C && board[11] == C) || (board[3] == C && board[17] == C)) return true;
                break;
            case 11:
                if ((board[9] == C && board[10] == C) || (board[1] == C && board[20] == C)) return true;
                break;
            case 12:
                if ((board[8] == C && board[4] == C) || (board[13] == C && board[14] == C)) return true;
                break;
            case 13:
                if ((board[12] == C && board[14] == C) || (board[16] == C && board[19] == C)) return true;
                break;
            case 14:
                if ((board[12] == C && board[13] == C) || (board[9] == C && board[5] == C)) return true;
                break;
            case 15:
                if ((board[2] == C && board[7] == C) || (board[16] == C && board[17] == C)) return true;
                break;
            case 16:
                if ((board[15] == C && board[17] == C) || (board[13] == C && board[19] == C)) return true;
                break;
            case 17:
                if ((board[15] == C && board[16] == C) || (board[3] == C && board[10] == C)) return true;
                break;
            case 18:
                if ((board[0] == C && board[6] == C) || (board[19] == C && board[20] == C)) return true;
                break;
            case 19:
                if ((board[18] == C && board[20] == C) || (board[13] == C && board[16] == C)) return true;
                break;
            case 20:
                if ((board[18] == C && board[19] == C) || (board[1] == C && board[11] == C)) return true;
                break;
        }
        return false;
    }

    public static void display(char[] board){
        for(int i=0; i<21; i++){
            System.out.print(board[i]);
        }
        System.out.println();
    }

    public List<char[]> generateRemove(char[] cloneBoard, List<char[]> L) {
        List<char[]> cList = new ArrayList<>(L);
        boolean val = false;
        for(int x=0; x< 21 ;x++){
            if(cloneBoard[x] == 'B') {
                if (!closeMill(x, cloneBoard)) {  //check if it is in Mill already
                    char[] cRemoveBoard = cloneBoard.clone();
                    cRemoveBoard[x] = 'x';
                    cList.add(cRemoveBoard);
                    val = true;
                }
            }
        }
        if(!val) cList.add(cloneBoard);
        return cList;
    }

    public static int[] pieceEstimator(char[] board) {
        int BlackPieces = 0;
        int WhitePieces = 0;
        for (char x : board) {
            if (x == 'B') BlackPieces++;
            else if (x == 'W') WhitePieces++;
        }
        return new int[]{BlackPieces, WhitePieces};

    }

    public List<char[]> generateMove(char[] board){
        int count=0;
        List<char[]> cList = new ArrayList<>();
        for(int i=0; i< 21 ;i++){
            if(board[i] == 'W'){
                int n[] = neighbors(i);
                for(int j: n){
                    if(board[j] == 'x'){
                        char[] cloneBoard = board.clone();
                        cloneBoard[i] = 'x';
                        cloneBoard[j] = 'W';
                        if(closeMill(j, cloneBoard)) {
                            cList = generateRemove(cloneBoard, cList);
                        }
                        else cList.add(cloneBoard);
                    }
                }
            }
        }

        return cList;
    }

    public static int[] neighbors(int position){
        int[] neighborsList;
        switch (position){
            case 0: neighborsList = new int[]{1,2,6}; return neighborsList;
//            break;
            case 1: neighborsList = new int[]{0,11}; return neighborsList;
//            break;
            case 2: neighborsList = new int[]{0,3,4,7}; return neighborsList;
//            break;
            case 3: neighborsList = new int[]{2,10}; return neighborsList;
//            break;
            case 4: neighborsList = new int[]{2,5,8}; return neighborsList;
//            break;
            case 5: neighborsList = new int[]{4,9}; return neighborsList;
//            break;
            case 6: neighborsList = new int[]{0,7,18}; return neighborsList;
//            break;
            case 7: neighborsList = new int[]{2,6,8,15}; return neighborsList;
//            break;
            case 8: neighborsList = new int[]{4,7,12}; return neighborsList;
//            break;
            case 9: neighborsList = new int[]{5,10,14}; return neighborsList;
//            break;
            case 10: neighborsList = new int[]{3,9,11,17}; return neighborsList;
//            break;
            case 11: neighborsList = new int[]{1,10,20}; return neighborsList;
//            break;
            case 12: neighborsList = new int[]{8,13}; return neighborsList;
//            break;
            case 13: neighborsList = new int[]{12,14,16}; return neighborsList;
//            break;
            case 14: neighborsList = new int[]{9,13}; return neighborsList;
//            break;
            case 15: neighborsList = new int[]{7,16}; return neighborsList;
//            break;
            case 16: neighborsList = new int[]{13,15,17,19}; return neighborsList;
//            break;
            case 17: neighborsList = new int[]{10,16}; return neighborsList;
//            break;
            case 18: neighborsList = new int[]{6,19}; return neighborsList;
//            break;
            case 19: neighborsList = new int[]{16,18,20}; return neighborsList;
//            break;
            case 20: neighborsList = new int[]{11,19}; return neighborsList;
//            break;
        }
        System.out.println("Neighbour is NULL");
        return null;
    }

    public char[] swap(char[] x) {
        char[] board = x.clone();
        for(int i=0;i<board.length;i++) {
            if(board[i]=='W') {
                board[i] = 'B';
            }
            else if(board[i]=='B')
                board[i] = 'W';
        }
        return board;
    }

    public static void fancyBoard(char[] board) {
        System.out.println(ANSI_RED+board[18] + "--------" + board[19] + "--------" + board[20]);
        System.out.println("|        |        |");
        System.out.println("|  " + board[15] + "-----" + board[16] + "-----" + board[17] + "  |");
        System.out.println("|  |     |     |  |");
        System.out.println("|  |  " + board[12] + "--" + board[13] + "--" + board[14] + "  |  |");
        System.out.println("|  |  |     |  |  |");
        System.out.println(board[6] + "--" + board[7] + "--" + board[8] + "     " + board[9] + "--" + board[10] + "--" + board[11]);
        System.out.println("|  |  |     |  |  |");
        System.out.println("|  |  " + board[4] + "-----" + board[5] + "  |  |");
        System.out.println("|  |/          |  |");
        System.out.println("|  " + board[2] + "-----------" + board[3] + "  |");
        System.out.println("|/                |");
        System.out.println(board[0] + "-----------------" + board[1]+ANSI_RESET);

    }

    public List<char[]> generateMovesMidgameEndgame(char[] board){
        List<char[]> L = new ArrayList<>();
        int whitePieces = 0;
        for(int i=0; i<21; i++){
            if(board[i] == 'W')
                whitePieces++;
        }

        if(whitePieces == 3)
            L = generateHopping(board);
        else
            L = generateMove(board);

        return L;
    }

    public List<char[]> generateHopping(char[] board){
        List<char[]> L= new ArrayList<>();
        for(int i=0; i<21 ;i++){
            if(board[i] == 'W'){
                for(int j=0; j<21; j++){
                    if(board[j] == 'x'){
                        char[] cloneBoard = board.clone();
                        cloneBoard[i] = 'x';
                        cloneBoard[j] = 'W';
                        if(closeMill(j, cloneBoard))
                            L = generateRemove(cloneBoard, L);
                        else
                            L.add(cloneBoard);
                    }
                }
            }
        }
        return L;
    }



}

