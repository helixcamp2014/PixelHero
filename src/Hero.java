
public class Hero extends Character{

	private Weapon weapon;
	private int health;
	private Armor armor;
	
	//constructor for the hero - this initializes the hero
	public Hero()
	{
		health = 100;
		weapon = new Weapon();
		armor = new Armor();
		
		type_id = 'H';
	}
}
