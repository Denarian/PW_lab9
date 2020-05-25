package pl.grodny.java.pw.lab9;

public class Zad3
{
    public  void main(String[] args)
    {
        int[] tab = new int[1000];
        for (int i = 0; i < tab.length; i++)
            tab[i] = i+1;
        int cores = Runtime.getRuntime().availableProcessors();
        Deleter[] threads = new Deleter[cores];
        int size = tab.length / cores;


        for (int j = 1; j < tab.length; j++)
        {
            if(tab[j] != 0)
            {
                int i = 0;
                for (; i < cores - 1; i++)
                    threads[i] = new Deleter(tab, i * size, i * size + size);
                threads[i] = new Deleter(tab, i * size, tab.length);

                Deleter.setX(tab[j]);

                for (Deleter x: threads)
                    x.start();

                for (Deleter x: threads)
                {
                    try
                    {
                        x.join();
                    }
                    catch (InterruptedException e)
                    {}
                }
            }

        }
        for (int x: tab)
            if(x != 0 )
                System.out.println(x);
    }


}

class Deleter extends Thread
{
    static int X;

    static public void setX(int x)
    {
        X = x;
    }

    int tab[];
    int k,p;

    public Deleter(int[] tabb, int pp, int kk)
    {
        tab = tabb;
        k = kk;
        p = pp;
    }

    @Override
    public void run()
    {
        for (int i = p; i < k; i++)
        {
            if(tab[i] % X == 0 && tab[i] != X)
                tab[i] = 0;
        }
    }
}