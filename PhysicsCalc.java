//This is a Java Program for doing projectile motion calculations like those found in an introductory physics class.
import javax.swing.JFrame;
import java.awt.*;
import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import java.util.Scanner;

class physicscalc extends JFrame
{
    private static Graphics gBuf = null;
    private static GraphPaperCanvas canvas = null;
    private static Image vm = null;
    private int x, y;
    private int w, h;

    public physicscalc( int x, int y )
    {
        if ( canvas == null )
        {
            setTitle("Projectile Trajectory Graph");
            setSize(625,645);
            setLocation(20,50);

            canvas = new GraphPaperCanvas(null);
            getContentPane().add(canvas);
            setVisible(true);

            vm = canvas.createImage(2200,1900);
            gBuf = vm.getGraphics();
            canvas.setVm(vm);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        this.x = x;
        this.y = y;
        w = 600;
        h = 600;

        drawBounds();
        gBuf.setColor( Color.BLUE );
    }

    public void drawBounds()
    {
        Color cur = gBuf.getColor();
        gBuf.setColor( Color.LIGHT_GRAY );
        for ( int d=0; d<w; d+=w/50 )
            gBuf.drawLine( x+d, y+0, x+d, y+h );
        for ( int d=0; d<h; d+=h/50 )
            gBuf.drawLine( x+0, y+d, x+h, y+d );

        gBuf.setColor( Color.BLACK );
        gBuf.drawRect( x, y, w+1, h+1 );
        gBuf.drawLine( x+w/2, y+0, x+w/2, y+h );
        gBuf.drawLine( x+0, y+h/2, x+w, y+h/2 );
        gBuf.setColor( cur );
        canvas.repaint();
    }
    public void setColor( Color c )
    {
        gBuf.setColor(c);
    }

    public void drawPoint( double px, double py )
    {
        if ( px > 50 || px < -50 || py > 50 || py < -50 )
            return;

        px *= w/50;
        py *= h/50;
        px += w/2 + 1;
        py = h/2 - py + 1;

        gBuf.drawLine( x+(int)px, y+(int)py, x+(int)px, y+(int)py );
        canvas.repaint();
    }
}

class GraphPaperCanvas extends Canvas
{
    private Image vm;

    public GraphPaperCanvas( Image vm )
    {
        this.vm = vm;
        setBackground( Color.white );
    }

    public void setVm( Image vm )
    {
        this.vm = vm;
    }

    public void paint( Graphics g )
    {
        g.drawImage(vm,0,0,this);
    }

    public void update(Graphics g) { paint(g); }	// don't clear screen on repaint

}

class GraphingParabolas
{
    public static void main( String[] args )
    {
        Scanner in = new Scanner(System.in);





        double initialVelocity = 0;
        double launchAngle =0;
        double initialHeight=0;
        double frictionCoefficient=0;
        double weight=0;

        try {
            System.out.println("Please Enter Projectile Initial Velocity in Meters per Second.");
            initialVelocity = in.nextDouble();

        }catch (java.util.InputMismatchException e){
            System.out.println( "Please only Enter Positive Numbers for the Initial Velocity.");
            System.exit(0);
        }
        if (initialVelocity<= 0){
            System.out.println( "Please only Enter Positive Numbers for the Initial Velocity.");
            System.exit(0);
        }
        try {
            System.out.println("Please Enter Projectile Launch Angle in Degrees.");
            launchAngle = in.nextDouble();

        }catch (java.util.InputMismatchException e){
            System.out.println( "Please only Enter Positive Numbers Between 0 and 90 for the Launch Angle.");
            System.exit(0);
        }
        if (launchAngle<= 0||launchAngle>90){
            System.out.println( "Please only Enter Positive Numbers Between 0 and 90 for the Launch Angle.");
            System.exit(0);
        }
        try {
            System.out.println("Please Enter the Initial Height of the Projectile in Meters.");
            initialHeight = in.nextDouble();

        }catch (java.util.InputMismatchException e){
            System.out.println( "Please only Enter Positive Numbers for the Initial Height.");
            System.exit(0);
        }
        if (initialHeight<= 0){
            System.out.println( "Please only Enter Positive Numbers for the Initial Height.");
            System.exit(0);
        }
        try {
            System.out.println("Please Enter the Frictional Coefficient of the Projectile, a positive value usually between 0 and 1.");
            frictionCoefficient = in.nextDouble();

        }catch (java.util.InputMismatchException e){
            System.out.println( "Please only Enter Positive Numbers for the Frictional Coefficient.");
            System.exit(0);
        }
        if (frictionCoefficient<= 0){
            System.out.println( "Please only Enter Positive Numbers for the Frictional Coefficient.");
            System.exit(0);
        }
        try {
            System.out.println("Please Enter the Weight of the Projectile, in Kilograms");
            weight = in.nextDouble();

        }catch (java.util.InputMismatchException e){
            System.out.println( "Please only Enter Positive Numbers for the Weight.");
            System.exit(0);
        }
        if (weight<= 0){
            System.out.println( "Please only Enter Positive Numbers for the Weight.");
            System.exit(0);
        }













System.out.print(projectileCalc(initialVelocity,launchAngle,initialHeight,frictionCoefficient,weight));




    }






