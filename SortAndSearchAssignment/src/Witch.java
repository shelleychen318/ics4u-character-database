// Shelley Chen

public class Witch extends Character{
	
	protected String spell;
		
		public Witch () // constructor
		{
			this.spell = "no spell";
		}
		
		public Witch (String charName, String charGender, String charEyeColour, String charSpell) // constructor
		{
			this.name = charName;
			this.gender = charGender;
			this.eyeColour = charEyeColour;
			this.spell = charSpell;
		}
		
		public void setSpell (String newSpell)
		{
			spell = newSpell;
		}
		
		public String getSpell ()
		{
			return spell;
		}
		
		public String who ()
		{
			return "I am a witch";
		}

}