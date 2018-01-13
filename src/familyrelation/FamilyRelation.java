package familyrelation;

import java.util.Scanner;

/*
 * instantiate a new Relative object
 * gender is set by last element of input array
 * There needs to be a tree like structure to mirror the family tree
 * where as the input string is parsed, it narrows down possibilities
 * until it isolates one, or arrives at a relationship without a name
 * basic identifiable relationships: ((great)grand*) son / daughter (in-law),
 * father / mother (in-law), uncle / aunt; cousin, nephew / niece
 * 
 * grand child, niece/neph, cousin are all lastly determined as someone's child
 */

public class FamilyRelation {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		String input = "";
		Relative rel;
		String helpMessage = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" + 
							"Start with 'my' and write an expression using any of the following terms\n" +
							"mother, father, wife, husband, sister, brother, son, daughter\n" +
							"> \"my father's mother's sister\" returns \"grand aunt\"\n" +
							"To quit, enter \"quit\"\n" +
							"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

		while (!input.equals("quit")) {
			System.out.print(
					"Enter a family relationships (or 'help'): ");
			input = console.nextLine();
			if (input.equals("help")) {
				System.out.println(helpMessage);
			} else if (!input.equals("quit")) {
				rel = new Relative(input.split(" "));
				System.out.println(rel);
				System.out.println();
			}
		}
		System.out.println("Program quitting...");
		console.close();
	}
}

enum Gender {
	FEMALE, MALE
}

class Relative {
	private Gender gender = null;
	private int generation;
	private boolean isInLaw = false;
	private String[] relation;
	private String[] females = { "sister", "mother", "daughter", "wife" };
	private String[] males = { "brother", "father", "son", "husband" };
	private String relationship = "";
	private String finalTerm;

	public Relative(String[] relation) {
		this.relation = relation;
		try {
			this.gender = parseGender(relation[relation.length - 1]);
		} catch (GenderException e) {
			e.printStackTrace();
		}
		parseRelation();
	}
	public boolean isParent(String input) {
		return (input.startsWith("mother") || input.startsWith("father"));
	}
	public boolean isSpouse(String input) {
		return (input.startsWith("wife") || input.startsWith("husband"));
	}
	public boolean isChild(String input) {
		return (input.startsWith("daughter") || input.startsWith("son"));
	}
	public boolean isSibling(String input) {
		return (input.startsWith("brother") || input.startsWith("sister"));
	}
	
	public void parseRelation() {

		finalTerm = "";
		
		if (relation[1].startsWith("husband") || relation[1].startsWith("wife")) {
			isInLaw = true;
		}

		for (int i = 1; i < relation.length; i++) {
			generation += (isParent(relation[i])) ? 1 : 0;
			generation += (isChild(relation[i])) ? -1 : 0;
			if (i > 1 && isChild(relation[i])) {
				if (isSibling(relation[i - 1])) {
					if (generation < 0) {
						finalTerm = (gender == Gender.FEMALE) ? "niece" : "nephew";
					} else {
						finalTerm = "cousin";
					}
				}
			}
			if (i > 1 && isSibling(relation[i])) {
				if (isParent(relation[i - 1])) {
					finalTerm = (gender == Gender.FEMALE) ? "aunt" : "uncle";
				}
			}
			if (i > 1 && i == relation.length - 1) {
				if (isSpouse(relation[i])) {
					if (isChild(relation[i - 1])) {
						finalTerm = (gender == Gender.FEMALE) ? "daughter" : "son";
						isInLaw = true;
					}
				}
			}
			if (i > 1 && i == relation.length - 1) {
				if (isSpouse(relation[i - 1])) {
					if (isParent(relation[i])) {
						finalTerm = (gender == Gender.FEMALE) ? "mother" : "father";
					} else if (isSibling(relation[i])) {
						finalTerm = (gender == Gender.FEMALE) ? "sister" : "brother";
					}
					isInLaw = true;
				}
			}
		} // for
		
		
		// start with 'great's and 'grand's
		if (generation > 1) {
			for (int i = generation; i > 1; i--) {
				if (i > 2) {
					relationship += "great ";
				}
				if (i == 2) {
					relationship += "grand ";
				}
			}
		}
		if (generation < -1) {
			for (int i = generation; i < 1; i++) {
				if (i < -2) {
					relationship += "great ";
				}
				if (i == -2) {
					relationship += "grand ";
				}
			}
		}
		// Final term
		if (finalTerm.length() > 0) {
			relationship += finalTerm;
			// in-law suffix
			if (isInLaw) {
				relationship += " in-law";
			}
		} else {
			relationship = "(no relationship found)";
		}
		
	}

	public String toString() {
		String output = "Your ";
		for (int i = 1; i < relation.length; i++) {
			output += relation[i] + " ";
		}
		output += "is your " + relationship;
		return output + ".";
	}

	private Gender parseGender(String input) throws GenderException {
		Gender result = Gender.FEMALE;

		boolean found = false;
		for (int i = 0; i < females.length; i++) {
			if (females[i].equals(input)) {
				found = true;
			}
		}
		for (int i = 0; i < males.length; i++) {
			if (males[i].equals(input)) {
				result = Gender.MALE;
				found = true;
			}
		}
		if (!found) {
			throw new GenderException();
		}

		return result;
	}

	class GenderException extends Exception {
		public GenderException(String message) {
			super(message);
		}

		public GenderException() {
			super();
		}
	}
}