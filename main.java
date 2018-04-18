import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
class Bloomon {
	
	public static void main(String[] args) {
    	
		MainClass myClass = new MainClass();
		
		//read all
		myClass.readValues();
		//finish read input
		myClass.verifyBouquites();
		
	}
	public static class MainClass
	{
		
        private String[] bouquets = new String[9999];
		private Map<String,Integer> flowers = new HashMap<>();
		private int countbouquet = 0;
        public void readValues()
		{
			String input;
			Scanner reader = new Scanner(System.in);		
			while (!(input = reader.nextLine()).isEmpty()){
				this.bouquets[this.countbouquet] = input;
				this.countbouquet++;
			}
			while (reader.hasNextLine())
			{
				input = reader.nextLine();
				char specie = input.charAt(0);
				char size = input.charAt(1);
				
				Integer value = this.flowers.get("" + specie + size);
				if (value == null)
				{
					value = 0;
				}
				this.flowers.put("" + specie + size,value + 1);
			}
		}
		public void verifyBouquites()
		{
			for(int i=0;i<this.countbouquet; i++)
			{
				boolean available = true;
				String input = this.bouquets[i];
				char design = input.charAt(0);
				char size = input.charAt(1);
				int[] allquantities = new int[9999];
				char[] allspecies = new char[9999];
				int contquantities = 0;
				int contspecies = 0;
				//get species
				Matcher m = Pattern.compile("\\p{javaLowerCase}+").matcher(input);
				while (m.find()) {
				  allspecies[contspecies] = m.group().charAt(0);
				  contspecies++;
				}
				//get quantities
				m = Pattern.compile("-?\\d+").matcher(input);
				while (m.find()) {
				  allquantities[contquantities] = Integer.parseInt(m.group());
				  contquantities++;
				}
				contquantities--;
				int total = allquantities[contquantities];
				
				//verify if bouquet is available
				int totalaux = total;
				Map<String,Integer> flowersCopy = new HashMap<String,Integer>(this.flowers);
				for(int j=0;j<contspecies;j++)
				{
					char specie = allspecies[j];
					int quantity = allquantities[j];
					
					Integer value = this.flowers.get("" + specie + size);
					if(value==null || value < quantity)
					{
						available = false;
					}
					else
					{
						flowersCopy.put("" + specie + size,value - quantity);
						totalaux -= quantity;
					}
				}
				
				if(available)
				{
					for (Map.Entry<String, Integer> entry : flowersCopy.entrySet())
					{
						String input2 = entry.getKey();
						char specieB = input2.charAt(0);
						char sizeB = input2.charAt(1);
						Integer quantityaux = entry.getValue();
						if(size == sizeB && totalaux > 0 && quantityaux > 0)
						{
							if(quantityaux > totalaux)
							{
								quantityaux = totalaux;
							}
							totalaux -= quantityaux;
							flowersCopy.put(entry.getKey(),quantityaux);
						}
					}
					//if its available
					if(totalaux == 0)
					{
						String resultparcial = "" + design + size;
						for (Map.Entry<String, Integer> entry : flowersCopy.entrySet())
						{
							//print only flowers used
							if(flowersCopy.get(entry.getKey()) != this.flowers.get(entry.getKey()))
							{
								String input2 = entry.getKey();
								char specieB = input2.charAt(0);
								char sizeB = input2.charAt(1);
								Integer quantityused = this.flowers.get(entry.getKey()) - flowersCopy.get(entry.getKey());
								resultparcial += String.valueOf(quantityused) + specieB;
							}
						}
						System.out.println(resultparcial);
						this.flowers = flowersCopy;
					}
				}
			}
		}
		
    }
	
}