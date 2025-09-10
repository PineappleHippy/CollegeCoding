import java.io.*;
import java.util.*;

/************************** Driver to test your code **************************/

/* Sortness
 * Controls what numbers are generated for the arrays
 */
enum OrderOfData {
   RANDOM,
   INCREASING,
   DECREASING,
   DUPLICATE
}

/* Provided code - DO NOT CHANGE THIS CODE
 */
public class DriverRuntimeTable
{
   /* set enableTest[i] to false to disable the ith test */
   public static final boolean enableTest[] = {
         true,  // Insertion (sorted order)
         true,  // Insertion (reverse sorted order)
         true,  // quicksort (Random numbers)
         true,  // quicksort (duplicate numbers)
         true,  // Mystery 1
         true,  // Mystery 2
         true,  // Mystery 3
   };

   public static final int[] sizes1= { 4000, 8000, 16000, 32000, 64000, 128000, 256000, 512000, 1024000, 2048000, 4096000};

   public static void main(String[] args) throws IOException
   {
      RuntimeTable tableDS;

      System.out.println(  );
      RuntimeTable.printName( );
      System.out.println(  );

      tableDS = RuntimeTable.timeAlgorithm("quicksort (random)", 1, 9, sizes1, new ArrayAlgs("quickSortOptInitial"), OrderOfData.RANDOM ); //unprinted run to allocate memory ahead of time

      for( int i=0; i<=6; i++ ){
         if( enableTest[i] && i==0 )
            tableDS = RuntimeTable.timeAlgorithm("Insertion Sort (sorted order)", 4, 5, sizes1, new ArrayAlgs("insertionSortInitial"), OrderOfData.INCREASING );
         else if( enableTest[i] && i==1 )
            tableDS = RuntimeTable.timeAlgorithm("Insertion Sort (reverse sorted order)", 4, 4, sizes1, new ArrayAlgs("insertionSortInitial"), OrderOfData.DECREASING );
         else if( enableTest[i] && i==2 )
            tableDS = RuntimeTable.timeAlgorithm("quicksort (random)", 12, 8, sizes1, new ArrayAlgs("quickSortOptInitial"), OrderOfData.RANDOM );
         else if( enableTest[i] && i==3 )
            tableDS = RuntimeTable.timeAlgorithm("quicksort (duplicates)", 12, 5, sizes1, new ArrayAlgs("quickSortOptInitial"), OrderOfData.DUPLICATE );
         else if( enableTest[i] && i==4 )
            tableDS = RuntimeTable.timeAlgorithm("Mystery 1", 6, 8, sizes1, new ArrayAlgs("mysteryRuntime1"), OrderOfData.RANDOM );
         else if( enableTest[i] && i==5 )
            tableDS = RuntimeTable.timeAlgorithm("Mystery 2", 3, 4, sizes1, new ArrayAlgs("mysteryRuntime2"), OrderOfData.RANDOM );
         else if( enableTest[i] && i==6 )
            tableDS = RuntimeTable.timeAlgorithm("Mystery 3", 4, 5, sizes1, new ArrayAlgs("mysteryRuntime3"), OrderOfData.RANDOM );
         if( enableTest[i] )
            RuntimeTable.printRuntimeTable(tableDS);
      }

      RuntimeTable.printName( );
      System.out.println(  );
   }
}

/************************** Methods for timing functions and printing tables of the runtimes **************************/

/* TODO:
 * Give your asymptotic estimates for the runtimes of the following 3 functions:
 * mysteryRuntime1: O(n)
 * mysteryRuntime2: O(n^2)
 * mysteryRuntime3: O(n log n)
 *
 */

class RuntimeTable
{
   public static final String DATA_FILE_NAME = "TestData.txt";

   public String name;               //name of the function being tested
   public double[][] runtimeTable;   //table of runtimes (array #rows = numTestCaseSizes, #columns = numRepeats)
   public double[] averageArray;     //average runtimes (array length = numTestCaseSizes)
   public int[] testCaseSizesArray;  //array containing the test case sizes (array length = numTestCaseSizes)
   public int numRepeats;            //number of times to repeat each test size
   public int numTestCaseSizes;      //number of test case sizes

