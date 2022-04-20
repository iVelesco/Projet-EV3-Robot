package robotel;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Main {
	
	private static int etat = -1;
	private static final int DEMARAGE = 0;
	private static final int RECUPERER_PALET = 1;
	private static final int TRANSPORTER_PALET_1 = 2; //PALET_TOUCHE
	private static final int TOUCH_SENSOR_TRUE = 3;
	private static final int PINCE_FERME_avec_PALET = 4;
	private static final int POSITION_MILIEU = 5;
	private static final int REMIS_DROIT = 6;
	private static final int STOP_ROBOT_PINCE_FERME = 7;
	private static final int PINCES_OUVERTES = 8;
	private static final int PALET_ELIBERE = 9;
//	final int RECUPERER_PALET = 10;
	float procheDistance = 1000; //TODO : Distance a la quel on s'arrete avant le palet
	final int DEPOSER_PALET = 11;
	final int DEMARER = 99;

Robot charlie = new Robot();



public void firstRun() { //premier palet 
	etat = RECUPERER_PALET;
	while (true) {
		switch (etat) {
		
		case DEMARAGE : 
			

			etat= RECUPERER_PALET;
		case RECUPERER_PALET: // A quelques cm du palet
//			charlie.recupererPalet();
//			charlie.pince.stop();
			charlie.pince.setSpeed(2000);
			charlie.pince.forward();
			Delay.msDelay(1400);
			charlie.pince.stop();
			charlie.pilot.forward();
			while(!charlie.isTouched()) {}
			charlie.pilot.stop();
			Delay.msDelay(1000);
			charlie.pince.setSpeed(2000);
			charlie.pince.backward();
			Delay.msDelay(1000);

			//nous avons fait des modification pendant le jour de la competition et on a modifie le code
			// donc, on a pas beaucoup utilise les methodes crees dans la class Robot 
			
//			if(charlie.getEtatPince()) {
				etat = TRANSPORTER_PALET_1 ;
//			}
			 break;
		case TRANSPORTER_PALET_1: 
			charlie.pilot.rotate(35);
			charlie.pilot.travel(400);
			charlie.pilot.rotate(-35);
//			while(!charlie.estBlanc()) {	
			
			while(charlie.calculerDistance() > 0.3) {
				charlie.pilot.travel(50);
			}
			charlie.pilot.stop();
			charlie.pince.forward();
			Delay.msDelay(3000);
			charlie.pince.stop();
			charlie.pilot.travel(-600);
			charlie.pince.backward();
			Delay.msDelay(3000);
			charlie.pince.stop();
		
			
//				charlie.pilot.travel(1000000, charlie.stopBlanc());
//			}
			
			etat = -1 ;

			break;
		case -1:
			break;
			
//		case ETAT3:
//			
//			//ici les pinces sont ouvertes avancer jusqu'à touch sensor true puis stop
//			if(sensor.isTouched()==true) {
//				
//			robot.stop();
//			}
//			else {
//				robot.forward();
//			
//			}
//			
//			System.out.println("etat3");
//			ETAT = ETAT4;
//			break;
//		case ETAT4:
//			//fermer les pinces
//			pinces.fermer();
//			System.out.println("etat4");
//			break;
					
			}
			
	
	}
}
// ici est notre code 

public static void main(String[] args) {
	Main game = new Main();
	Button.LEDPattern(4);    // flash green led and
    Sound.beepSequenceUp();  // make sound when ready.
         
    Button.waitForAnyPress();
	
    game.firstRun();
	}

}
