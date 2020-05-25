package pl.grodny.java.pw.lab9;

import java.util.*;

public class Zad2
{
    private static final Random rand = new Random();
    public static void main(String[] args)
    {
        int cores = Runtime.getRuntime().availableProcessors();
        CharSeeker[] threads = new CharSeeker[cores];;
        char[][] tab;
        int n = 100, m = 100, max = 255;
        tab = new char[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                tab[i][j] = (char)rand.nextInt(max);

        char[] dict;
        int i = 0;
        for (; i < cores - 1; i++)
        {
            dict = new char[max / cores];
            for (int j = 0; j < dict.length; j++)
                dict[j] = (char) (i * dict.length + j);
            threads[i] = new CharSeeker(tab, dict);
        }
        dict = new char[max - (max / cores * i)];
        for (int j = 0; j < dict.length; j++)
            dict[j] = (char) (i * (max / cores) + j);
        threads[i] = new CharSeeker(tab, dict);


        for (CharSeeker x: threads)
            x.start();
        for (CharSeeker x: threads)
        {
            try
            {
                x.join();
            }
            catch (InterruptedException e)
            {}
        }
        i = 1;
        int j;
        for (CharSeeker t : threads)
        {
            j = 1;
            Map<Character, Integer> res = t.getRes();
            System.out.println("Watek: "+ i++);
            for (Character c : res.keySet())
            {
                System.out.println("\t"+ j++ + " - " + ((int) c.charValue()) + " | " + c.toString() + " - " + res.get(c));
            }
        }
    }
}

class CharSeeker extends Thread
{
    char[][] tab;
    int n, m;
    char[]  dict;
    int[] counters;

    public CharSeeker(char[][]tabb, char[] d )
    {
        tab = tabb;
        dict = new char[d.length];
        for (int i = 0; i < d.length ; i++)
            dict[i] = d[i];
        counters = new int[d.length];
    }
    @Override
    public void run()
    {
        for (char[]t : tab)
        {
            for (char x : t)
            {
                for (int i = 0; i < dict.length; i++)
                {
                    if(x == dict[i])
                        counters[i]++;
                }
            }
        }
    }
    Map<Character, Integer> getRes()
    {
        Map<Character, Integer> res = new HashMap<Character, Integer>();

        for (int i = 0; i < dict.length; i++)
        {
            res.put(dict[i],counters[i]);
        }
        return res;
    }

}