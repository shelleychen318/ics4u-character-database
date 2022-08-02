// Shelley Chen

public class Vampire extends Character {
		
		protected String power;
		
		public Vampire () // constructor, no arguments
		{
			this.power = "no power";
		}
		
		public Vampire (String charName, String charGender, String charEyeColour, String charPower) // constructor, with arguments
		{
			this.name = charName;
			this.gender = charGender;
			this.eyeColour = charEyeColour;
			this.power = charPower;
		}
		
		public void setPower (String newPower)
		{
			power = newPower;
		}
		
		public String getPower ()
		{
			return power;
		}
		
		public String who ()
		{
			return "I am a vampire";
		}
	}
