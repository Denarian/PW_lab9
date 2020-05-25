package pl.grodny.java.pw.lab9;

import java.util.Random;

public class Zad1
{
    private static final Random rand = new Random();

    public static void main(String[] args)
    {
        int n = 9;
        int[] A, B, C;
        int cores = Runtime.getRuntime().availableProcessors();

        A = new int[n];
        B = new int[n];
        C = new int[n];
        for (int i = 0; i < n; i++) A[i] = rand.nextInt(10);
        for (int i = 0; i < n; i++) B[i] = rand.nextInt(10);

        int len, extra;
        Thr[] threads = new Thr[cores];;
        if(n > cores)
        {
            len = n / cores;
            if(n % cores == 0)
                for (int i = 0; i < cores; i++)
                    threads[i] = new Thr(A, B, C, i * len, i * len + len);
            else
            {
                int i = 0;
                for (; i < cores - 1; i++)
                    threads[i] = new Thr(A, B, C, i * len, i * len + len);
                threads[i] = new Thr(A, B, C, i * len, n);
            }
            for (Thr x: threads)
                x.start();
            for (Thr x: threads)
            {
                try
                {
                    x.join();
                }
                catch (InterruptedException e)
                {}
            }
        }
        else
        {
            for (int i = 0; i < n; i++)
                threads[i] = new Thr(A, B, C, i , i + 1);
            for (int i = 0; i < n; i++)
                threads[i].start();
            for (int i = 0; i < n; i++)
            {
                try
                {
                    threads[i].join();
                }
                catch (InterruptedException e)
                {}
            }
        }


        System.out.println("Cores: "+cores);
        for (int x : A) System.out.printf("%2d | ", x);
        System.out.println("");
        for (int x : B) System.out.printf("%2d | ", x);
        System.out.println("");
        for (int x : C) System.out.printf("%2d | ", x);
        System.out.println("");


    }
}

class Thr extends Thread
{
    int[] A, B, C;
    int p, k;
    public Thr(int[] a, int[] b, int[] c, int po, int ko)
    {
        A = a;
        B = b;
        C = c;
        p = po;
        k = ko;
    }
    @Override
    public void run()
    {
        for (int i = p; i < k; i++)
        {
            C[i] = A[i] + B[i];
        }
    }
}
