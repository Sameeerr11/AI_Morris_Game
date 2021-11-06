package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//WxxxxxWxxxxxBxxxxxxxB

public class MinMaxOpeningImproved {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    static int position_evaluated = 0;

    public static char[] board = new char[30];

    public static void main(String[] args){

        String inputFile = args[0];
        String outputFile = args[1];
        int depth = Integer.parseInt(args[2]);

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String st;
            while ((st = br.readLine()) != null) {
                board = st.toCharArray();
            }
            display((board));
            fancyBoard((board));

            MinMaxOpeningImproved mmoi = new MinMaxOpeningImproved();
            Node res = mmoi.MinMaxOpeningGame(depth, board);
            System.out.println("Board: "+(res.board));
            System.out.println("Positions Evaluated: "+position_evaluated);
            System.out.println("Static Estimate:" +res.staticEstimate);
            display((res.board));
            fancyBoard((res.board));

            String outputBoard = String.valueOf(res.board);

            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(outputBoard);
            bufferedWriter.close();

        }catch (Exception e){
            System.out.println("Exception: "+e);
        }

    }

    public char[] game(char[] board, int depth){
        MinMaxOpeningImproved mmoi = new MinMaxOpeningImproved();
        Node res = mmoi.MinMaxOpeningGame(depth, board);
        return res.board;
    }

    public Node MinMaxOpeningGame(int depth, char[] board) {
        return MaxMinOpening(depth, board);
    }

    public Node MaxMinOpening(int depth, char[] board) {
        int minMaxEstimate = 0;
        List<char[]> childrenNodes = new ArrayList<>();
        if(depth <=0){
            minMaxEstimate = staticEstimationImprovedOpening(board, depth);
            Node n = new Node();
            n.staticEstimate = minMaxEstimate;
            n.board = board;
            position_evaluated++;
            return n;
        }
        else {
            Node minBoard;
            childrenNodes = generateAdd(board);

            Node v = new Node();
            v.board = new char[]{'x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x'};

            v.staticEstimate = Integer.MIN_VALUE;
            for(int i=0; i<childrenNodes.size(); i++) {
                minBoard = MinMaxOpening(depth-1, childrenNodes.get(i));
                int u = minBoard.staticEstimate;
                if( v.staticEstimate < u ){
                    v.staticEstimate = u;
                    v.board = childrenNodes.get(i);
                }
            }
            return v;
        }

    }

    public Node MinMaxOpening(int depth, char[] board) {
        int minMaxEstimate = 0;
        List<char[]> childrenNodes = new ArrayList<>();
        if(depth <= 0){
            minMaxEstimate = staticEstimationImprovedOpening(board, depth);
            Node n = new Node();
            n.staticEstimate = minMaxEstimate;
            n.board = board;
            position_evaluated++;
            return n;
        }
        else {
            Node maxBoard;
            childrenNodes = genBlackMoves(board);

            Node v = new Node();
            v.staticEstimate = Integer.MAX_VALUE;
            v.board = new char[]{'x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x','x'};

            for(int i=0; i<childrenNodes.size(); i++) {
                maxBoard = MaxMinOpening(depth-1, childrenNodes.get(i));

                int u = maxBoard.staticEstimate;

                if( v.staticEstimate > u){
                    v.staticEstimate = u;
                    v.board = childrenNodes.get(i);
                }
            }

            return v;
        }

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

    public boolean potentialCloseMill(int j, char[] board) {
//        char C = board[j];
        char C = 'B';
        switch (j) {
            case 0:
                if ((board[6] == C && board[18] == C) || (board[2] == C && board[4] == C)) return true;
                break;
            case 1:
                if (board[11] == C && board[20] == C) return true;
                break;
            case 2:
                if (((board[7] == C && board[15] == C)) || (board[0] == C && board[4] == C)) return true;
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

    public static void display(char[] board){
        for(int i=0; i<21; i++){
            System.out.print(board[i]);
        }
        System.out.println();
    }

    public List<char[]> generateAdd(char[] board) {
        List<char[]> L = new ArrayList<>();
        char[] cloneBoard;
        for(int i=0; i< 21 ;i++){
            if (board[i] == 'x') {
                cloneBoard = board.clone();
                cloneBoard[i] = 'W';              //Setting player position
                if (closeMill(i, cloneBoard)) {
                    L = generateRemove(cloneBoard, L);
                }
                else L.add(cloneBoard);
            }
        }
        return L;
    }

    public List<char[]> generateRemove(char[] cloneBoard, List<char[]> L) {
        List<char[]> cList = new ArrayList<>(L);
        boolean val = false;
        for(int x=0; x< 21 ;x++){
            if(cloneBoard[x] == 'B'){
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

    public int staticEstimationImprovedOpening(char[] board, int depth) {
        int a = 0;
        a  = millCount(board);
        int BlackPieces = pieceEstimator(board)[0];
        int WhitePieces = pieceEstimator(board)[1];
        int res = (WhitePieces - BlackPieces);
        res = res+a;
        return res;
    }

    public int millCount(char[] board){
        int mills  = 0;
        char[] clone = board.clone();
        for(int i=0; i< 21; i++){
            if(clone[i] == 'W'){
                if(potentialMill(clone, i)){
                    mills = mills+5;
                }
                if(closeMill(i,clone)){
                    mills = mills+100;
                }

            }
            if(clone[i] == 'W') {
                if (potentialCloseMill(i, clone)) {
                    mills = mills + 100;
                }
            }
        }
        return mills;
    }

    public boolean potentialMill(char[] board, int i){
        switch (i){
            case 0:
                if(board[6] == 'W' || board[18] == 'W') return true;
                break;
            case 1:
                if(board[11] == 'W' || board[20] == 'W') return true;
                break;
            case 2:
                if(board[0] == 'W' || board[4] == 'W' || board[7] == 'W' || board[15] == 'W') return true;
                break;
            case 3:
                if(board[10] == 'W' || board[17] == 'W') return true;
                break;
            case 4:
                if(board[0] == 'W' || board[2] == 'W' || board[8] == 'W' || board[12] == 'W') return true;
                break;
            case 5:
                if(board[9] == 'W' || board[14] == 'W') return true;
                break;
            case 6:
                if(board[0] == 'W' || board[18] == 'W' || board[7] == 'W' || board[8] == 'W') return true;
                break;
            case 7:
                if(board[6] == 'W' || board[8] == 'W' || board[2] == 'W' || board[15] == 'W') return true;
                break;
            case 8:
                if(board[6] == 'W' || board[7] == 'W' || board[4] == 'W' || board[12] == 'W') return true;
                break;
            case 9:
                if(board[5] == 'W' || board[14] == 'W' || board[10] == 'W' || board[11] == 'W') return true;
                break;
            case 10:
                if(board[9] == 'W' || board[11] == 'W' || board[3] == 'W' || board[17] == 'W') return true;
                break;
            case 11:
                if(board[9] == 'W' || board[10] == 'W' || board[1] == 'W' || board[20] == 'W') return true;
                break;
            case 12:
                if(board[4] == 'W' || board[8] == 'W' || board[13] == 'W' || board[14] == 'W') return true;
                break;
            case 13:
                if(board[12] == 'W' || board[14] == 'W' || board[16] == 'W' || board[19] == 'W') return true;
                break;
            case 14:
                if(board[12] == 'W' || board[13] == 'W' || board[9] == 'W' || board[5] == 'W') return true;
                break;
            case 15:
                if(board[2] == 'W' || board[7] == 'W' || board[16] == 'W' || board[17] == 'W') return true;
                break;
            case 16:
                if(board[15] == 'W' || board[17] == 'W' || board[13] == 'W' || board[19] == 'W') return true;
                break;
            case 17:
                if(board[15] == 'W' || board[16] == 'W' || board[10] == 'W' || board[3] == 'W') return true;
                break;
            case 18:
                if(board[0] == 'W' || board[6] == 'W' || board[19] == 'W' || board[20] == 'W') return true;
                break;
            case 19:
                if(board[18] == 'W' || board[20] == 'W' || board[16] == 'W' || board[13] == 'W') return true;
                break;
            case 20:
                if(board[18] == 'W' || board[19] == 'W' || board[1] == 'W' || board[11] == 'W') return true;
                break;
        }

        return false;
    }

    public int[] pieceEstimator(char[] board) {
        int BlackPieces = 0;
        int WhitePieces = 0;
        for (char x : board) {
            if (x == 'B') BlackPieces++;
            else if (x == 'W') WhitePieces++;
        }
        return new int[]{BlackPieces, WhitePieces};

    }

    public List genBlackMoves(char[] board) {
        char[] lboard = board.clone();
        for(int i=0;i<lboard.length;i++) {
            if(lboard[i]=='W') {
                lboard[i] = 'B';
                continue;
            }
            if(lboard[i]=='B') {
                lboard[i] = 'W';
            }
        }

        List<char[]> xBoard;
        List<char[]> swapBoard = new ArrayList<char[]>();

        xBoard = generateAdd(lboard);
        for(char[] y : xBoard) {
            char[] lsboard = y;
            for(int i=0;i<lsboard.length;i++) {
                if(lsboard[i]=='W') {
                    lsboard[i] = 'B';
                    continue;
                }
                if(lsboard[i]=='B') lsboard[i] = 'W';
            }
            swapBoard.add(y);
        }
        return swapBoard;
    }

    public static char[] swap(char[] x) {
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

}
