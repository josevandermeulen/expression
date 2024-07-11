package regexpr;
import util.*;

public class FoldOld {
	
	
  final IExpressions exprs ;
  
  static final boolean F = true ;
  static final boolean B = ! F ;

	
	public FoldOld(Background exprs)
	{
		this.exprs = exprs ;

 	}
 	
  int[] concatToArray(final int iExpr)
  // E is of the form T1 . (T2 . ( ... . Tn) ) ) (n >= 1)
  // {T1, ..., Tn} is returned.
  {
  	int n = 1 ;
  	int iT = iExpr ;
  	while (exprs.type(iT) == Expressions.CONCAT)
  	{
  		iT = exprs.tabS(iT)[1] ;
  		n ++ ;
  	}
  	
  	int[] a = new int[n] ;
  	int i = 0 ;
  	iT = iExpr ;
  	while (exprs.type(iT) == Expressions.CONCAT)
  	{
  		a[i] = exprs.tabS(iT)[0] ;
  		iT   = exprs.tabS(iT)[1] ;
  		i ++ ;
  	}
  	a[i] = iT ;
  	
  	return a ;
  } 	
  
  int[][] toArrayOfArrays(final int iExpr)
  // E is of the form E1 + E2 + ... + En (n >= 0)
  // {tabE1, ..., tabEn} is returned where tabEi = concatToArray(Ei}
  {
  	int[] tabE = exprs.tabE(iExpr) ;
  	int[][] ttabE = new int[tabE.length][] ;
  	
  	int i = 0 ;
  	while (i != tabE.length)
  	{
  		ttabE[i] = concatToArray(tabE[i]) ;
  		i ++ ;
  	}
  	return ttabE ;
  }
 	
 	int compareArraysF(int[] a, int[] b) // forward
 	// if a < b --> < 0
 	// if a = b --> 0
 	// if a > b --> > 0
 	{
 		int i = 0 ;
 		while (i != a.length && i != b.length && a[i] == b[i])
 			i ++ ;
 		
 		if (i != a.length && i != b.length)
 			return a[i] - b[i] ; 
 		
 		return a.length - b.length ;
 	}
 	
 	int compareArraysB(int[] a, int[] b) // backward
 	// if a < b --> < 0
 	// if a = b --> 0
 	// if a > b --> > 0
 	{
 		int i = a.length ;
 		int j = b.length ;
 		while (i != 0 && j != 0 && a[i - 1] == b[j - 1])
 			{	i -- ; j -- ; }
 		
 		if (i != 0 && j != 0)
 			return a[i - 1] - b[j - 1] ; 
 		
 		return i - j ;
 	}
 	
 	int compareArrays(int[] a, int[] b, boolean dir)
 	{
 		if (dir == F)
 			return compareArraysF(a, b) ;
 		else
 			return compareArraysB(a, b) ;
 	}
 	
 	
 	int compareF(int iExpr1, int iExpr2)
 	{
 		int[] tab1 = concatToArray(iExpr1) ;
 		int[] tab2 = concatToArray(iExpr2) ;
 		return compareArraysF(tab1, tab2) ;
 	}
 	
  int compareB(int iExpr1, int iExpr2)
 	{
 		int[] tab1 = concatToArray(iExpr1) ;
 		int[] tab2 = concatToArray(iExpr2) ;
 		return compareArraysB(tab1, tab2) ;
 	}
 	
 	int[] mergeF(int[] tabE1, int[] tabE2)
 	{
 		//System.out.print("tabE1 : ") ;
 		//printTab(tabE1) ;
 		
 		//System.out.print("tabE2 : ") ;
 		//printTab(tabE2) ;
 		
 		int n = 0 ;
 		int i1 = 0 ; int i2 = 0 ;
 		while (i1 != tabE1.length && i2 != tabE2.length)
 		{
 			n ++ ;
 			
 			int c = compareF(tabE1[i1], tabE2[i2]) ;
 			if (c <= 0)
 				i1 ++ ;
 			if (c >= 0)
 				i2 ++ ;
 			
 		}
 		
 		n += tabE1.length - i1 ;
 		n += tabE2.length - i2 ;
 		
 		int[] tabE12 = new int[n] ;
 		
 		i1 = 0 ; i2 = 0 ; n = 0 ;
 		while (i1 != tabE1.length && i2 != tabE2.length)
 		{ 			
 			int c = compareF(tabE1[i1], tabE2[i2]) ;
 			if (c < 0)
 				tabE12[n] = tabE1[i1 ++] ;
 			if (c > 0)
 				tabE12[n] = tabE2[i2 ++] ;
 			if (c == 0)
 			{
 				tabE12[n] = tabE1[i1] ;
 				i2 ++ ; 
 			}
      n ++ ;
 		}
 		
 		while (i1 != tabE1.length)
 			tabE12[n ++] = tabE1[i1 ++] ;
 		
  	while (i2 != tabE2.length)
 			tabE12[n ++] = tabE2[i2 ++] ;
 		
 		 		
 		//System.out.print("tabE12 : ") ;
 		//printTab(tabE12) ;

 		
 		return tabE12 ;
 	}
    	