   /* TO BE COMPLETED BY YOU
    * Fill in your name in the function below
    */
   public static void printName( )
   {
      /* TODO : Fill in your name */
      System.out.println("This solution was completed by:");
      System.out.println("John Thompson - KLH551");
   }

   /* TO BE COMPLETED BY YOU
    * Fill in the missing parts of the code (see TODOs below)
    */
   public static RuntimeTable timeAlgorithm(String name, int numRepeats, int numTestCaseSizes, int[] testCaseSizesArray, ArrayAlgs sortingAlgorithm, OrderOfData order ) throws IOException
   {
      /* Call and calculate the runtime of the provided function */
      long start = 0, end = 0;
      int i, j;
      File testData;

      // create RuntimeTable variable to return
      RuntimeTable tableDS = new RuntimeTable();

      // TODO: copy passed data into the RuntimeTable variable
      /* fill name in tableDS with the variable name */
      tableDS.name = name;
      /* fill numRepeats in tableDS with the variable numRepeats */
      tableDS.numRepeats = numRepeats;
      /* fill numTestCaseSizes in tableDS with the variable numTestCaseSizes */
      tableDS.numTestCaseSizes = numTestCaseSizes;
      /* set testCaseSizesArray in tableDS to hold numTestCaseSizes number of ints */
      tableDS.testCaseSizesArray = new int[numTestCaseSizes];
      /* fill testCaseSizesArray in tableDS with the variable testCaseSizesArray (hint: use a loop) */
      for (int k = 0; k < numTestCaseSizes; k++) {
         tableDS.testCaseSizesArray[k] = testCaseSizesArray[k];
      }

      // TODO: set tableDS.runtimeTable to a new double 2D array of with numTestCaseSizes rows and numRepeats columns
      tableDS.runtimeTable = new double[numTestCaseSizes][numRepeats];
      for(i = 0; i < numTestCaseSizes; i++)
      {
         for(j = 0; j < numRepeats; j++)
         {
            //Generate test data for the function sortingAlgorithm
            testData = generateTestInput( 0, testCaseSizesArray[i], testCaseSizesArray[i], order );

            //Run sortingAlgorithm on the generated test data
            start = System.currentTimeMillis();
            sortingAlgorithm.executeAlg(testData);
            end = System.currentTimeMillis();

            //Enter the elapsed number of seconds into the arrRuntimes array for tableDS
            //TODO: uncomment the next line line after you've set a value for tableDS.runtimeTable
            tableDS.runtimeTable[i][j] = (double)(end - start) / 1000;
         }
      }
      //TODO: Calculate the average runtimes (create space for tableDS.averageArray and call computeAverages here)
      computeAverages(tableDS);

      return tableDS;
   }

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    */
   public static File generateTestInput(int min, int max, int size, OrderOfData order) throws IOException
   {
      int i;
      File data = new File(DATA_FILE_NAME);
      data.createNewFile();
      FileWriter writer = new FileWriter(data);
      writer.write(String.valueOf(size));
      writer.write(" ");
      for (i = 0; i < size; i++)
      {
         Random rand = new Random();
         if( order==OrderOfData.RANDOM )
            writer.write(String.valueOf(rand.nextInt(max - min + 1) + min));
         else if( order==OrderOfData.INCREASING )
            writer.write(String.valueOf(i));
         else if( order==OrderOfData.DECREASING )
            writer.write(String.valueOf(size-i));
         else if( order==OrderOfData.DUPLICATE )
            writer.write(String.valueOf(1337));
         else
            writer.write("ERROR");
         writer.write(" ");
      }
      writer.flush();
      writer.close();
      return data;
   }

   /* TODO: TO BE COMPLETED BY YOU
    * Calculate and insert the average runtime for each set of test data into tableDS
    */
   public static void computeAverages(RuntimeTable tableDS)
   {
      int numTestCaseSizes = tableDS.numTestCaseSizes;
      int numRepeats = tableDS.numRepeats;

      tableDS.averageArray = new double[numTestCaseSizes];
      for (int k = 0; k < numTestCaseSizes; k++){
         double sum = 0.0;
         for (int l = 0; l < numRepeats; l++) {
            sum += tableDS.runtimeTable[k][l];
         }
         double average = sum / numRepeats;
         tableDS.averageArray[k] = average;
      }
   }

