package reglergrafik;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ReglerGrafik extends Frame {

    double[] ist1;
    //ist = new double[200];   
    double soll[];
     
    final int ARRAY_MAX = 200;

    public double[] pid() {
        // TODO code application logic here
        //soll[] wird per globale Variable übertragen
        final double Kp = 0.1;
        final double Ki = 0.1;
        final double Kd = 0.1;       
      

        //double soll[];
        double[] ist;
        double[] differenz;
        soll = new double[ARRAY_MAX];
        ist = new double[ARRAY_MAX];
        differenz = new double[ARRAY_MAX];

        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\t.gronowski\\Documents\\NetBeansProjects\\EinfacherRegler\\src\\einfacherregler\\test.txt"));
            int zaehler = 0;

            String zeile1 = br.readLine();

            soll[zaehler] = Double.parseDouble(zeile1.trim());

            while ((zeile1 = br.readLine()) != null) {
                zaehler++;
                soll[zaehler] = Double.parseDouble(zeile1.trim());
                //System.out.println(zaehler+" :"+soll[zaehler]);
            }

        } catch (IOException e) {
            System.out.println("test.txt ist nicht vorhanden");
        }

        // ist[3]=ist[2]+Kp*differenz[2]+Kd*(differenz[2]-differenz[1])+Ki*(differenz[2]+differenz[1]+differenz[0]);
        System.out.println("Ist: " + ist[3]);

        for (int i = 3; i < 82; i++) {
            differenz[i - 1] = soll[i - 1] - ist[i - 1];
            ist[i] = ist[i - 1] + Kp * differenz[i - 1] + Kd * (differenz[i - 1] - differenz[i - 2]) + Ki * (differenz[i - 1] + differenz[i - 2] + differenz[i - 3]);
            //Störung, z.B. Plasmasturm
            if (i==50) ist[i]=10.0;
        }

        System.out.println();

        for (int i = 0; i < 82; i++) {
            //System.out.println("Ist: "+ist[i]+" Soll: "+soll[i]);
            System.out.printf("Zeit: %d Ist: %.2f  Soll: %.2f\n", i, ist[i], soll[i]);
        }
        return ist;
    }

    public ReglerGrafik() {
        super("Regler-Grafik");

        ist1 = pid();

        this.setSize(1000, 700);
        this.setLayout(new FlowLayout());
        class MyCanvas extends Canvas {

            public void paint(Graphics g) {
                g.drawString("Canvas", 10, 10);
                int x=0;
                int xalt=0;
                for (int i = 1; i < ARRAY_MAX; i++) {
                    x=i*5;
                    //g.drawLine(i - 1, 600 -  (int)(50 * ist1[i - 1]), i, 600 -  (int)(50 * ist1[i]));
                     g.setColor(Color.red);
                    g.drawLine(xalt, 600 -  (int)(50 * ist1[i - 1]), x, 600 -  (int)(50 * ist1[i]));
                    g.setColor(Color.yellow);
                    g.drawLine(xalt, 600 -  (int)(50 * soll[i - 1]), x, 600 -  (int)(50 * soll[i]));
                    int output1 = 600 -  (int)(10 * ist1[i - 1]);
                    System.out.println("output1: " + output1);
                    xalt=x;
                    
                    try{
                        Thread.sleep(100);
                        
                    }catch(InterruptedException e){}
                }
            }
        }
        Canvas c = new MyCanvas();
        c.setBackground(Color.blue);
        c.setForeground(Color.white);
        c.setSize(1000, 700);
        this.add(c);
        //Kreuzchen aktivieren, damit Appl. beendet werden kann, als innere Klasse
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        }
        );
    }

    public static void main(String[] args) {
        // TODO code application logic here
        ReglerGrafik r = new ReglerGrafik();
        r.setVisible(true);
    }
}
