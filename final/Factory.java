//@author Joseph Haythorn + alfredo
package big_project;


public class Factory {
	private static Factory theInstance;
	
	private Factory() {}

	public static Factory getInstance(){
		if(theInstance == null)	theInstance = new Factory();
		
		return theInstance;		
	}
	//takes String name and instantiates correct PlayerAbstract
	//object
        public PlayerAbstract Create(String name){
		PlayerAbstract player;
		try{
                        //System.out.println("TRYING TO MAKE PLAYER: "+name);
			player = (PlayerAbstract)Class.forName("big_project.Player"+name).newInstance();
                        player.setName(name);
			return player;
		}
	       	catch(InstantiationException e){
			e.printStackTrace();
		}
		catch(IllegalAccessException e){
			e.printStackTrace();
		}	
		catch(ClassNotFoundException e){
			System.out.println("CLASS_NOT_FOUND");
                        player = new SamplePlayer();
                        player.setName(name);
                        return player;
                        //e.printStackTrace();
		}
		//should not return null
		return null;
	}
}
