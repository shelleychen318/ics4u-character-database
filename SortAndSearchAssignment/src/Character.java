// Shelley Chen

public class Character {

	protected String name, gender, eyeColour;
	
	public Character () // constructor
	{
		this.name = "no name";
		this.gender = " no gender";
		this.eyeColour = "no eye colour";
	}
	
	public Character (String charName, String charGender, String charEyeColour) // constructor
	{
		this.name = charName;
		this.gender = charGender;
		this.eyeColour = charEyeColour;
	}
	
	public void setName (String newName)
	{
		name = newName;
	}
	
	public void setGender (String newGender)
	{
		gender = newGender;
	}
	
	public void setEyeColour(String newEyeColour)
	{
		eyeColour = newEyeColour;
	}
	
	public String getName () 
	{
		return name;
	}
	
	public String getGender () 
	{
		return gender;
	}
	
	public String getEyeColour () 
	{
		return eyeColour;
	}
	
	public String who ()
	{
		return "I am a base character";
	}
	
}