 	int[][] mergeF(int[][] tabE1, int[][] tabE2)
 	{
 		//System.out.print("tabE1 : ") ;
 		//printTab(tabE1) ;
 		
 		//System.out.print("tabE2 : ") ;
 		//printTab(tabE2) ;
 		
 		int n = 0 ;
 		int i1 = 0 ; int i2 = 0 ;
 		while (i1 != tabE1.length && i2 != tabE2.length)
 		{
 			n ++ ;
 			
 			int c = compareArraysF(tabE1[i1], tabE2[i2]) ;
 			if (c <= 0)
 				i1 ++ ;
 			if (c >= 0)
 				i2 ++ ;
 			
 		}
 		
 		n += tabE1.length - i1 ;
 		n += tabE2.length - i2 ;
 		
 		int[][] tabE12 = new int[n][] ;
 		
 		i1 = 0 ; i2 = 0 ; n = 0 ;
 		while (i1 != tabE1.length && i2 != tabE2.length)
 		{ 			
 			int c = compareArraysF(tabE1[i1], tabE2[i2]) ;
 			if (c < 0)
 				tabE12[n] = tabE1[i1 ++] ;
 			if (c > 0)
 				tabE12[n] = tabE2[i2 ++] ;
 			if (c == 0)
 			{
 				tabE12[n] = tabE1[i1] ;
 				i2 ++ ; 
 			}
      n ++ ;
 		}
 		
 		while (i1 != tabE1.length)
 			tabE12[n ++] = tabE1[i1 ++] ;
 		
  	while (i2 != tabE2.length)
 			tabE12[n ++] = tabE2[i2 ++] ;
 		
 		 		
 		//System.out.print("tabE12 : ") ;
 		//printTab(tabE12) ;

 		
 		return tabE12 ;
 	}
   
 	    	
 	int[][] merge(int[][] tabE1, int[][] tabE2, boolean dir)
 	{
 		//System.out.print("tabE1 : ") ;
 		//printTab(tabE1) ;
 		
 		//System.out.print("tabE2 : ") ;
 		//printTab(tabE2) ;
 		
 		int n = 0 ;
 		int i1 = 0 ; int i2 = 0 ;
 		while (i1 != tabE1.length && i2 != tabE2.length)
 		{
 			n ++ ;
 			
 			int c = compareArrays(tabE1[i1], tabE2[i2], dir) ;
 			if (c <= 0)
 				i1 ++ ;
 			if (c >= 0)
 				i2 ++ ;
 			
 		}
 		
 		n += tabE1.length - i1 ;
 		n += tabE2.length - i2 ;
 		
 		int[][] tabE12 = new int[n][] ;
 		
 		i1 = 0 ; i2 = 0 ; n = 0 ;
 		while (i1 != tabE1.length && i2 != tabE2.length)
 		{ 			
 			int c = compareArrays(tabE1[i1], tabE2[i2], dir) ;
 			if (c < 0)
 				tabE12[n] = tabE1[i1 ++] ;
 			if (c > 0)
 				tabE12[n] = tabE2[i2 ++] ;
 			if (c == 0)
 			{
 				tabE12[n] = tabE1[i1] ;
 				i2 ++ ; 
 			}
      n ++ ;
 		}
 		
 		while (i1 != tabE1.length)
 			tabE12[n ++] = tabE1[i1 ++] ;
 		
  	while (i2 != tabE2.length)
 			tabE12[n ++] = tabE2[i2 ++] ;
 		
 		 		
 		//System.out.print("tabE12 : ") ;
 		//printTab(tabE12) ;

 		
 		return tabE12 ;
 	}
   
