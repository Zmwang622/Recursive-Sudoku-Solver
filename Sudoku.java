package cs445.a3;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {
	//rec stands for recent. Idea: use two variables to track the last element.
    private static int recR=0;
    private static int recC=0;
	static boolean[] aux = new boolean[10];
	
	
    
  
    
    static boolean isFullSolution(int[][] board) 
    {
    	for(int i = 0; i<board.length;i++) 
    	{
    		for(int j = 0; j<9;j++) 
    		{	
    			if(board[i][j]==0)
    			{
    				return false;
    			}
    		}
    	}
    	return true;
    }
            
/***  OLD REJECT METHOD */ 
//    if a number  is in same box, row, or column
//    static boolean reject(int[][] board) 
//    {
//    	int[][] temp = board;
//    	printBoard(board);
//    	System.out.println("Beginning Rejection period");	
//    	//check each row
//    	for(int i = 0; i< temp.length;i++) 
//    	{
//    		if(rejRow(temp[i])) 
//    		{
//    			System.out.println("Row is invalid.");
//    			return true;
//    		}  		
//    	}
//    	System.out.println("Row is fine");
//    	//check each column
//    	for(int i =0; i<9;i++) 
//    	{
//    		if(rejCol(temp,i)) 
//    		{
//    			System.out.println("Column is invalid");
//    			return true;
//    		}
//    	}
//    	System.out.println("Column is fine");
//    	//check each square
//    	for(int i = 0; i<board.length; i+=3) 
//    	{
//    		for(int j = 0; j<board[i].length; j+=3) 
//    		{
//    			if(rejSqr(temp,i,j)) 
//    			{
//    				System.out.print("Square is invalid");
//    				return true;
//    			}
//    		}
//    	}
//    	System.out.println("Square is fine, returning false");
//    	return false;
//    } 
    
    static boolean reject(int[][] board) {
    	int[][] temp = board;// Arrays.copyOf(board, 9);
    	//System.out.println("I run reject");
    	//printBoard(temp);
//    	System.out.println("");
//    	System.out.println("Beginning reject");
    //	System.out.println("recC: "+recC + " recR: "+recR);
    	
    	int sqrR = recR - recR%3;
    	int sqrC = recC - recC%3;
    	if((sqrR<9) && (sqrC<9))
    	{
	    	if(rejSqr(temp,sqrR,sqrC))
	    	{
//	    		if(temp[recR][recC] == 9 && !(recR == 0 && recC == 0)) {
//	    			if(recC==0 )
//	        		{
//	        			recR--;
//	        			recC=8;
//	        		}
//	        		
//	        		else 
//	        		{	
//	        			recC--;
//	        		}
//	    		}
	    	//	System.out.print("Square is invalid");
	    		return true;
	    	}
    	}
    	//System.out.println("Square is fine");
    	if(rejRow(temp[recR]))
    	{
//    		if(temp[recR][recC] == 9 && !(recR == 0 && recC == 0)) {
//    			if(recC==0 )
//        		{
//        			recR--;
//        			recC=8;
//        		}
//        		
//        		else 
//        		{	
//        			recC--;
//        		}
//    		}
    		//System.out.print("Row is invalid");
    		return true;
    	}
    	//System.out.println("Row is fine");
    	if(rejCol(temp,recC))
    	{
//    		if(temp[recR][recC] == 9 && !(recR == 0 && recC == 0)) {
//    			if(recC==0 )
//        		{
//        			recR--;
//        			recC=8;
//        		}
//        		
//        		else 
//        		{	
//        			recC--;
//        		}
//    		}
    		//System.out.print("Column is invalid");
    		return true;
    	}
    	//System.out.println("Column is fine");
      return false;
    }
    //Doesn't work w prewritten and recurse input when the recurse input is prior to the prewritten
    private static boolean rejRow(int[] row) 
    {	int[] temp = Arrays.copyOf(row, 9);
    	Arrays.fill(aux, Boolean.FALSE);
    	boolean done = false;
    	int i =0;
    	while(i<9 && !done) 
    	{
    		//System.out.println(Arrays.toString(aux));
    		if(temp[i]>9)
    		{
    			if(aux[temp[i] - 10])
    			{
    				done = true;
    			}
    			else
    			{
    				aux[temp[i]-10]=true;
    			}
    		}
    		else if(temp[i]<10 && temp[i]>0)
    		{
	    		if(aux[temp[i]]) 
	    		{
	    			done = true;
	    		}
	    		else 
	    		{
	    			aux[temp[i]]= true;
	    		}
    		}
    		i++;
    	}
    	//System.out.println(i+""+constant);
    	return done;
    }
    
    private static boolean rejCol(int[][] board, int colNum) 
    {
    	 int[][] temp =Arrays.copyOf(board, 9); 
    	 Arrays.fill(aux, Boolean.FALSE);
    	boolean done = false;
    	int i = 0;
    	while(i<9 && !done) 
    	{
    		 if(temp[i][colNum]>9)
    		 {
    			 if(aux[temp[i][colNum]-10])
    			 {
    				 done = true;
    			 }
    			 else 
    			 { 
    				 aux[temp[i][colNum]-10]= true;
    			 }
    		 }
    		 else if(temp[i][colNum]>0&&temp[i][colNum]<10)
    		 {  
    			 if(aux[temp[i][colNum]]) 
    		 	 {
	    			done=true;
	    		 }
	    		 else 
	    		 {
	    			aux[temp[i][colNum]]=true;
	    		 }
    		 }
    		i++;
    	}
    	return done;
    }
    private static boolean rejSqr(int[][] board, int rowNum, int colNum) 
    {
    	Arrays.fill(aux, Boolean.FALSE);
    	 int[][] temp =Arrays.copyOf(board, 9);
    	 int x1 = rowNum; int y1= colNum;
    	int x2 = rowNum + 3; int y2 = colNum + 3;
    	for (int i=x1; i<x2; i++) 
    	{
    		for(int j=y1; j<y2; j++) 
    		{   
    			//System.out.println(Arrays.toString(aux));
    			//System.out.println(temp[i][j]);
    			if(temp[i][j]>9)
    			{
    				if(aux[temp[i][j]-10])
    				{
    					return true;
    				}
    				else
    				{
    					aux[temp[i][j]-10]=true;
    				}
    			}
    			else if(temp[i][j]>0 && temp[i][j]<10)
    			{	
    				if(aux[temp[i][j]])
	    			{	
	    				return true;
	    			}
	    			else
	    			{	
	    				aux[temp[i][j]] = true;
	    			}
    			}
    		}
    	}	
    	return false;
    }
   /*** OLD EXTEND METHOD */
// Extends solution by making next empty part 1    
//      static int[][] extend(int[][] board) 
//      {
//          int[][]temp = new int[9][9];
//    	  boolean done = false;
//          for(int i=0;i<board.length;i++) 
//          {
//         	for(int j=0; j<9;j++) 
//         	{
//        		if(board[i][j]!=0) 
//        		{
//        			temp[i][j] = board[i][j];
//        		}
//    			else if(board[i][j]==0 && !done) 
//    			{
//    				temp[i][j]=1;	
//    				recR=i;
//    				recC=j;
//    				done = true;
//            	}
//    			else 	 1
//    			{
//    				temp[i][j]=0;
//    			}
//        	}
//         }
//         if(done)
//         {	 
//        	 return temp;
//         }
//         return null;
//     }
    
      static int[][] extend(int[][] board)
      {   //System.out.println("Beginning extend");
    	  int[][] temp =Arrays.copyOf(board, 9);
    	  //printBoard(temp);
    	  
    	  boolean done = false;
    	  while(!done&&recC<9&&recR<9)
    	  {
    		if(recC==8&&recR==8&&temp[recR][recC]!=0)
    			return null;
    		else if(temp[recR][recC]!=0) 
    		{
    			if(recC==8)
    			{
    				recC=0; recR++;
    			}
    			else
    			{
    				recC++;
    			}
    		}
    		else if(temp[recR][recC]==0)
    		{
    			temp[recR][recC] = 1;
    			done = true;
    		}
    	  }
    	   	
   		return temp;
     }
    
    static int[][] next(int[][] board)
    {	//System.out.println("Beginning Next");
    	 int[][] temp = Arrays.copyOf(board, 9);
    	//System.out.println(temp[recR][recC]);
    	//printBoard(temp);
		//System.out.println("Row : " + recR + " Col : " + recC+ temp[recR][recC]);
    	if(temp[recR][recC]>=9) 
    	{	
    		//System.out.println("Inside");
    		if(temp[recR][recC]==9) {
    			//System.out.println("HEY");
    			//System.out.println(temp[4][0]);
    			temp[recR][recC]=0;
    			//System.out.println(temp[4][0]);
    		}
    		if(recC==0 )
    		{
    			recR--;
    			recC=8;
    		}
    		
    		else 
    		{	
    			recC--;
    		}
	   		while(temp[recR][recC] > 10) 
	   		{
	    		if(recC==0 )
	    		{
	    			recR--;
	    			recC=8;
	    		}
	    		
	    		else 
	    		{	
	    			recC--;
	    		}
	   		}
    		//System.out.println("After - Row : " + recR + " Col : " + recC+ temp[recR][recC]);
    		return null;
    	}
    	
    	else if(temp[recR][recC]<9)	
    	{	
    		//System.out.println("Hello"); 
    		temp[recR][recC] = temp[recR][recC]+1;
    	}
    	return temp;
    }
    static void testIsFullSolution() 
    {
    	//Test is Full
    	int[][] notFull = new int[][] 
    	{
        	{1,0,2,3,4,5,6,7,8},
        	{2,2,3,4,5,6,7,8,9},
        	{3,2,3,4,5,6,7,8,9},
        	{4,2,3,4,5,6,7,8,9},
        	{5,2,3,4,5,6,7,8,9},
        	{6,2,3,4,5,6,7,8,9},
        	{7,2,3,4,5,6,7,8,9},
        	{8,2,3,4,5,6,7,8,9},
        	{9,2,3,4,5,6,7,8,9}
        };
        
        int[][] mixFull = new int[][] 
        {
        	{1,15,2,3,4,5,6,7,8},
        	{2,2,3,4,5,6,7,18,9},
        	{3,2,13,4,5,6,7,8,9},
        	{4,2,3,4,15,6,7,8,9},
        	{5,2,13,4,5,6,7,8,9},
        	{6,12,3,4,15,6,7,8,9},
        	{7,2,3,4,5,6,7,8,19},
        	{8,2,3,14,5,6,7,8,9},
        	{19,2,3,4,5,6,7,8,9}
        };
                
        int[][] full = new int[][] 
        {
        	{1,9,2,3,4,5,6,7,8},
        	{2,2,3,4,5,6,7,8,9},
        	{3,2,3,4,5,6,7,8,9},
        	{4,2,3,4,5,6,7,8,9},
        	{5,2,3,4,5,6,7,8,9},
        	{6,2,3,4,5,6,7,8,9},
        	{7,2,3,4,5,6,7,8,9},
        	{8,2,3,4,5,6,7,8,9},
        	{9,2,3,4,5,6,7,8,9}
        };
        int[][] emptArr = new int[][] 
        {
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0}
        };
        int[][] comSol = new int[][] 
        {
        	{17,9,3,2,5,6,4,18,1},
        	{2,6,5,11,4,18,13,7,9},
        	{4,11,8,7,19,3,2,5,16},
        	{11,3,2,18,7,14,16,19,5},
        	{9,8,4,5,6,1,7,3,2},
        	{5,17,16,19,3,12,8,1,14},
        	{18,4,9,6,11,7,5,12,3},
        	{6,5,17,13,2,19,1,4,8},
        	{3,12,1,4,8,5,9,6,17}
        };
        System.out.println("TEST IS FULL");
        System.out.println("This isn't full: ");
        System.out.println(isFullSolution(notFull));
        System.out.println(isFullSolution(emptArr));
        System.out.println("This is full: ");
        System.out.println(isFullSolution(full));
        System.out.println(isFullSolution(comSol));
        System.out.println("A board with prewrit and single digits");
        System.out.println(isFullSolution(mixFull));
    }

    static void testReject() 
    {
    	testRejRow();
    	testRejCol();
    	testRejSqr();
    	recR=0; recC=0;
    	int[][] rej1 = new int[][]
    	        {
    	        	{1,9,2,6,8,5,3,4,7},
    	        	{6,3,8,2,4,1,5,9,9},
    	        	{4,7,9,3,9,9,1,8,2},
    	        	{2,1,4,5,9,3,7,6,8},
    	        	{7,5,3,1,2,6,4,9,9},
    	        	{9,9,6,8,7,9,9,1,5},
    	        	{9,8,1,7,5,2,9,9,3},
    	        	{9,2,7,9,3,8,6,5,4},
    	        	{3,6,5,9,1,4,2,7,9},
    	        };
    	
    	System.out.println("Board 1: Being printed as solution, but obviously wrong");
    	System.out.println(reject(rej1));
    	System.out.println("Board 1: Isolated Square Reject Test");
    	System.out.println(rejSqr(rej1,recR,recC));
    	System.out.println("Board 1: Isolated Row Reject Test");
    	System.out.println(rejRow(rej1[1]));
    	System.out.println("Board 1: Isolated Column Reject Test");
    	System.out.println(rejCol(rej1,0));
      }
    private static void testRejRow() 
    {
    	int[][] rRow1 = new int[][] 
    	{
        		{1,2,3,4,15,16,1,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
             	{0,0,0,0,0,0,0,0,0},
        };
        int[] rRow2 = new int[] {12,5,0,15,16,0,11,19,17};
        int[] rRow3 = new int[] {1,2,3,4,5,6,7,8,9};
        int[] rRow4 = new int[] {1,2,3,4,0,0,7,8,9};
        int[] rRow5 = new int[] {11,2,3,5,1,0,17,18,19};
        int[] rRow6 = new int[] {11,2,0,12,15,0,0,0,0};
        int[] rRow7 = new int[] {11,2,11,13,14,0,0,0,0};
    	System.out.println("This row has a duplicate:");
    	System.out.println(rejRow(rRow1[0]));
    	System.out.println("This row is wrong but it works for some reason: ");
    	System.out.println(rejRow(rRow2));
    	System.out.println("This row is totally legal");
    	System.out.println(rejRow(rRow3));
    	System.out.println("This row is legal and has zeroes");
    	System.out.println(rejRow(rRow4));
    	System.out.println("Testing duplicate where one # is prewritten and other is recursively inputted");
    	System.out.println(rejRow(rRow5));
    	System.out.println("Row 5 worked, testing one where recurse-input is before the prewritten # in the array");
    	System.out.println(rejRow(rRow6));
    	System.out.println("Test a row that's duplicate comes from the puzzle itself");
    	System.out.println(rejRow(rRow7));
    }
    private static void testRejCol() 
    {
    	int[][] rejCol0 = new int[][] 
    	{
        	{11,0,0,0,0,0,0,0,0},
        	{12,0,0,0,0,0,0,0,0},
        	{5,0,0,0,0,0,0,0,0},
        	{6,0,0,0,0,0,0,0,0},
        	{7,0,0,0,0,0,0,0,0},
        	{5,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0}
        };
        int[][] rejCol1 = new int[][] 
        {
        	{1,2,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{3,0,0,0,0,0,0,0,0},
        	{4,0,0,0,0,0,0,0,0},
        	{5,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{7,0,0,0,0,0,0,0,0},
        	{8,0,0,0,0,0,0,0,0},
        	{9,0,0,0,0,0,0,0,0},
        };	
        int[][] rejCol2 = new int[][] 
        {
        	{11,0,0,0,0,0,0,0,0},
        	{12,0,0,0,0,0,0,0,0},
        	{3,0,0,0,0,0,0,0,0},
        	{4,0,0,0,0,0,0,0,0},
        	{7,0,0,0,0,0,0,0,0},
        	{5,0,0,0,0,0,0,0,0},
        	{8,0,0,0,0,0,0,0,0},
        	{9,0,0,0,0,0,0,0,0},
        	{16,0,0,0,0,0,0,0,0}
        };
        
        int[][] rejCol3 =new int[][]
        {
        	{0,11,0,0,0,0,0,0,0},
        	{0,12,0,0,0,0,0,0,0},
        	{0,3,0,0,0,0,0,0,0},
        	{0,11,0,0,0,0,0,0,0},
        	{0,7,0,0,0,0,0,0,0},
        	{0,5,0,0,0,0,0,0,0},
        	{0,8,0,0,0,0,0,0,0},
        	{0,9,0,0,0,0,0,0,0},
        	{0,16,0,0,0,0,0,0,0}
        };
        System.out.println("REJECT COLUMN");
        System.out.println("Column 1: Two identical recursive input values");
    	System.out.println(rejCol(rejCol0,0));
    	System.out.println("Column 2: Not duplicate with zeroes in column");
    	System.out.println(rejCol(rejCol1,0));
    	System.out.println("Column 3: No duplicate with user input and recursive input");
    	System.out.println(rejCol(rejCol2,0));
    	System.out.println("Column 4: Duplicate prewrit values in different column.");
    	System.out.println(rejCol(rejCol3,1));
    }
    private static void testRejSqr() 
    {
    	int[][] rejSqr1 = new int[][] 
    	{
        	{1,2,16,0,0,0,0,0,0},
        	{3,5,7,0,0,0,0,0,0},
        	{18,9,2,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0}
        };
        int[][] rejSqr2 = new int[][] 
        {
        	{1,2,16,0,0,0,0,0,0},
        	{3,5,7,0,0,0,0,0,0},
        	{18,9,4,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0}
        };
        int[][] rejSqr3 = new int[][] 
        {
        	{1,0,16,0,0,0,0,0,0},
        	{3,0,7,0,0,0,0,0,0},
        	{18,9,4,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0}
        };
        int[][] rejSqr4 = new int[][] 
        {
           {1,2,16,0,0,0,0,0,0},
           {3,16,7,0,0,0,0,0,0},
           {18,9,4,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0},
           {0,0,0,0,0,0,0,0,0}
        };
        recR=0; recC=0;
        System.out.println("REJECTING SQUARE");
    	System.out.println("Square 1: There is a duplicate: both inputted");
        System.out.println(rejSqr(rejSqr1,0,0));
    	System.out.println("Square 2: ");
    	System.out.println(rejSqr(rejSqr2,0,0));
    	System.out.println("Square 3:  won't get rejected. Has zeroes");
    	System.out.println(rejSqr(rejSqr3,0,0));
    	System.out.println("Square 4: Rejection from duplicate prewrit numbers");
    	System.out.println(rejSqr(rejSqr4,0,0));
    }
    static void testExtend() 
    {
    	int[][] extBrd0 = new int[][] 
    	{
        	{1,3,4,5,6,7,8,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,0,0,0,0,0,0,0,0}
        };
        int[][] extBrd1 = new int[][] 
        {
        	{0,12,14,0,16,17,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
         };
         int[][] extBrd2 = new int[][] 
         {
        	 {1,2,14,5,6,7,9,8,3},
             {0,13,17,19,11,12,0,14,16},
             {0,0,0,0,0,0,0,0,0},
             {0,0,0,0,0,0,0,0,0},
             {0,0,0,0,0,0,0,0,0},
             {0,0,0,0,0,0,0,0,0},
             {0,0,0,0,0,0,0,0,0},
             {0,0,0,0,0,0,0,0,0},
             {0,0,0,0,0,0,0,0,0} 
         };
         //Complete Solution 1: can't be extended
        int[][] extBrd3 = new int[][] 
        {
        	{4,8,2,7,3,5,9,1,6},
        	{5,1,6,8,9,2,3,4,7},
        	{7,3,9,4,6,1,5,8,2},
        	{3,2,4,9,5,7,8,6,1},
        	{6,9,5,1,4,8,2,7,3},
        	{8,7,1,6,2,3,4,5,9},
        	{9,6,8,3,7,4,1,2,5},
        	{2,4,3,5,1,6,7,9,8},
        	{1,5,7,2,8,9,6,3,4}
        };
        
    	System.out.println("Standard First Row: ");
    	printBoard(extend(extBrd0));
    	System.out.println("First Element must be extended: ");
    	recR=0;recC=0;
    	printBoard(extend(extBrd1));
    	System.out.println("Second Row First Element extension: ");
    	printBoard(extend(extBrd2));
    	System.out.println("Full Solution can't be further extended: ");
    	printBoard(extend(extBrd3));
    }

    static void testNext() 
    {
    	int[][] nxtBrd0= new int[][]{
    			{1,2,3,4,5,6,7,8,0},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    			{1,2,3,4,5,6,7,8,9},
    	};
    	
    	int[][] nxtBrd1 = new int[][] 
    	{
    		{0,1,13,15,4,6,7,8,0},
    		{2,6,5,11,0,18,13,0,0},
        	{4,11,8,0,19,0,0,0,16},
        	{11,0,0,18,0,14,16,19,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,17,16,19,0,12,0,0,14},
        	{18,0,0,0,11,0,0,12,0},
        	{0,0,17,13,0,19,0,0,0},
        	{0,12,0,0,0,0,0,0,17}
    	};
    	int[][] nxtBrd2 = new int[][]
    	{
    		{9,9,3,2,5,6,4,18,1},
        	{2,6,5,11,4,18,13,7,9},
        	{4,11,8,7,19,3,2,5,16},
        	{11,3,2,18,7,14,16,19,5},
        	{9,8,4,5,6,1,7,3,2},
        	{5,17,16,19,3,12,8,1,14},
        	{18,4,9,6,11,7,5,12,3},
        	{6,5,17,13,2,19,1,4,8},
        	{3,12,1,4,8,5,9,6,17}		
    	};
    	//Extend at (3,2), and then next ups it.
    	int[][] nxtBrd3 = new int[][]
    	{
    		{1,9,3,2,5,6,4,18,1},
        	{2,6,5,11,4,18,13,7,9},
        	{4,11,8,7,19,3,2,5,16},
        	{11,3,0,18,7,14,16,19,5},
        	{9,8,4,5,6,1,7,3,2},
        	{5,17,16,19,3,12,8,1,14},
        	{18,4,9,6,11,7,5,12,3},
        	{6,5,17,13,2,19,1,4,8},
        	{3,12,1,4,8,5,9,6,17}
    	};
    	//the coordinate is 9, but the one before is also prewritten, so what do?
    	int[][] nxtBrd4 = new int[][]
    	{
    	 	{1,9,3,2,5,6,4,18,1},
    	    {2,6,5,11,4,18,13,7,9},
    	    {4,11,8,7,19,3,2,5,16},
    	    {11,9,18,7,14,16,19,5},
    	    {9,8,4,5,6,1,7,3,2},
    	    {5,17,16,19,3,12,8,1,14},
    	    {18,4,9,6,11,7,5,12,3},
    	    {6,5,17,13,2,19,1,4,8},
    	    {3,12,1,4,8,5,9,6,17}
    	 };
    	 int[][] nxtBrd5 = new int[][] {
    		{1,9,2,6,8,0,3,4,7},
     	    {6,0,0,0,0,0,5,0,0},
     	    {4,0,9,0,0,0,0,8,0},
     	    {0,0,0,0,9,3,7,6,0},
     	    {0,5,0,1,0,6,0,9,0},
     	    {0,9,6,8,7,0,0,0,0},
     	    {0,8,0,0,0,0,9,0,3},
     	    {0,0,7,0,0,0,0,0,4},
     	    {3,6,5,0,1,4,2,0,0} 
    	 };
    	System.out.println("TEST NEXT");
    	System.out.println("Board 1: Typical next in line 1");
    	recR=0; recC=8;
    	printBoard(next(nxtBrd0));
    	System.out.println("Board 2: Next line 1 with multiple 0s");
    	System.out.println("Should look like:" + "{0,1,3,5,4,6,7,9,0}");
    	recR=0; recC=7;
    	System.out.println("");
    	printBoard(next(nxtBrd1));
    	System.out.println("Because  it is null, recC should have been subtracted to -1");
    	System.out.println("recR: "+recR+" recC: "+recC);
    	System.out.println("");
    	System.out.println("Basic Next works, now: Testing extend into next.");
    	recR=3; recC=2;
    	printBoard(next(extend(nxtBrd3)));
    	System.out.println("Ideally, recR and recC will change if maxed. If column column is 0, recR-- && recC==8");
    	recR=4; recC=0;
    	System.out.println("PRE: recR: "+recR+" recC: "+recC);
    	printBoard(next(nxtBrd3));
    	System.out.println("Post: recR: "+recR+" recC: "+recC);
    	System.out.println("Board 5: The [0,1] should turn to 0 and [0,0] turn to 2");
    	recR=0;recC=0;
    	printBoard(next(nxtBrd5));
    	System.out.println("But it doesn't do that :/");
    }

    static void testPrintBoard() 
    {
    	//Test Print Board: Has empty spots
        int[][] pb1 = new int[][] {
        	{17,9,3,0,0,0,0,18,0},
        	{2,6,5,11,0,18,13,0,0},
        	{4,11,8,0,19,0,0,0,16},
        	{11,0,0,18,0,14,16,19,0},
        	{0,0,0,0,0,0,0,0,0},
        	{0,17,16,19,0,12,0,0,14},
        	{18,0,0,0,11,0,0,12,0},
        	{0,0,17,13,0,19,0,0,0},
        	{0,12,0,0,0,0,0,0,17}
        };
       //Test Print Board 2: Full solution with double digit (pre-written) and single digit (written) numbers.
        int[][] pb2 = new int[][] {
        	{17,9,3,2,5,6,4,18,1},
        	{2,6,5,11,4,18,13,7,9},
        	{4,11,8,7,19,3,2,5,16},
        	{11,3,2,18,7,14,16,19,5},
        	{9,8,4,5,6,1,7,3,2},
        	{5,17,16,19,3,12,8,1,14},
        	{18,4,9,6,11,7,5,12,3},
        	{6,5,17,13,2,19,1,4,8},
        	{3,12,1,4,8,5,9,6,17}
        };
      //Test Print Board 3: Full Solution with only single digit numbers
        int[][] pb3 = new int[][] {
        	{9,5,3,7,4,6,2,8,1},
        	{1,8,4,5,9,2,6,7,3},
        	{7,6,2,1,8,3,5,4,9},
        	{6,2,7,3,5,9,8,1,4},
        	{8,3,5,6,1,4,9,2,7},
        	{4,1,9,2,7,8,3,6,5},
        	{2,7,1,8,3,5,4,9,6},
        	{5,9,6,4,2,7,1,3,8},
        	{3,4,8,9,6,1,7,5,2}
        };
    	System.out.println("Board 1:");
    	printBoard(pb1);
    	System.out.println("Board 2:");
    	printBoard(pb2);
    	System.out.println("Board 3:");
    	printBoard(pb3);
    }
    
    static void printBoard(int[][] board) 
    {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
            	if (j == 2 || j == 5) {
            		if(board[i][j]>9) 
            		{
            			System.out.print(board[i][j]-10  + " | ");
            		}
            		else
            		{
            			System.out.print(board[i][j] + " | ");
            		}
                } else {
                	if(board[i][j]>9) 
            		{
            			System.out.print(board[i][j]-10);
            		}
            		else {
                    System.out.print(board[i][j]);
            		}
            	}
            }
            System.out.print("\n");
        }
    }
    
    static int[][] readBoard(String filename) 
    {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = 10+Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
             }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
    	if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) {
        	//System.out.println("running while loop");
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
      //  System.out.println("Im out of the solve loop");
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
            testPrintBoard();
        } else {
             int[][] board = readBoard(args[0]);
            System.out.println("Sudoku: ");
            System.out.println("");
            printBoard(board);
            System.out.println("Solution:");
            System.out.println("");
            printBoard(solve(board));
        }
    };
}

