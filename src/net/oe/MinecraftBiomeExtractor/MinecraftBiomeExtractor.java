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
		
		boolean nogui = false;
		boolean flush = false;
		boolean errorsOnly = false;
		File jarLocation = null;
		boolean manualJarLocation = false;
		boolean help = false;
		File world_folder = null;
		
		int i = 0;
		while (i < args.length)
		{
			int remaining = args.length - i - 1;
			
			if (args[i].equalsIgnoreCase("-nogui"))
			{
				nogui = true;
			}
			else if (args[i].equalsIgnoreCase("-flush"))
			{
				flush = true;
			}
			else if (args[i].equalsIgnoreCase("-help") || args[i].equalsIgnoreCase("-?") || args[i].equalsIgnoreCase("?"))
			{
				help = true;
			}
			else if (args[i].equalsIgnoreCase("-quiet"))
			{
				errorsOnly = true;
			}
			else if (args[i].equalsIgnoreCase("-jar") && remaining > 0)
			{
				i++;
				jarLocation = new File(args[i]);
				if (jarLocation.exists())
					manualJarLocation = true;
				else
					System.out.print("Minecraft jar location was invalid.");
			}
			else
			{
				world_folder = new File(args[i]);
			}
			
			i++;
			
		}
		
		if (help)
		{
			System.out.println("Minecraft Biome Extractor command line usage:");
			System.out.println("\tjava -jar MinecraftBiomeExtractor.jar -nogui world_folder [-flush] [-quiet] [-jar jarloaction]");
		}
		else if (!nogui)
		{
			MinecraftBiomeExtractorGUI.launchGUI();
		}
		else
		{
			if (world_folder.isDirectory())
			{
				// Create a world processor and bind to minecraft
				WorldProcessor world_processor = new WorldProcessor(null,errorsOnly,flush);
				
				if (manualJarLocation)
					world_processor.setjarlocation(jarLocation);
				
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

	}

}