 	int[] mergeSortF(int[] tabE)
 	{
 		int[][] toSort = new int[tabE.length][] ;
 		
 		{
 			int i = 0 ;
 			while (i != tabE.length)
 			{
 				toSort[i] = new int[]{tabE[i]} ;
 				i ++ ;
 			}
 		}
 		
 		while (toSort.length > 1)
 		{
 			int[][] toSort2 = new int[(toSort.length + 1) / 2][] ;
 			
 			int i = 0 ;
 			int j = 0 ;
 			while (i + 1 < toSort.length)
 			{
 				toSort2[j] = mergeF(toSort[i], toSort[i + 1]) ;
 				j ++ ; i += 2 ;
 			}
 			
 			if (i != toSort.length)
 				toSort2[j] = toSort[i] ;
 			
 			toSort = toSort2 ;
 		}
 		
 		if (toSort.length == 1)
 			return toSort[0] ;
 		else
 			return new int[]{} ;
 	}
 	   
 	int[][] mergeSort(int[][] tabE, boolean dir)
 	{
 		int[][][] toSort = new int[tabE.length][][] ;
 		
 		{
 			int i = 0 ;
 			while (i != tabE.length)
 			{
 				toSort[i] = new int[][]{tabE[i]} ;
 				i ++ ;
 			}
 		}
 		
 		while (toSort.length > 1)
 		{
 			int[][][] toSort2 = new int[(toSort.length + 1) / 2][][] ;
 			
 			int i = 0 ;
 			int j = 0 ;
 			while (i + 1 < toSort.length)
 			{
 				toSort2[j] = merge(toSort[i], toSort[i + 1], dir) ;
 				j ++ ; i += 2 ;
 			}
 			
 			if (i != toSort.length)
 				toSort2[j] = toSort[i] ;
 			
 			toSort = toSort2 ;
 		}
 		
 		if (toSort.length == 1)
 			return toSort[0] ;
 		else
 			return new int[][]{} ;
 	}
 	
 	int firstTerm(int iExpr)
 	{
 		if (exprs.type(iExpr) == Expressions.CONCAT)
 			return exprs.tabS(iExpr)[0] ;
 		else
 			return iExpr ;
 	}
 	
 	int otherTerms(int iExpr)
 	{
 		if (exprs.type(iExpr) == Expressions.CONCAT)
 			return exprs.tabS(iExpr)[1] ;
 		else
 			return exprs.one() ;
 	}
 	
 	void printTab(int[] tabE)
 	{
 		int i = 0 ;
 		while (i != tabE.length) 
 		{
 			System.out.print(exprs.toString(tabE[i]) + " ; ") ;
 			i ++ ;
 		}
    System.out.println() ;
 	}
 	
 	int foldF(int iExpr) throws GCException
 	{
 		int[][] sortedTabEF =  mergeSort(toArrayOfArrays(iExpr), F) ;
 		int iExprF = fold(sortedTabEF, F) ;
 		int[][] sortedTabEB =  mergeSort(toArrayOfArrays(iExprF), B) ;
 		int iExprB = fold(sortedTabEF, B) ;
 		
 		return iExprB ;

 	}
 	
 	int fold(final int[] sortedTabE) throws GCException
 	{
 		
 		//printTab(sortedTabE) ;
 		
 		int n = 0 ;
 		int i = 0 ;
 		while (i != sortedTabE.length)
 		{
 			int iF = firstTerm(sortedTabE[i]) ;
 			int j = i + 1 ;
 			while (j != sortedTabE.length 
 				&& iF == firstTerm(sortedTabE[j]))
 				j ++ ;
 			i = j ;
 			n ++ ;
 		}
 		
 		int[] tabU = new int[n] ;
 		i = 0 ; n = 0 ;
 		while (i != sortedTabE.length)
 		{
 			int iF = firstTerm(sortedTabE[i]) ;
 			int j = i + 1 ;
 			int j0 = i ;
 			while (j != sortedTabE.length 
 				&& iF == firstTerm(sortedTabE[j]))
 				j ++ ;
 			i = j ;
 			tabU[n ++] = j - j0 ;
 		}
 		
 		int[] tabF = new int[n] ;
 		i = 0 ; n = 0 ;
 		while (i != sortedTabE.length)
 		{
 			int iF = firstTerm(sortedTabE[i]) ;
 			int[] tabOthers = new int[tabU[n]] ;
 			int j = i + 1 ;
 			int k = 1 ;
 			tabOthers[0] = otherTerms(sortedTabE[i]) ;
 			while (j != sortedTabE.length 
 				&& iF == firstTerm(sortedTabE[j]))
 			{
 				int iFj = firstTerm(sortedTabE[j]) ;
 				tabOthers[k ++] = otherTerms(sortedTabE[j]) ;
 				j ++ ; 				
 			}
 			
 			//System.out.print("tabOthers[" + n + "] : ") ;
 		  //printTab(tabOthers) ;
 			
 			int iOthers ;
 			if (tabOthers.length != 1)
 			  iOthers = fold(tabOthers) ;
 			else
 				iOthers = tabOthers[0] ;
 			
 			tabF[n ++] = exprs.concat(iF, iOthers) ;
 			i = j ;
 		}
 		
 		//System.out.print("tabF : ") ;
 		  //printTab(tabF) ;
 			
 		
 		return exprs.union(tabF) ;
 	}
 	
 	
 	int firstTerm(int[] tabE)
 	{
 		if (tabE.length >= 1)
 			return tabE[0] ;
 		else
 			return exprs.one() ;
 	}
 	
