
public class Hero extends Character{

	public Weapon weapon;
	//public int health;
	public Armor armor;
	
	//constructor for the hero - this initializes the hero
	public Hero()
	{
		health = 20;
		weapon = new Weapon();
		armor = new Armor();
		isMonster = false;
		description = "Our Hero";
		
		
		type_id = 'H';
	}
}