    //Variables
    static double dist = 0;//Horizontal distance travelled
    static double time = 0;//Time in air
    static double rad = 0;//Launch angle in radians
    static double apog = 0;//Apogee height
    static double tapog = 0;//Time to apogee
    static double fapog = 0;//Time from apogee to contact with ground
    static double dslide=0;//Sliding distance
    static double kenergy =0;//Kinetic energy
    static double vx=0;
    static double vymax=0;
    static double vmax=0;
    //Fuction Itself(Where the magic happens)
    public static String projectileCalc(double initialVelocity,double launchAngle, double startHeight, double coefFriction, double mass){
        rad=launchAngle*Math.PI/180;//Converts degrees to radians
        time= ((-initialVelocity*sin(rad)-sqrt(((initialVelocity*sin(rad))*(initialVelocity*sin(rad)))+(4*4.9*startHeight)))/(-9.80665f));//Time in air
        dist=time*(initialVelocity*cos(rad));//Horizontal Distance Travelled
        apog=((initialVelocity*sin(rad)*initialVelocity*sin(rad))/(9.80665*2))+startHeight;//Apogee height
        tapog=(initialVelocity*sin(rad))/9.80665;//Time to apogee
        dslide = (initialVelocity*cos(rad)*initialVelocity*cos(rad))/(coefFriction*2*9.80665);// Sliding Distance
        kenergy=initialVelocity*initialVelocity*mass;//Kinetic energy
        fapog = time-tapog;//Time from apogee to contact with ground
        vx =initialVelocity*cos(rad);
        vymax= -(initialVelocity*sin(rad)-9.80665*time);
        vmax=sqrt(vx*vx+vymax*vymax);
        double sin,cos;
        double graphSpeed;
        if (initialVelocity>750)
        {
            graphSpeed=10;
        }else{
            graphSpeed=0.01;
        }


        sin = sin(launchAngle*Math.PI/180);
        cos = cos(launchAngle*Math.PI/180);
        double y=0;
        double a, b, c,h;
        physicscalc gp1 = new physicscalc(10,10);
        for ( double x = 0; x<=1000000000&y>=0; x+=graphSpeed)//Graphing function
        {
            if (dist>10000000||apog>10000000){
                System.out.println("Value too large to calculate");
                h=0;
                System.exit(0);
            } else if (dist>25&&apog<=25){
                h=25/dist;
            } else if (apog>25&&dist<=25){
                h=25/apog;
            } else if (apog>25&&dist>25){
                if (apog>dist){
                    h=25/apog;
                    break;
                }
                else{
                    h = 25 / dist;
                    break;
                }
            } else{
                h=1;
            }

            a = -9.80665/(sin*sin*initialVelocity*initialVelocity);
            b = sin/cos; c = startHeight;
            y = (a*Math.pow(x,2)+b*x + c);
            gp1.drawPoint(x*h*2,y*h);
        }

//Return Statement
        return "\n"+"\n"+"This projectile has a kinetic energy of " + kenergy+" Joules, enough to power an average American home for "+kenergy/5161+" seconds."+"\n"+"This projectile will land "+dist+" meters from the starting point and will slide for " +dslide+" meters after hitting the ground."+"\n"+ "This projectile will travel a total of " +(dslide+dist)+" meters travelled horizontally."+"\n"+ "This projectile will be in the air for "+time+" seconds."+"\n"+"The maximum height of this projectile is "+apog+" meters."+"\n"+"This projectile will take "+tapog+" seconds to reach that maximum and will fall for "+fapog+" seconds after reaching that apogee." +"\n"+"This projectile has a maximum velocity of "+vmax+" meters per second."+"\n"+"\n";

    }
    public class ProjectileMotionCalc {

    }}