   /* TODO: TO BE COMPLETED BY YOU
    * Print the information in tableDS as a 2d table.  Display 3 digits after the decimal point.
    * You can assume all of the runtimes are <= 99.999 seconds.  The number of repeats will be <= 14.
    *
    * The columns should each line up.  Using printf to create minimum width sizes for your printed variables should make this easier.
    *
    * The ith value in the increase column is calculated by dividing the ith average by the i-1th average.  If i=0, print "N/A" instead.
    * E.g. if i=3, increase = tableDS.averageArray[3]/tableDS.averageArray[2]
    */
   public static void printRuntimeTable(RuntimeTable tableDS)
   {
      int row = tableDS.numTestCaseSizes;
      int col = tableDS.numRepeats;

      //Table Header
      System.out.println(tableDS.name);
      System.out.printf("%-11s","Test size");
      for (int i = 1; i <= col; i++){
         System.out.printf("Test #%-3d", i);
      }
      System.out.println("Average  Increase");

            //"Test size Test #1 Test #2 Test #3 Test #4 Average Increase");

      //Main Table
      for (int i = 0; i < row; i++){
         System.out.printf("%-10d ", tableDS.testCaseSizesArray[i]);
         for (int j = 0; j < col; j++){
            System.out.printf("%-9.3f", tableDS.runtimeTable[i][j]);
         }
         System.out.printf("%-9.3f", tableDS.averageArray[i]);          //Average
         if (i == 0) System.out.println("N/A");
         else {
            double inc = tableDS.averageArray[i] / tableDS.averageArray[i-1];
            System.out.printf("%-9.3f\n", inc);
         }
      }

      System.out.println("\n");
   }
}

/************************** Sorting and Mystery Algorithms **************************/

class ArrayAlgs
{
   private String algName;

   public ArrayAlgs( String algName )
   {
      this.algName = algName;
   }

