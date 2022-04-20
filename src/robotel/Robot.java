package robotel;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Robot {

	//Initialisation pince
	protected EV3MediumRegulatedMotor pince = new EV3MediumRegulatedMotor(MotorPort.B);

	//Initialisation sensors
	protected EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
	protected EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	Brick b = BrickFinder.getDefault();
	SampleProvider spTouch = this.touchSensor.getTouchMode();
	Port s4 = b.getPort("S4");
	private EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(s4);
	// Initiliation moteur
	protected EV3LargeRegulatedMotor moteurDroit = new EV3LargeRegulatedMotor(MotorPort.A);
	protected EV3LargeRegulatedMotor moteurGauche = new EV3LargeRegulatedMotor(MotorPort.C);
	private Wheel wheel1 = WheeledChassis.modelWheel(moteurDroit, 56).offset(-68);
	private Wheel wheel2 = WheeledChassis.modelWheel(moteurGauche, 56).offset(68);
	private Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	protected MovePilot pilot = new MovePilot(chassis);	
	
	//Attributs utilitaires (variables)
	private boolean etatPinceFermes = true;
//	private int pinceSpeed = 1500;
//	private double loadingSpeed = 15;
//	private int duree = 1; //temps d'ouverture pince en secondes	
	
	

	

	public void ouvrirPince() {
		pince.setSpeed(2000);
    	pince.forward();
    	Delay.msDelay(1300);
//    	pince.close();
    	etatPinceFermes= false;
    }
    
    public void fermerPince() {
    	pince.setSpeed(2000);
    	pince.backward();
    	Delay.msDelay(500);//    	pince.close();
    	etatPinceFermes= true;
    } 
    
    public void recupererPalet() {
		ouvrirPince();
		pilot.forward();
//		pilot.setLinearSpeed(loadingSpeed);
		while(!this.isTouched()) {};
		pilot.stop();			
			fermerPince();
			
	}
    
    public boolean getEtatPince() {
    	return this.etatPinceFermes;
    }
    
    
    //******************** Methods touch sensor **************************/
    
    public boolean isTouched() {
    	
    	float [] sample = new float[spTouch.sampleSize()];
    	
    	spTouch.fetchSample(sample, 0);

        if (sample[0] == 0)
            return false;
        else
            return true;
    	}
    
    
    //******************** Methods color sensor **************************/
    
    public boolean estBlanc() {
    	System.out.println(this.colorSensor.getColorID() == Color.WHITE);
         return this.colorSensor.getColorID() == Color.WHITE;
    }
    public boolean stopBlanc() {
    	System.out.println(this.colorSensor.getColorID() == Color.WHITE);
    	 if(this.colorSensor.getColorID() == Color.WHITE) {
    		 pince.setSpeed(2000);
    		 pince.forward();
    		 Delay.msDelay(1000);
    		 pilot.backward();
    		 Delay.msDelay(3000);
    		 pilot.rotate(180);
    		 pilot.stop();
    		 return true;
    	 }
         return false;
    }

    public boolean estVert() {
    	System.out.println(this.colorSensor.getColorID() == Color.GREEN);
        return this.colorSensor.getColorID() == Color.GREEN;
   }
    
    public boolean estBleu() {
    	System.out.println(this.colorSensor.getColorID() == Color.BLUE);
        return this.colorSensor.getColorID() == Color.BLUE;
   }
    
   // TODO: quickStop a la linge blanche
    
  //******************** Methods ultrasonic sensor **************************/

//    public void setupUtlraSonic() {
//    	ultraSensor.getMode("Distance");
//    } ?????????????????????????
    
    public float calculerDistance() {
    	SampleProvider sp = ultraSensor.getDistanceMode();
    	float[] sample = new float[sp.sampleSize()];
    	sp.fetchSample(sample, 0);
		return sample[0];
	}
    
    public float getDistance() {// TODO : on a besoin ou non ???
		System.out.println(this.calculerDistance());
		return this.calculerDistance();
	}
    
    public void run() { 
    	pilot.forward();
    	while(!this.isTouched()) {
    	pilot.stop(); 
    		
    	}
    	
    	
    }
    
    
    
    public static void main(String[] args) {
		Robot nono = new Robot();
//		nono.ouvrirPince();
//		nono.fermerPince();
		nono.pince.backward();
		Delay.msDelay(7000);
//		nono.run();
//		nono.recupererPalet();
//		nono.estBlanc();
//		nono.setupUtlraSonic();\\
//		nono.getDistance();
	}
}