 	int[] otherTerms(int[] tabE)
 	{
 		if (tabE.length <= 1)
 			return new int[]{} ;
 		
 		int[] tabS = new int[tabE.length - 1] ;
 		int i = 0 ;
 		while (i != tabS.length)
 		{
 			tabS[i] = tabE[i + 1] ;
 			i ++ ;
 		}
 		return tabS ;
 	}
 	 		
 	int firstTerm(int[] tabE, boolean dir)
 	{
 		if (tabE.length >= 1)
 			if (dir == F)
 			   return tabE[0] ;
 			 else
 			 	 return tabE[tabE.length - 1] ;
 		else
 			return exprs.one() ;
 	}
 	
 	int[] otherTerms(int[] tabE, boolean dir)
 	{
 		if (tabE.length <= 1)
 			return new int[]{} ;
 		
 		int[] tabS = new int[tabE.length - 1] ;
 		
 		if (dir == F)
 		{
 			int i = 0 ;
 		  while (i != tabS.length)
 		  {
 			  tabS[i] = tabE[i + 1] ;
 			  i ++ ;
 		  }
 		}
 		else
 		{
 			int i = 0 ;
 		  while (i != tabS.length)
 		  {
 			  tabS[i] = tabE[i] ;
 			  i ++ ;
 		  }
 		}
 		return tabS ;
 	}
 	 	
 	int fold(final int[][] sortedTabE, boolean dir) throws GCException
 	{
 		
 		//printTab(sortedTabE) ;
 		
 		int n = 0 ;
 		int i = 0 ;
 		while (i != sortedTabE.length)
 		{
 			int iF = firstTerm(sortedTabE[i], dir) ;
 			int j = i + 1 ;
 			while (j != sortedTabE.length 
 				&& iF == firstTerm(sortedTabE[j], dir))
 				j ++ ;
 			i = j ;
 			n ++ ;
 		}
 		
 		int[] tabU = new int[n] ;
 		i = 0 ; n = 0 ;
 		while (i != sortedTabE.length)
 		{
 			int iF = firstTerm(sortedTabE[i], dir) ;
 			int j = i + 1 ;
 			int j0 = i ;
 			while (j != sortedTabE.length 
 				&& iF == firstTerm(sortedTabE[j], dir))
 				j ++ ;
 			i = j ;
 			tabU[n ++] = j - j0 ;
 		}
 		
 		int[] tabF = new int[n] ;
 		i = 0 ; n = 0 ;
 		while (i != sortedTabE.length)
 		{
 			int iF = firstTerm(sortedTabE[i], dir) ;
 			int[][] tabOthers = new int[tabU[n]][] ;
 			int j = i + 1 ;
 			int k = 1 ;
 			tabOthers[0] = otherTerms(sortedTabE[i], dir) ;
 			while (j != sortedTabE.length 
 				&& iF == firstTerm(sortedTabE[j], dir))
 			{
 				int iFj = firstTerm(sortedTabE[j], dir) ;
 				tabOthers[k ++] = otherTerms(sortedTabE[j], dir) ;
 				j ++ ; 				
 			}
 			
 			//System.out.print("tabOthers[" + n + "] : ") ;
 		  //printTab(tabOthers) ;
 			
 			int iOthers ;
 			if (tabOthers.length != 1)
 			  iOthers = fold(tabOthers, dir) ;
 			else
 				iOthers = exprs.bestExpr(exprs.concat(tabOthers[0])) ;
 			
 			if (dir == F)
 			   tabF[n ++] = exprs.bestExpr(exprs.concat(iF, iOthers)) ;
 			 else
 			 	 tabF[n ++] = exprs.bestExpr(exprs.concat(iOthers, iF)) ;
 			 
 			i = j ;
 		}
 		
 		return exprs.bestExpr(exprs.union(tabF)) ;
 	}
}




























