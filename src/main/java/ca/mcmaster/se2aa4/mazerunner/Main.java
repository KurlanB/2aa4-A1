package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    //Initializing Apache Logger
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        //Start of maze runner
        logger.info("** Starting Maze Runner");
        
        //Starting the maze runner
        start(args);
        
        //End of maze runner
        logger.info("** End of MazeRunner");
    }

    /**
     * Starts the maze runner with the given command line arguments.
     * 
     * @param args The command line arguments.
     */
    public static void start(String[] args){
        //Parsing options
        Options options = new Options();
        options.addOption("i", true, "Input file of maze selected");
        options.addOption("p", true, "Path input");
    
        CommandLineParser parser = new DefaultParser();

        try {
            //Parsing the command line arguments
            CommandLine cmd = parser.parse(options, args);
            
            //Reading from maze file
            String filePath = cmd.getOptionValue("i");
            Maze maze = new Maze(filePath); 

            //Computing path
            if(cmd.hasOption("p") && cmd.getOptionValue("p") != null){
                logger.info("**** Verifying path");
                String path = cmd.getOptionValue("p").toUpperCase();

                boolean isvalid = maze.checkPathWest(path) || maze.checkPathEast(path);

                if(isvalid){
                    logger.info("*** Path is valid");
                    System.out.println("corrrect path");
                } else {
                    logger.info("*** Path is invalid");
                    System.out.println("incorrect path");
                }

            } else if(cmd.hasOption("p") && cmd.getOptionValue("p") == null){
                logger.warn("PATH NOT COMPUTED");
                System.out.println("No path was found");

            } else {
                logger.info("**** Computing path");
                MazeRunner runner = new RightHandRule();
                String pathFound = runner.escapeMaze(maze);
                System.out.println("Path: " + pathFound);
            }
            
        } catch(Exception e) {
            logger.error("Error: " + e.getMessage());
        }
    }
}
