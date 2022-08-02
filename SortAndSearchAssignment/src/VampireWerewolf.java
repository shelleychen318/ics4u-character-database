// Shelley Chen

public class VampireWerewolf extends Vampire {
	
	protected String furColour;
	
	public VampireWerewolf () // constructor
	{
		this.furColour = "no fur colour";
	}
	
	public VampireWerewolf (String charName, String charGender, String charEyeColour, String charPower, String charFurColour)
	{
		this.name = charName;
		this.gender = charGender;
		this.eyeColour = charEyeColour;
		this.power = charPower;
		this.furColour = charFurColour;
	}
	
	public void setFurColour (String newFurColour)
	{
		furColour = newFurColour;
	}
	
	public String getFurColour ()
	{
		return furColour;
	}
	
	public String who ()
	{
		return "I am a vampire-werewolf hybrid";
	}
}