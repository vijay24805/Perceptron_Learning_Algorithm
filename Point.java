public class Point{
    float x;
    float y;
    float z;
    
    public Point(){
    
    }
    
    public Point(int x, int y, int z){
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
    }
    
    public String toString(){
        return ("X: " + x + " Y: " + y + " Z: " + z);
    }
}