   public void executeAlg( File testData ) throws FileNotFoundException
   {
      if( algName.equals("insertionSortInitial") )
         insertionSortInitial(testData);

      else if( algName.equals("quickSortOptInitial") )
         quickSortOptInitial(testData);

      else if( algName.equals("mysteryRuntime1") )
         mysteryRuntime1(testData);

      else if( algName.equals("mysteryRuntime2") )
         mysteryRuntime2(testData);

      else if( algName.equals("mysteryRuntime3") )
         mysteryRuntime3(testData);

      else
         System.out.println("ERROR - Unknown Algorithm");
   }

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    * TODO: find the runtime of this code and put it in comment above RuntimeTable
    */
   static void mysteryRuntime1(File input) throws FileNotFoundException
   {
      int temp;
      int n;
      int i = 0;
      int[] array;

      Scanner sc = new Scanner(input);
      n = sc.nextInt();
      array = new int[n];
      while(sc.hasNextInt() && i<n)
      {
         temp = sc.nextInt();
         array[i] = temp;
         i++;                             //Runtime = O(n) : increments linearly until i reaches n.
      }
      sc.close();
      while (n > 1)
      {
         n = n/2;
         array[n/2] = array[n];           //Runtime = O(log n) : increments down exponentially
      }
   }                                      //Total Runtime = O(n) : when sequential take the most dominant

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    * TODO: find the runtime of this code and put it in comment above RuntimeTable
    */
   static void mysteryRuntime2(File input) throws FileNotFoundException
   {
      int temp;
      int n;
      int i = 0, j = 0;
      int[] array;

      Scanner sc = new Scanner(input);
      n = sc.nextInt();
      array = new int[n];

      while(sc.hasNextInt() && i<n)
      {
         temp = sc.nextInt();
         array[i] = temp;
         i++;
      }                                      //Runtime = O(n) : increments linearly until i reaches n
      sc.close();
      i = 0;
      while(j < n)
      {
         array[j] = array[i];
         i++;
         if(i >= n)
         {
            j++;
            i = 0;
         }
      }                                      //Runtime = O(n^2) : goes through the 1 loop, then increments the next loop once.
   }                                         //Total Runtime = 0(n^2) : when sequential take the most dominant.

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    * TODO: find the runtime of this code and put it in comment above RuntimeTable
    */
   static void mysteryRuntime3(File input) throws FileNotFoundException
   {
      int temp;
      int n;
      int j;
      int i = 0;
      int[] array;

      Scanner sc = new Scanner(input);
      n = sc.nextInt();
      array = new int[n];                    //Runtime = o(n) : fills in whole array with 0's

      while(sc.hasNextInt() && i<n)          //Runtime = O(n) : increments until reaches n
      {
         temp = sc.nextInt();
         array[i] = temp;
         i++;
      }

      sc.close();

      for(i = 0; i < n; i++)                 //Runtime = O(n) : increments until reaches n
      {
         for(j = n-1; j>1; j/=1.01)          //Runtime = O(log n) : shrinks by constant factor until less than 1.
         {
            array[j-1] = array[j];
         }
      }                                      //Combined Runtime = O(n log n) : nested loops multiply.
   }                                         //Total Runtime = O(n log n) : when sequential take the most dominant

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    */
   static void insertionSortInitial(File input) throws FileNotFoundException
   {
      int i;
      int size;
      int[] array;

      Scanner sc = new Scanner(input);
      size = sc.nextInt();
      array = new int[size];

      for(i = 0; i < size; i++)
      {
         array[i] = sc.nextInt();
      }
      insertionSort(array, 0, size-1);

      sc.close();

      // Error check to verify the array is sorted
    /*for(i = 1; i < size; i++)
     {
     if(array[i-1] > array[i])
     {
     System.out.println("Not sorted!");
     System.exit(-1);
     }
     }*/
   }

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    */
   static void insertionSort(int[] points, int low, int high)
   {
      int i, j;
      int temp; // fixed from double temp;
      for (i = low+1; i <= high; i++)
      {
         for (j = i; j > low && points[j] < points[j-1]; j--)
         {
            temp = points[j];
            points[j] = points[j-1];
            points[j-1] = temp;
         }
      }
   }

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    */
   static void quickSortOptInitial(File input) throws FileNotFoundException
   {
      int i;
      int size;
      int[] array;

      Scanner sc = new Scanner(input);
      size = sc.nextInt();
      array = new int[size];

      for(i = 0; i < size; i++)
      {
         array[i] = sc.nextInt();
      }
      quickSortOpt(array,0, size-1);

      sc.close();

      // Error check to verify the array is sorted
    /*for(i = 0; i < size; i++)
    {
      System.out.print(array[i] + " ");
      if(array[i-1] > array[i])
      {
        System.out.println("Not sorted!");
        System.exit(-1);
      }
    }
    System.out.println();*/
   }

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    */
   static void quickSortOpt(int[] points, int low, int high)
   {
      Stack<Pair> recCalls = new Stack<Pair>();
      recCalls.push( new Pair( low, high ) );

      while( !recCalls.isEmpty() ){
         Pair p = recCalls.pop( );
         if (p.high < p.low + 30)//Uses insertion sort when sorting <30 numbers
         {
            insertionSort(points, p.low, p.high);
         }
         else
         {
            int pivot = partition(points, p.low, p.high);
            recCalls.push( new Pair( p.low, pivot-1 ) );   //same as: quickSortOpt(points, p.low, pivot -1);
            recCalls.push( new Pair( pivot+1, p.high ) );  //same as: quickSortOpt(points, pivot+1, p.high);
         }
      }
   }

   /*
    * Provided code - DO NOT CHANGE THIS METHOD
    */
   static int partition(int[] points, int low, int high)
   {
      Random rand = new Random();
      int pivot = rand.nextInt(high - low + 1) + low; //Pick a random pivot
      int pivotValue = points[pivot];
      int i = low + 1;
      int j = high;
      int temp;

      points[pivot] = points[low];
      points[low] = pivotValue;

      while( i<j )
      {
         while( i<=high && points[i] <= pivotValue )
         {
            i++;
         }
         while( j>=low && points[j] > pivotValue )
         {
            j--;
         }
         if(i<j)  //swap out of order elements
         {
            temp = points[i];
            points[i] = points[j];
            points[j] = temp;
         }
      }
      if( i<=high && points[i] <= pivotValue )
      {
         i++;
      }

      points[low] = points[i-1];
      points[i-1] = pivotValue;

      return i-1;
   }
}

class Pair {
   public int low;
   public int high;

   /* creates a Pair from the given x, y values */
   public Pair( int low, int high )
   {
      this.low  = low;
      this.high = high;
   }
}
