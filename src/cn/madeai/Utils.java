package cn.madeai;

/**
 * Created by <a href="mailto:huangyebiaoke@outlook.com">huang</a> on 2020/11/25 13:28
 */
public class Utils {
    public static String arrayToString(int[] arr){
        String str="";
        for (int i:arr) {
            str+=i;
        }
        return str;
    }
    public static int[] stringToArray(String str){
        int[] arr=new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i]=Integer.parseInt(str.substring(i,i+1));
        }
        return arr;
    }
}
