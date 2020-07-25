/* 
repeatedly swapping the ajacent elements (if they are in wrong order)
the biggest number is moved to the end during each iteration
*/
public class BubbleSort{

    /* 
     if array[a]>array[b], swapping two ajacent elements
    */
    public static void swap(int a, int b, int[] array){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static int[] sort(int[] array){
        for (int i =0;i<array.length-1;i++){
            for (int j=1;j<array.length-i;j++){
                if(array[j-1]>array[j]){
                    BubbleSort.swap(j-1,j,array);
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int[] x  = new int[]{2,1,3,7,4,9};
        int[] y  = BubbleSort.sort(x);
        for (int i=0;i<y.length;i++){
            System.out.println(y[i]);
        }
    }

}