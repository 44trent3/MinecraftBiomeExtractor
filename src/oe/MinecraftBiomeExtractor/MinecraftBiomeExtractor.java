package net.oe.MinecraftBiomeExtractor;
import java.io.File;

public class MinecraftBiomeExtractor {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		if (args.length==0)
		{
			MinecraftBiomeExtractorGUI.launchGUI();
		}
		else if (args.length > 1 && args[0].equalsIgnoreCase("-nogui"))
		{
			boolean flush = false;
			boolean errorsOnly = false;
			
			// Parse command line arguments
			File world_folder = new File(args[1]);
			if (args.length > 2 && args[2].equalsIgnoreCase("-flush"))
				flush = true;
			
			if (world_folder.isDirectory())
			{
				// Create a world processor and bind to minecraft
				WorldProcessor world_processor = new WorldProcessor(null,errorsOnly,flush);
				boolean bound = world_processor.bindToMinecraft();
				if (!bound)
				{
					System.out.print("Failed to bind to Minecraft, cannot generate biomes.\n" 
							 + "Review the above messages to see if there's anything you can do about it.\n"
							 + "If not, check online for a new version.");
					return;
				}
				world_processor.setWorldFolder(world_folder);
				world_processor.run();
			}				
			else
			{
				System.out.println("Error: world folder not found.");
				return;
			}
		}
		else
		{
			System.out.println("Minecraft Biome Extractor command line usage:");
			System.out.println("\tjava -jar MinecraftBiomeExtractor.jar -nogui world_folder [-flush]");
		}

	}

}
