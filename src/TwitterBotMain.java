/* Programmer: Divya Chakkaram
 * Date: October 20, 2020
 * This class is a template for creating a twitterbot & also demonstrated web-scraping
 */

import processing.core.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.jaunt.JauntException;

//This class serves as a template for creating twitterbots and demonstrates string tokenizing and web scraping and the use of the 
//twitter API
public class TwitterBotMain extends PApplet {

//	MarkovGenerator<String> hungergamestext = new MarkovGenerator<String>();

	private ArrayList<String> tokens;
	private static String HEYER_TWITTER_URL = "https://twitter.com/DChakkaram"; // this is mine, you should use
																				// yours
	private static int TWITTER_CHAR_LIMIT = 140; // I understand this has changed... but forget limit

	// useful constant strings -- for instance if you want to make sure your tweet
	// ends on a space or ending punctuation, etc.
	private static final String fPUNCTUATION = "\",.!?;:()/\\";
	private static final String fENDPUNCTUATION = ".!?;,";
	private static final String fREALENDPUNCTUATION = ".!?";

	private static final String fWHITESPACE = "\t\r\n ";

	// example twitter hastag search term
	private static final String fPASSIVEAGG = "passiveaggressive";
	private static final String fCOMMA = ",";

	// handles twitter api
	TwitterInteraction tweet;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("TwitterBotMain"); // Not really using processing functionality but ya know, you _could_. UI not
										// required.

	}

	public void settings() {
		size(500, 400);

	};

	public void setup() {
		background(45, 187, 245); // background - the twitter blue color
		fill(255, 255, 255); // fill color of title
		textSize(25);
		text("Hunger Games Twitter Bot", 85, 50);

		textSize(17);
		fill(240, 95, 36); // fill color for hunger games tweet generation
		text("Press the key 0 to generate a tweet from ", 80, 90);
		text("an excerpt of the Hunger Games.", 114, 110);

		fill(255, 255, 255); // fill color of unit test instructions
		textSize(17);
		text("Press the key 1, to run Unit Test 1 from Probability Gen", 13, 155);
		text("Press the key 2, to run Unit Test 2 from Probability Gen", 13, 185);
		text("Press the key 3, to run Unit Test 3 from Probability Gen", 13, 215);

		text("Press the key 4, to run Unit Test 1 from Markov Generator", 13, 260);
		text("Press the key 5, to run Unit Test 2 from Markov Generator", 13, 290);
		text("Press the key 6, to run Unit Test 3 from Markov Generator", 13, 320);

		textSize(13);
		fill(240, 95, 36); // fill color of twitter handle
		text("twitter.com/DChakkaram", 167, 360);
		tweet = new TwitterInteraction();

//NOTE: everything starts uncommented. Comment out the calls that you would like to try and use.

		loadNovel("data/hungergames.txt"); // TODO: must train from another source
		// using text from Suzanne Collins 2008 book, The Hunger Games
		// println("Token size:"+tokens.size());

		// TODO: train an AI algorithm (eg, Markov Chain) and generate text for markov
		// chain status

		// can train on twitter statuses -- note: in your code I would put this part in
		// a separate function
		// but anyhow, here is an example of searching twitter hashtag. You have to pay
		// $$ to the man to get more results. :(
		// see TwitterInteraction class
//		ArrayList<String> tweetResults = tweet.searchForTweets("John Cage");
//		for (int i = 0; i < tweetResults.size(); i++) {
//				println(tweetResults.get(i)); //just prints out the results for now
//		}

		// Make sure within Twitter limits (used to be 140 but now is more?)
//		String status = "testing twice";
//		tweet.updateTwitter(status);

		// prints the text content of the sites that come up with the google search of
		// dogs
//		//you may use this content to train your AI too
//		Scraper scraper = new Scraper(); 
//		ArrayList<String> results;
//		try {
//			results = scraper.scrapeGoogleResults("dogs");
//			
//			//print your results
//			System.out.println(results); 
//			
////			scraper.scrape("http://google.com",  "dogs"); //see class documentation
////
//		} catch (JauntException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}

	}

	// this loads the novel 'hungergames.txt' given a path p -- but really will load
	// any file.
	void loadNovel(String p) {
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		tokens = new ArrayList<String>();

		try {
			List<String> lines = Files.readAllLines(path);

			for (int i = 0; i < lines.size(); i++) {

				TextTokenizer tokenizer = new TextTokenizer(lines.get(i));
				ArrayList<String> t = tokenizer.parseSearchText();
				tokens.addAll(t);

			}

		} catch (Exception e) {
			e.printStackTrace();
			println("Oopsie! We had a problem reading a file!");
		}
	}

	void printTokens() {
		for (int i = 0; i < tokens.size(); i++)
			print(tokens.get(i) + " ");
	}

	// get the relative file path
	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	public void draw() {
		// ellipse(width / 2, height / 2, second(), second());

	}

	public void keyPressed() {
		MarkovGenerator<String> mpitchGenerator = new MarkovGenerator<String>(); // creating MarkovGenerator
		MarkovGenerator<String> mrhythmGenerator = new MarkovGenerator<String>();// mPitchGenerator

		ProbabilityGenerator<String> pitchGenerator = new ProbabilityGenerator<String>(); //creaing pitchGens 
		ProbabilityGenerator<String> rhythmGenerator = new ProbabilityGenerator<String>();

		if (key == '0') { // if you press 0
			MarkovGenerator<String> hungergamestext = new MarkovGenerator<String>(); // creating a new markov generator
																						// hungergamestext
			hungergamestext.train(tokens); // train the new markovgenerator with input tokens based on hunger games
											// excerpt text loaded

			ArrayList<String> sentence = new ArrayList<>(hungergamestext.generate(2)); // fill a new ArrayList of
																						// strings, sentence, with the
																						// generated tokens from input
																						// and with markov generator
																						// hungergamestext
			StringBuilder twitterString = new StringBuilder(); // create a new StringBuilder, twitterString
			for (String t : sentence) {
				twitterString.append(t); // append the StringBuilder with sentence ArrayList of strings to the single
											// String, t
			}
			String newTweet = twitterString.toString(); // assign String newTweet with twitterString.toString()
			tweet.updateTwitter(newTweet); // post to twitter with the newTweet String

		} else if (key == '1') {
			pitchGenerator.train(tokens);
			rhythmGenerator.train(tokens);

			pitchGenerator.printProbability(); // prints the probability distribution values and tokens for the pitches
			rhythmGenerator.printProbability();// prints the probability distribution values and tokens for the rhythms

		} else if (key == '2') {
			pitchGenerator.train(tokens);
			rhythmGenerator.train(tokens);

			System.out.println(pitchGenerator.generate(20)); // prints out the notes for the pitches from the generated
																// melody
			System.out.println(rhythmGenerator.generate(20)); // prints out the rhythms from the generated melody

		} else if (key == '3') {
			pitchGenerator.train(tokens);
			rhythmGenerator.train(tokens);

			ProbabilityGenerator<String> probDistGenPitch = new ProbabilityGenerator<String>(); 
			ProbabilityGenerator<String> probDistGenRhythm = new ProbabilityGenerator<String>(); 
			
			for (int i = 1; i <= 10000; i++) { // for loop going through the 10000 melodies
				ArrayList<String> newSongPitch = pitchGenerator.generate(20);
				ArrayList<String> newSongRhythm = rhythmGenerator.generate(20); 
																				
				probDistGenPitch.train(newSongPitch); 
				probDistGenRhythm.train(newSongRhythm); 
			}

			probDistGenPitch.printProbability();
			probDistGenRhythm.printProbability(); 


		} else if (key == '4') { // if you press key 1
			mpitchGenerator.train(tokens); // train the markovGenerator with input tokens from text
			mrhythmGenerator.train(tokens); // train the markovGenerator with input tokens from text

			mpitchGenerator.printTransitionTable(); // print the values trained
			mrhythmGenerator.printTransitionTable(); // print the values trained

		} else if (key == '5') {
			mpitchGenerator.train(tokens); // train the markovGenerator with the input tokens from text
			mrhythmGenerator.train(tokens); // train the markovGenerator with input tokens from text

			System.out.println(mpitchGenerator.generate(20)); // print the 20 tokens generated
			System.out.println(mrhythmGenerator.generate(20)); // print the 20 tokens generated

		} else if (key == '6') {
			mpitchGenerator.train(tokens); // train the markovgenerator with input tokens from text
			mrhythmGenerator.train(tokens); // train the markovGenerator with input tokens from text

			MarkovGenerator<String> markovGenPitches = new MarkovGenerator<String>(); // create a new String markov gen
			MarkovGenerator<String> markovGenRhythms = new MarkovGenerator<String>(); // create a new String markov gen

			for (int i = 0; i <= 10000; i++) { // for loop from 1 through 10,000
				ArrayList<String> newSongPitch = mpitchGenerator.generate(20); // create an ArrayList of strings, filled
				ArrayList<String> newSongRhythm = mrhythmGenerator.generate(20); // with generated String tokens

				markovGenPitches.train(newSongPitch); // train new markov generator with input ArrayList of newSongPitch
				markovGenRhythms.train(newSongRhythm); // train new markov generator with input ArrayList of newSongPitch
			}

			markovGenPitches.printTransitionTable(); // print the values based on the trained markovGenPitches
			markovGenRhythms.printTransitionTable();  // print the values based on the trained markovGenPitches

		}
	}
}