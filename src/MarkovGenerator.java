//Programmer: Divya Chakkaram
//Date: Oct 13, 2020
//Description:

import java.util.ArrayList;
import java.util.Arrays;

public class MarkovGenerator<String> extends ProbabilityGenerator<String> {

	ArrayList<ArrayList<Integer>> transitionTable; // declaring an transitionTable ArrayList
	// ArrayList<T> alphabet; //declared an empty arraylist for alphabet
	ProbabilityGenerator<String> initTokenGen = new ProbabilityGenerator<String>();

	MarkovGenerator() {
		// to create the ArrayList
		transitionTable = new ArrayList();
		// alphabet = new ArrayList<T>();

	}
	// use a for loop to interate through the input string with i
	// have to expand transition table to expand the alphabet
	// transitionTable.get(lastIndex) <- this will give you your row array to update
	// the counts
	// row.get(tokenIndex) <- then add one to that lastIndex value??? minute 6.30
	// tokenIndex is always the size of the alphabet everytime you parse through the
	// input string
	// when you update your counts, always look at what the lastIndex was

	void train(ArrayList<String> input) { // training is the process of filling the empty transition table
		initTokenGen.train(input);

		int lastIndex = -1;

		for (int i = 0; i < input.size(); i++) { // for loop going one by one through the input ArrayList
			int tokenIndex = alphabet.indexOf(input.get(i)); // setting tokenIndex before changing values

			if (tokenIndex == -1) { // if the tokenIndex equals -1, then do this

				tokenIndex = alphabet.size(); // set tokenIndex to the size of alphabet if i is not found in alphabet

				ArrayList<Integer> myRow = new ArrayList(); // creating a new arrayList for a new row in the transition
															// table
				for (int j = 0; j < alphabet.size(); j++) { // iterating through the new row that's the size of alphabet
					myRow.add(0); // add a zero to the end of the row, aka add it to the column
				}

				transitionTable.add(myRow); // add myRow to transitionTable

				for (int k = 0; k < transitionTable.size(); k++) { // adding a zero to every specific row in
																	// transitionTable
					transitionTable.get(k).add(0); // get each row in transitionTable and then add a 0 to the end of it
				}

				alphabet.add(input.get(i)); // adding the current token to alphabet

			}

			// adding the counts now to the transition table
			if (lastIndex > -1) { // this indicates that it isn't the first time through because there is a
									// previous token
				ArrayList<Integer> rowCount = transitionTable.get(lastIndex); // getting the correct row from
																				// transitionTable with lastIndex
				int myElement = rowCount.get(tokenIndex);
				myElement++; // adding one to the cross referenced value
				rowCount.set(tokenIndex, myElement); // getting and setting the correct value in the column from the
														// tokenIndex
			}

			lastIndex = tokenIndex; // setting the current to the previous value for the next time through

		}

	}

	void printTransitionTable() {
		// System.out.println(alphabet);//print out the alphabet arraylist before going
		// through transition table/row arraylists

		for (int j = 0; j < transitionTable.size(); j++) { // iterating through the transition table
			ArrayList<Integer> sumrow = transitionTable.get(j); // initializing the arraylist sumrow to
																// trnasitiontable.get(j)
			float sum = 0; // initializing sum to 0
			for (int k = 0; k < sumrow.size(); k++) { // iterating through sumrow arraylist values one by one
				sum += sumrow.get(k); // adding the values to the variable sum
			}
			System.out.print(alphabet.get(j)); // printing out alphabet from iterating through transition table values
			for (int o = 0; o < sumrow.size(); o++) { // iterating through sumrow values again
				// if (sum == 0) { //if the value of sum = 0, print out "0.0" for the space
				// System.out.print(" 0.0 ");
				// }
				// else { //if sum doesn't equal 0, print out the values in the sumrow one by
				// one divided by the value of sum
				// System.out.print(" " + sumrow.get(o)/sum + " ");
				// }
			}

			System.out.println(); // println a space

		}
	}

	String generate(String initToken) {
		float index = alphabet.indexOf(initToken); // set index to index number of initToken in alphabet
		ArrayList<Integer> row = transitionTable.get((int) index); // declaring a single arrayList, row, to the row with
																	// the index found with initToken

		float total = 0; // initializing sum to 0
		for (int k = 0; k < row.size(); k++) { // iterating through row arraylist values one by one
			total += row.get(k); // adding the values to the variable total
		}
		if (total == 0) { // if the total counts = 0, return intTokenGen.generate()
			return initTokenGen.generate();
		}

		sum = total; // set total in MarkovGenerator to the value of sum in ProbabilityGenerator
		alphabet_counts = row; // set row in MarkovGenerator to the value of alphabet_counts in Probability
								// Generator
		return super.generate(); // return the generate method from probabilityGenerator, that is why super is
									// used here

	}

	ArrayList<String> generate(String initToken, int numberOfTokensToGenerate) { // this calls the method above

		ArrayList<String> newSequence = new ArrayList(); // newSequence is a new ArrayList
		String nextToken = initToken; // nextToken is set to initToken
		for (int i = 0; i < numberOfTokensToGenerate; i++) { // iterating through whatever the length of the melody
																// called is
			newSequence.add(nextToken); // add the nextToken variable to newSequence ArrayList
			nextToken = generate(nextToken); // nextToken is set to the value of generate(nextToken)
		}

		return newSequence; // return the newSequence ArrayList

	}

	ArrayList<String> generate(int length) { // this calls the method above
		return generate(initTokenGen.generate(), length);
	}

}
