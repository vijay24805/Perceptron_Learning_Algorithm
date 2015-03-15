public class Main{
    public static void main(String []args){
        PerceptronLearning learn = new PerceptronLearning();   
    
        learn.generatePool();
        learn.run(10.0f);
        learn.run(1.0f);
        learn.run(0.1f);
        learn.run(0.001f);
        System.out.println("Please look in directory for results");
        //System.out.println(learn.getMidPoint());
    }
}
