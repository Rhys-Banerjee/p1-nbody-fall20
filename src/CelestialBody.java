

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		this.myXPos = b.myXPos;
		this.myYPos = b.myYPos;
		this.myXVel = b.myXVel;
		this.myYVel = b.myYVel;
		this.myMass = b.myMass;
		this.myFileName = b.myFileName;
	}

	public double getX() {
		return myXPos;
	}
	public double getY() {
		return myYPos;
	}
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}
	
	public double getMass() {
		return myMass;
	}
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double distanceSquared = ((b.myXPos - myXPos)*(b.myXPos - myXPos))+((b.myYPos - myYPos)*(b.myYPos - myYPos));
		return Math.sqrt(distanceSquared);
	}

	public double calcForceExertedBy(CelestialBody b) {
		return (6.67*1e-11)*((b.myMass*myMass)/(this.calcDistance(b)*calcDistance(b)));
	}

	public double calcForceExertedByX(CelestialBody b) {
		return calcForceExertedBy(b)*((b.myXPos-myXPos)/this.calcDistance(b));
	}
	public double calcForceExertedByY(CelestialBody b) {
		return calcForceExertedBy(b)*((b.myYPos-myYPos)/this.calcDistance(b));
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for(int i=0;i<bodies.length;i++){
			if(! bodies[i].equals(this)){
				sum += this.calcForceExertedByX(bodies[i]);
			}
		}
		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for(int i=0;i<bodies.length;i++){
			if(! bodies[i].equals(this)){
				sum += this.calcForceExertedByY(bodies[i]);
			}
		}
		return sum;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax = xforce/this.myMass;
		double ay = yforce/this.myMass;
		double nvx = this.myXVel + deltaT*ax; 
		double nvy = this.myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
