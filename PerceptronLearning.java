import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class PerceptronLearning{
    private ArrayList<Point> pool;
    private ArrayList<Point> classA, classB;
    private ArrayList<Point> scatteredPoints;
    private ArrayList<Integer> yTrack, tTrack;
    private Random rand;
    private float w1, w2, w3, w4;
    private float alpha;
    private int V;
    private float theta;
    private int midPoint;
    private long timeStart;
    private PrintWriter outFile;

    public PerceptronLearning(){
        rand = new Random();
        pool = new ArrayList<>();
        classA = new ArrayList<>();
        classB = new ArrayList<>(); 
        scatteredPoints = new ArrayList<>(); 
        yTrack = new ArrayList<>();
        tTrack = new ArrayList<>(); 
        theta = 0.0f; 
        w1 = 0;
        w2 = 0;
        w3 = 0;
        w4 = 0;
        V = -1;
    }
    
    public void generatePool(){
        for(int i = 0; i <= 10; i++){
            for(int j = 0; j <= 10; j++){
                for(int k = 0; k <= 10; k++){
                    pool.add(new Point(i, j, k));       
                }
            }
        }
        
        midPoint = pool.size()/2;
        
        pickPoints();
    }
   
    public int getPoolSize(){
        return pool.size();
    }
    
    public Point getMidPoint(){
        return pool.get(pool.size()/2);
    }
    
    public void run(float learningRate){
        alpha = learningRate;
        
        try{
            outFile = new PrintWriter("LearningRate_" + alpha + "_Output.txt");
        }catch(FileNotFoundException e){
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
            System.exit(1);
        }
        
        outFile.println("Learning rate = " + alpha);
        
        int epoch = 1;
        int y;
        int notEqual = 30;
        float dot;
        int tMy;
        float atMy, dw1, dw2, dw3, dw4;
        Point curr;
        int maxCount = 0;
        
        timeStart = System.currentTimeMillis();
        
        while(notEqual > 0){
            yTrack.clear();
            notEqual = 0;
            
            //if(epoch == 1){
            outFile.println("Epoch " + epoch);
            outFile.println("X | Y | Z | V | w1 | w2 | w3 | w4 | dot | y | t | t-y | a(t-y) | dW1 | dW2 | dW3 | dW4");
            outFile.println("--------------------------------------------------------------------------------------");
            //}
            
            for(int i = 0; i < scatteredPoints.size(); i++){
                curr = scatteredPoints.get(i);
                dot = ((curr.x * w1) + (curr.y * w2) + (curr.z * w3) + (V * w4));
                
                if(dot >= theta){
                    y = 1;
                    yTrack.add(new Integer(y)); 
                }
                else{
                    y = 0;
                    yTrack.add(new Integer(y));
                }
                
                //Check to see if y == t
                if(tTrack.get(i).intValue() != y){
                    notEqual++;
                }
                
                tMy = tTrack.get(i).intValue() - y; //yTrack.get(i).intValue();
                atMy = alpha * (float)tMy;
                
                dw1 = atMy * curr.x;
                dw2 = atMy * curr.y;
                dw3 = atMy * curr.z;
                dw4 = atMy * V;
                
                /*
                System.out.println("X : " + curr.x);
                System.out.println("Y : " + curr.y);
                System.out.println("Z : " + curr.z);
                System.out.println("V : " + V);
                System.out.println("W1 : " + w1);
                System.out.println("W2 : " + w2);
                System.out.println("W3 : " + w3);
                System.out.println("W4 : " + w4);
                System.out.println("DOT : " + dot);
                System.out.println("Y : " + y); //yTrack.get(i));
                System.out.println("T : " + tTrack.get(i));
                System.out.println("T-Y : " + tMy);
                System.out.println("a(T-Y) : " + atMy);
                System.out.println("dW1 : " + dw1);
                System.out.println("dW2 : " + dw2);
                System.out.println("dW3 : " + dw3);
                System.out.println("dW4 : " + dw4);
                System.out.println();
                //System.out.println();
                */
                
                //if(epoch == 1){
                outFile.println("" + curr.x + " | " + curr.y + " | " + curr.z + " | " + V + " | " + 
                                   w1 + " |  " + w2 + " | " + w3 + " | " + w4 + " | " + dot + " | " + yTrack.get(i) + " |  " +
                                   tTrack.get(i) + " | " + tMy + " | " + atMy + " | " + dw1 + " | " + dw2 + " | " + dw3 + " | " +dw4);
                outFile.println();
                //}
                
                w1 += dw1;
                w2 += dw2;
                w3 += dw3;
                w4 += dw4;
            }
            epoch++;
            maxCount++;
       }
       
        //outFile.println("FINAL EPOCH " + epoch);
        //outFile.println("" + curr.x + " | " + curr.y + " | " + curr.z + " | " + V + " | " + 
        //                   w1 + " |  " + w2 + " | " + w3 + " | " + w4 + " | " + dot + " | " + yTrack.get(i) + " |  " +
        //                   tTrack.get(i) + " | " + tMy + " | " + atMy + " | " + dw1 + " | " + dw2 + " | " + dw3 + " | " +dw4);
        outFile.println("Time (ms) " + (System.currentTimeMillis() - timeStart));
        outFile.println();
        
        printInClass(outFile);
        
        outFile.close();
    }
    
    private void pickPoints(){
        for(int i = 0; i < 15; i++){
            scatteredPoints.add(pool.get(rand.nextInt(midPoint)));
            scatteredPoints.add(pool.get(rand.nextInt(midPoint) + midPoint + 1)); 
            tTrack.add(new Integer(0));
            tTrack.add(new Integer(1));   
        }
    }
    
    private void printInClass(PrintWriter outFile){
        for(int i = 0; i < scatteredPoints.size(); i++){
            //System.out.println("T : " + tTrack.get(i).intValue() + " Y: " + yTrack.get(i).intValue());
        
        
            if(yTrack.get(i).intValue() == 0){
                outFile.println("Point -> " + scatteredPoints.get(i) + " in class A");
            }
            else
                outFile.println("Point -> " + scatteredPoints.get(i) + " in class B");
        }
    }
}
