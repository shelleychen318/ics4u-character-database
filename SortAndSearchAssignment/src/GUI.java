/*************************************************************************************************************************************
 Name: Shelley Chen
 
 Course Code: ICS4U0 A
 
 Program Name: Sort and Search (implemented in Inheritance Assignment)
 
 Date: March 25, 2022
 
 Description: This is a character creation GUI program that allows the user to add, edit, delete, and view characters of type vampire,
 witch, and vampire-werewolf. The characters' data gets saved to a text file once the user closes the program. All characters are 
 sorted, by name, in thier respective object arrays and in the master combo box using the insertion sort algorithm. When the program
 looks for a match for the edit, delete, and view functions, the binary search algorthim is used.
 
 Insertion Sort code: line 1015
 Binary Search code: line 1074
 
 *************************************************************************************************************************************/
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GUI extends JFrame {


	private JPanel contentPane;
	static JTextField nameTextField;
	static JLabel charTypeLabel, nameLabel, nameNoteLabel, genderLabel, eyeColourLabel, powersLabel, spellsLabel, furColourLabel, charListLabel;
	static JComboBox charTypeComboBox, genderComboBox, eyeColourComboBox, powersComboBox, spellsComboBox, furColourComboBox, charListComboBox;
	static String charName, charType, charGender, charEyeColour, charPower, charSpell, charFurColour, charChoice;
	static JButton addButton, editButton, deleteButton, viewButton, saveButton, cancelButton;
	static int indexVamp = 0, indexWitch = 0, indexVampWere = 0, task, clc = 0, editCharInd, charCount = 0;
	static Vampire[] vampires = new Vampire[10];
	static Witch[] witches = new Witch[10];
	static VampireWerewolf[] vampireWerewolves = new VampireWerewolf[10];
	static Vampire vampire;
	static Witch witch;
	static VampireWerewolf vampireWerewolf;
	static String[] charListComboList = new String[30];
	static boolean removeAll = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					readFromFile(); // call read from file method when program is launched
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Character Creation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.window);
		panel.setBounds(6, 6, 447, 453);
		contentPane.add(panel);
		panel.setLayout(null);

		addButton = new JButton("Add");
		addButton.setBounds(33, 28, 83, 29);
		panel.add(addButton);

		JLabel titleLabel = new JLabel("Create a character!");
		titleLabel.setBounds(160, 6, 117, 16);
		panel.add(titleLabel);

		deleteButton = new JButton("Delete");
		deleteButton.setBounds(223, 28, 83, 29);
		panel.add(deleteButton);
		deleteButton.setVisible(false);

		editButton = new JButton("Edit");
		editButton.setBounds(128, 28, 83, 29);
		panel.add(editButton);
		editButton.setVisible(false);

		viewButton = new JButton("View");
		viewButton.setBounds(318, 28, 83, 29);
		panel.add(viewButton);
		viewButton.setVisible(false);

		saveButton = new JButton("Save");
		saveButton.setBounds(86, 403, 117, 29);
		panel.add(saveButton);
		saveButton.setVisible(false); // set save button to be invisible at program launch

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(235, 403, 117, 29);
		panel.add(cancelButton);
		cancelButton.setVisible(false); // set cancel button to be invisible at program launch

		String genderComboList[] = {"none", "Male", "Female"};
		genderComboBox = new JComboBox(genderComboList);
		genderComboBox.setBounds(177, 189, 145, 27);
		panel.add(genderComboBox);
		genderComboBox.setVisible(false); // set combo box to be invisible at program launch

		nameLabel = new JLabel("Enter character name:");
		nameLabel.setBounds(33, 137, 145, 16);
		panel.add(nameLabel);
		nameLabel.setVisible(false); // set name label to be invisible at program launch

		nameTextField = new JTextField();
		nameTextField.setBounds(177, 132, 225, 26);
		panel.add(nameTextField);
		nameTextField.setColumns(10);
		nameTextField.setVisible(false); // set name text field to be invisible at program launch

		genderLabel = new JLabel("Select gender:");
		genderLabel.setBounds(33, 193, 130, 16);
		panel.add(genderLabel);
		genderLabel.setVisible(false); // set gender label to be invisible at program launch

		eyeColourLabel = new JLabel("Select eye colour:");
		eyeColourLabel.setBounds(33, 240, 130, 16);
		panel.add(eyeColourLabel);
		eyeColourLabel.setVisible(false); // set eye colour label to be invisible at program launch

		String eyeColourComboList[] = {"none", "brown", "blue", "green"};
		eyeColourComboBox = new JComboBox(eyeColourComboList);
		eyeColourComboBox.setBounds(177, 236, 145, 27);
		panel.add(eyeColourComboBox);
		eyeColourComboBox.setVisible(false); // set eye colour combo box to be invisible at program launch

		charTypeLabel = new JLabel("Select character type:");
		charTypeLabel.setBounds(33, 102, 145, 16);
		panel.add(charTypeLabel);
		charTypeLabel.setVisible(false); // set character type label to be invisible at program launch

		String charTypeComboList[] = {"Vampire", "Witch", "Vampire-Werewolf hybrid"};
		charTypeComboBox = new JComboBox(charTypeComboList);
		charTypeComboBox.setBounds(171, 98, 231, 27);
		panel.add(charTypeComboBox);
		charTypeComboBox.setVisible(false);

		powersLabel = new JLabel("Select power:");
		powersLabel.setBounds(33, 284, 95, 16);
		panel.add(powersLabel);
		powersLabel.setVisible(false);

		String powersComboList[] = {"none", "immortality", "speed", "telepathy"};
		powersComboBox = new JComboBox(powersComboList);
		powersComboBox.setBounds(177, 280, 145, 27);
		panel.add(powersComboBox);
		powersComboBox.setVisible(false); // set character type combo box to be invisible at program launch

		spellsLabel = new JLabel("Select witch spell:");
		spellsLabel.setBounds(33, 284, 130, 16);
		panel.add(spellsLabel);
		spellsLabel.setVisible(false);

		String spellsComboList[] = {"none", "healing spell", "shield spell", "cloaking spell"};
		spellsComboBox = new JComboBox(spellsComboList);
		spellsComboBox.setBounds(177, 280, 145, 27);
		panel.add(spellsComboBox);
		spellsComboBox.setVisible(false);

		furColourLabel = new JLabel("Select fur colour:");
		furColourLabel.setBounds(33, 327, 130, 16);
		panel.add(furColourLabel);
		furColourLabel.setVisible(false);

		String furColourComboList[] = {"none", "black", "brown", "grey"};
		furColourComboBox = new JComboBox(furColourComboList);
		furColourComboBox.setBounds(177, 323, 145, 27);
		panel.add(furColourComboBox);
		furColourComboBox.setVisible(false);

		charListComboBox = new JComboBox();
		charListComboBox.setBounds(171, 69, 229, 27);
		panel.add(charListComboBox);
		charListComboBox.setVisible(false);

		charListLabel = new JLabel("Select Character:");
		charListLabel.setBounds(33, 74, 130, 16);
		panel.add(charListLabel);
		charListLabel.setVisible(false);
		
		nameNoteLabel = new JLabel("Type a name and press enter");
		nameNoteLabel.setBounds(128, 165, 189, 16);
		panel.add(nameNoteLabel);
		nameNoteLabel.setVisible(false);
		
		JLabel checkNameLabel = new JLabel("This name already exists, please enter a different one.");
		checkNameLabel.setForeground(Color.RED);
		checkNameLabel.setBounds(45, 193, 354, 16);
		panel.add(checkNameLabel);
		checkNameLabel.setVisible(false);
		
		addButton.addActionListener(new ActionListener() { // when user clicks add button
			public void actionPerformed(ActionEvent e) {
				task = 0; // indicates that user is using add function
				saveButton.setText("Save"); // change save button to say save
				nameTextField.setEnabled(true);
				genderComboBox.setEnabled(true); // make attributes editable
				eyeColourComboBox.setEnabled(true);
				spellsComboBox.setEnabled(true);
				powersComboBox.setEnabled(true);
				furColourComboBox.setEnabled(true);
				charListLabel.setVisible(false);
				charListComboBox.setVisible(false);
				charTypeLabel.setVisible(true);
				charTypeComboBox.setVisible(true);
				charTypeComboBox.setEnabled(true);
				genderComboBox.setSelectedIndex(0); // auto-select first item in combo box
				eyeColourComboBox.setSelectedIndex(0);
				spellsComboBox.setSelectedIndex(0);
				powersComboBox.setSelectedIndex(0);
				furColourComboBox.setSelectedIndex(0);
				nameTextField.setText("");
				displayOptions(0); // call method to hide add, edit, delete, and view button
				}
			});

		editButton.addActionListener(new ActionListener() { // when user clicks edit button
			public void actionPerformed(ActionEvent e) {
				updateCharList(); // updates character list
				charListLabel.setVisible(true); // display drop down list of character names
				task = 1; // indicates that user is using edit function
				saveButton.setText("Save"); // change save button to say save
				editCharInd = findCharacter(charChoice); // call find character method, save index value of character user wants to edit
				charListComboBox.setVisible(true);
				charTypeLabel.setVisible(false); // hide character type label and combo box
				charTypeComboBox.setVisible(false);
				genderComboBox.setEnabled(true); // display editable attributes
				eyeColourComboBox.setEnabled(true);
				spellsComboBox.setEnabled(true);
				powersComboBox.setEnabled(true);
				furColourComboBox.setEnabled(true);
				saveButton.setVisible(true); // display save and cancel buttons
				cancelButton.setVisible(true);
				displayOptions(0); // call method to hide out add, edit, delete, view functions
			}
		});

		deleteButton.addActionListener(new ActionListener() { // when user clicks delete button
			public void actionPerformed(ActionEvent e) {
				task = 2; // indicates that user is using delete function
				saveButton.setText("Delete"); // change save button to say delete
				displayOptions(0); // call method to hide out add, edit, delete, view functions
				saveButton.setVisible(true);
				cancelButton.setVisible(true);
				charListLabel.setVisible(true);
				charListComboBox.setVisible(true);
				charTypeLabel.setVisible(false);
				charTypeComboBox.setVisible(false);
				genderLabel.setVisible(false); // hide all character attribute labels and combo boxes
				genderComboBox.setVisible(false);
				eyeColourLabel.setVisible(false);
				eyeColourComboBox.setVisible(false);
				powersLabel.setVisible(false);
				powersComboBox.setVisible(false);
				spellsLabel.setVisible(false);
				spellsComboBox.setVisible(false);
				furColourLabel.setVisible(false);
				furColourComboBox.setVisible(false);
				updateCharList(); // update character list combo box

			}
		});

		viewButton.addActionListener(new ActionListener() { // when user clicks view button
			public void actionPerformed(ActionEvent e) {
				task = 3; // indicates that user is using view function
				updateCharList(); // updates character list combo box
				displayOptions(0); // call method to hide out add, edit, delete, view functions
				editCharInd = findCharacter(charChoice); // call find character method, save index value of character user wants to edit
				charListLabel.setVisible(true);
				charListComboBox.setVisible(true);
				charTypeLabel.setVisible(false);
				charTypeComboBox.setVisible(false);
				genderComboBox.setEnabled(false);
				eyeColourComboBox.setEnabled(false);
				spellsComboBox.setEnabled(false);
				powersComboBox.setEnabled(false);
				furColourComboBox.setEnabled(false);
				saveButton.setVisible(false);
				cancelButton.setVisible(true);
			}
		});

		charTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameLabel.setVisible(true); // allow user to enter character name
				nameTextField.setVisible(true);
				nameNoteLabel.setVisible(true);
				charType = (String)charTypeComboBox.getSelectedItem(); // get user's character type choice, convert to string type, store in variable
				charTypeComboBox.setEnabled(false);
			}
		});

		nameTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				charName = nameTextField.getText(); // assign user's character name to a variable

				boolean check = checkName(charName); // cell method to check if name already exists

				// if user enters a name that already exists in database
				if (!check)
				{
					checkNameLabel.setVisible(true); // prompt user to enter a different name
					charName = nameTextField.getText(); // get new name
					check = checkName (charName); // call method to check name again
				}
				// else if user enters a unique name
				else
				{
					checkNameLabel.setVisible(false);
					saveButton.setVisible(true); // present save and cancel buttons
					cancelButton.setVisible(true);
					nameTextField.setEnabled(false);
					displayOptions(1); // display base attributes
					if (charType.equals("Vampire")) // if user selects vampire
					{
						displayOptions(2); // call method to display vampire options
					}
					else if (charType.equals("Witch")) // if user selects witch
					{ 
						displayOptions(3); // call method to display witch options
					}
					else if (charType.equals("Vampire-Werewolf hybrid")) 
					{
						displayOptions(4); // call method to display vampire-werewolf options
					}
				}
			}
				
		});

		genderComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charGender = (String)genderComboBox.getSelectedItem(); // get user's gender choice, convert to string type, store in variable
			}
		});

		eyeColourComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charEyeColour =(String)eyeColourComboBox.getSelectedItem(); // get user's eye colour choice, convert to string type, store in variable
			}
		});

		powersComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charPower =(String)powersComboBox.getSelectedItem(); // get user's power choice, convert to string type, store in variable
			}
		});

		spellsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charSpell = (String)spellsComboBox.getSelectedItem(); // get user's spell choice, convert to string type, store in variable
			}
		});

		furColourComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charFurColour =(String)furColourComboBox.getSelectedItem(); // get user's fur colour choice, convert to string type, store in variable
			}
		});

		charListComboBox.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				
				if (!removeAll) // if we are not doing a remove 
				{
					charListComboBox.setSelectedItem(0);
					charChoice = (String) charListComboBox.getSelectedItem(); // get selected item from combo box
//					System.out.println("charListComboBox listner - char choice: " + charChoice);
					editCharInd = findCharacter(charChoice); // call method to find character that user has selected
				}
				
				if (task == 2) // if user is using delete function
				{
					genderLabel.setVisible(false); // hide all attributes
					genderComboBox.setVisible(false);
					eyeColourLabel.setVisible(false);
					eyeColourComboBox.setVisible(false);
					powersLabel.setVisible(false);
					powersComboBox.setVisible(false);
					spellsLabel.setVisible(false);
					spellsComboBox.setVisible(false);
					furColourLabel.setVisible(false);
					furColourComboBox.setVisible(false);
				}
			}
		});

		saveButton.addActionListener(new ActionListener() { // when user clicks save button
			public void actionPerformed(ActionEvent e) {
				if (task == 0) // if user is using add function
				{
					charCount++; // increment character count by one
					if (charType.equals("Vampire")) // if user creates a vampire character
					{
						addCharacter(1); // call method to save character
					}
					else if (charType.equals("Witch")) // if user creates a witch character
					{
						addCharacter(2); // call method to save character
					}
					else if (charType.equals("Vampire-Werewolf hybrid")) // if user creates a vampire object			
					{
						addCharacter(3); // call method to save character
					}
				} 
				else if (task == 1) // if user is using edit function
				{
					if (charType.equals("Vampire")) // if user is editing a vampire
					{						
						vampires[editCharInd].setGender((String)genderComboBox.getSelectedItem()); // edit attributes in object array
						vampires[editCharInd].setEyeColour((String)eyeColourComboBox.getSelectedItem());
						vampires[editCharInd].setPower((String)powersComboBox.getSelectedItem());
					}
					else if (charType.equals("Witch")) // if user is editing a witch
					{
						witches[editCharInd].setGender((String)genderComboBox.getSelectedItem()); // edit attributes in object array
						witches[editCharInd].setEyeColour((String)eyeColourComboBox.getSelectedItem());
						witches[editCharInd].setSpell((String)spellsComboBox.getSelectedItem());
					}
					else if (charType.equals("Vampire-Werewolf hybrid")) // if user is editing a vampire-werewolf
					{
						vampireWerewolves[editCharInd].setGender((String)genderComboBox.getSelectedItem()); // edit attributes in object array
						vampireWerewolves[editCharInd].setEyeColour((String)eyeColourComboBox.getSelectedItem());
						vampireWerewolves[editCharInd].setPower((String)powersComboBox.getSelectedItem());
						vampireWerewolves[editCharInd].setFurColour((String)furColourComboBox.getSelectedItem());
					}
				}

				else if (task == 2) // if user is using delete function
				{
					charCount--; // decrement character count by one
					deleteCharacter(charChoice); // call method to delete character
				}
				displayOptions(5); // call method to hide attributes

				if (charCount > 0) // if user has created at least one character
				{
					displayOptions(6); // display all four buttons (add, edit, delete, view)
				} 
				else if (charCount <= 0)  // if user has zero saved characters
				{
					displayOptions(7); // only show add button
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() { // when user clicks cancel button
			public void actionPerformed(ActionEvent e) {
				displayOptions(5); // call method to hide attributes
				if (charCount > 0) // if user has created at least one character
				{
					displayOptions(6); // display all four functions (add, edit, delete, view)
				} 
				else    // if user has zero saved characters
				{
					displayOptions(7); // only show add button
				}
			}
		});
		
		// when user clicks the X button, write to file and close the program
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.out.println("exiting program");
		    	writeToFile();
	            System.exit(0);
		    }
		});

	} // GUI

	// method to display and hide buttons
	static void displayOptions(int k) 
	{
		if (k == 0) // if user selects one of the four functions
		{
			addButton.setVisible(false); // hide add, edit, delete, and view buttons
			editButton.setVisible(false);
			deleteButton.setVisible(false);
			viewButton.setVisible(false);
		}
		else if (k == 1) // if user selects a character type from combo box
		{
			genderLabel.setVisible(true); // display base attributes
			genderComboBox.setVisible(true);
			eyeColourLabel.setVisible(true);
			eyeColourComboBox.setVisible(true);
		}
		else if (k == 2) // if user selects vampire as character type
		{
			powersLabel.setVisible(true); // display powers options
			powersComboBox.setVisible(true);
			spellsLabel.setVisible(false); // hide spells and fur colour options
			spellsComboBox.setVisible(false);
			furColourLabel.setVisible(false);
			furColourComboBox.setVisible(false);
		}
		else if (k == 3) // if user selects witch as character type
		{
			spellsLabel.setVisible(true); // display spells options
			spellsComboBox.setVisible(true);
			powersLabel.setVisible(false); // hide powers and fur colour options
			powersComboBox.setVisible(false);
			furColourLabel.setVisible(false);
			furColourComboBox.setVisible(false);
		}
		else if (k == 4) // if user selects vampire-werewolf as character type
		{
			powersLabel.setVisible(true); // display powers and fur colours options
			powersComboBox.setVisible(true);
			furColourLabel.setVisible(true);
			furColourComboBox.setVisible(true);
			spellsLabel.setVisible(false); // hide spells options
			spellsComboBox.setVisible(false);
		}
		else if (k == 5) // if user clicks save or cancel button
		{
			charTypeLabel.setVisible(false); // hide all attributes
			charTypeComboBox.setVisible(false);
			charListLabel.setVisible(false);
			charListComboBox.setVisible(false);
			nameLabel.setVisible(false);
			nameTextField.setVisible(false);
			nameNoteLabel.setVisible(false);
			genderLabel.setVisible(false);
			genderComboBox.setVisible(false);
			eyeColourLabel.setVisible(false);
			eyeColourComboBox.setVisible(false);
			spellsLabel.setVisible(false);
			spellsComboBox.setVisible(false);
			powersLabel.setVisible(false);
			powersComboBox.setVisible(false);
			furColourLabel.setVisible(false);
			furColourComboBox.setVisible(false);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
			addButton.setVisible(true); // show add, edit, delete, and view buttons
			editButton.setVisible(true);
			deleteButton.setVisible(true);
			viewButton.setVisible(true);
		}
		else if (k == 6) // if user clicks save or cancel button
		{
			addButton.setVisible(true);
			editButton.setVisible(true);
			deleteButton.setVisible(true);
			viewButton.setVisible(true);
		}
		else if (k == 7) // if user doesn't have any characters saved
		{ 
			addButton.setVisible(true); // only show add button
			editButton.setVisible(false);
			deleteButton.setVisible(false);
			viewButton.setVisible(false);
		}
	}

	// method to check if user enters a name that already exists in the database
	static boolean checkName(String nameEntered)
	{		
		int result = binarySearch(charListComboList, nameEntered); // call binary search method
		
		if (result >= 0)
		{
			return false; // indicates that name already exists in database
		}
		else
		{
			return true; // indicates that the name the user entered is unique
		}
	}

	// method to add a character to object array
	static void addCharacter(int charTypeInt) 
	{
//		System.out.println("addCharacter method, clc: " + clc);	
//		System.out.println("charCount: "+charCount);

		String newName = "";
		if (charTypeInt == 1) // if user creates a vampire character
		{
			vampire = new Vampire(charName, charGender, charEyeColour, charPower); // instantiate new vampire object
			insertionSort(vampires, vampire, indexVamp);
			indexVamp++; // increment index value of array by one
		}
		else if (charTypeInt == 2) // if user creates a witch character
		{
			witch = new Witch(charName, charGender, charEyeColour, charSpell); // instantiate new witch object
			insertionSort(witches, witch, indexWitch);
			indexWitch++; // increment index value of array by one
		}
		else if (charTypeInt == 3) // if user creates a vampire-werewolf character
		{
			vampireWerewolf = new VampireWerewolf(charName, charGender, charEyeColour, charPower, charFurColour); // instantiate new vampire-werewolf object
			insertionSort(vampireWerewolves, vampireWerewolf, indexVampWere);
			indexVampWere++;     // increment index value of array by one
		}
		newName = charName;
		insertionSort(charListComboList, newName, clc);
		clc++; // increment character list counter by one
		
//		for (int i = 0; i < clc+1; i++)
//		{
//			System.out.println(charListComboList[i]);
//		}
	}
	
	// overload method for readFromFile method, passing in Character object as a parameter
	static void addCharacter(int charTypeInt, Character newChar) 
	{
		if (charTypeInt == 1) // if user creates a vampire character
		{
			vampires[indexVamp] = (Vampire) newChar; // add vampire object pointer to array
			charListComboList[clc] = vampires[indexVamp].getName(); // add name to combo list
			indexVamp++; // increment index value of array by one
		}
		else if (charTypeInt == 2) // if user creates a witch character
		{
			witches[indexWitch] = (Witch) newChar;  // add witch object pointer to array
			charListComboList[clc] = witches[indexWitch].getName();
			indexWitch++; // increment index value of array by one
		}
		else if (charTypeInt == 3) // if user creates a vampire-werewolf character
		{
			vampireWerewolves[indexVampWere] = (VampireWerewolf) newChar; //vampireWerewolf; // add vampire-werewolf object pointer to array
			charListComboList[clc] = vampireWerewolves[indexVampWere].getName();
			indexVampWere++;     // increment index value of array by one
		}
		clc++;
		charCount++;
	}

	// method to update list of character names
	static void updateCharList() 
	{
//		System.out.println("clc in updateCharList: " + clc);
		if (charListComboBox.getItemCount() > 0) // if there is at least one item in char list combo box
		{
			removeAll = true; // indicate that we are doing a remove
			charListComboBox.removeAllItems(); // remove all items
			removeAll = false; // indicate that remove has been done
		}
		for (int i = 0; i < clc; i++) // loop through character list
		{
			charListComboBox.addItem(charListComboList[i]); // add character from list into combo box
		}
		charListComboBox.setSelectedIndex(0);
	}
	
	// method to find character that user wants to edit/delete/view
	static int findCharacter(String name) 
	{
		int editInd = 0;
		String[] names = new String[10];
		int counter = 0;
//		System.out.println("findChararacter - name: " + name);
		
		// finding the name in the vampire array
		for (int i = 0; i < indexVamp; i++) // loop through vampire object array
		{
			// add vampire names to array
			names[counter] = (String)(vampires[i].getName()); 
			counter++;
		}

		editInd = binarySearch(names, name); // call method to get index of wanted character in vampire array
		
		// if name is found in vampire array
		if (editInd >= 0)
		{
			displayOptions(1); // display base options
			displayOptions(2); // display vampire options
			displayAttributesV(editInd); // display saved attributes for vampire character
			charType = "Vampire";
			return editInd;
		}
		
		// finding the name in the witch array
		counter = 0; // re-initialize counter to 0
		for (int j = 0; j < indexWitch; j++) // loop through witch object array
		{
			// add witch names to array
			names[counter] = (String)(witches[j].getName()); 
			counter++;
		}

		editInd = binarySearch(names, name); // call method to get index of wanted character in witch array
		
		// if name is found in witch array
		if (editInd >= 0)
		{
			displayOptions(1); // display base options
			displayOptions(3); // display witch options
			displayAttributesW(editInd); // display saved witch attributes
			charType = "Witch";
			return editInd;
		}
		
		// finding the name in vampire-werewolf array
		counter = 0;
		for (int k = 0; k < indexVampWere; k++) // loop through vampire-werewolf array
		{	
			// add vampire-werewolf names to array
			names[counter] = (String)(vampireWerewolves[k].getName()); 
			counter++;
		}
		
		editInd = binarySearch(names, name); // call methdo to get index of wanted character in vampire-witch array
		
		if (editInd >= 0)
		{
			displayOptions(1); // display base options
			displayOptions(4); // display vampire-werewolf options
			displayAttributesVW(editInd); // display saved vampire werewolf attributes
			charType = "Vampire-Werewolf hybrid";
			return editInd;
		}
		
		return editInd; // return index value of object in array that user wants to edit
	}

	// method to present user's saved attributes in combo boxes for vampire characters
	static void displayAttributesV(int index) 
	{
		String gender = (String)(vampires[index].getGender()); // get gender of vampire character, cast to string, store in variable
		if (gender.equals("Male"))      // if character is male
		{
			genderComboBox.setSelectedIndex(1); // default select male in combo box
		} 
		else if (gender.equals("Female"))   // if character is female
		{
			genderComboBox.setSelectedIndex(2); // default select female in combo box
		} 
		else
		{
			genderComboBox.setSelectedIndex(0);
		}

		String eye = (String)(vampires[index].getEyeColour()); // get eye colour of vampire character
		if (eye.equals("brown")) // if eye colour is brown
		{
			eyeColourComboBox.setSelectedIndex(1); // default select brown in combo box
		} 
		else if (eye.equals("blue"))
		{
			eyeColourComboBox.setSelectedIndex(2);
		} 
		else if (eye.equals("green")) 
		{
			eyeColourComboBox.setSelectedIndex(3);
		} else 
		{
			eyeColourComboBox.setSelectedIndex(0);
		}

		String power = (String)(vampires[index].getPower()); // get power of vampire character
		if (power.equals("immortality")) // if power is immortality
		{
			powersComboBox.setSelectedIndex(1); // default select immortality in combo box
		} 
		else if (power.equals("speed"))
		{
			powersComboBox.setSelectedIndex(2);
		} 
		else if (power.equals("telepathy")) 
		{
			powersComboBox.setSelectedIndex(3);
		} 
		else 
		{
			powersComboBox.setSelectedIndex(0);
		}
	}

	// method to present user's saved attributes in combo boxes for witch characters
	static void displayAttributesW(int index) 
	{
		String gender = (String)(witches[index].getGender()); // get gender of vampire character, cast to string, store in variable
		if (gender.equals("Male"))      // if character is male
		{
			genderComboBox.setSelectedIndex(1); // default select male in combo box
		} 
		else if (gender.equals("Female"))   // if character is female
		{
			genderComboBox.setSelectedIndex(2); // default select female in combo box
		} 
		else
		{
			genderComboBox.setSelectedIndex(0);
		}

		String eye = (String)(witches[index].getEyeColour());
		if (eye.equals("brown"))
		{
			eyeColourComboBox.setSelectedIndex(1);
		} 
		else if (eye.equals("blue")) 
		{
			eyeColourComboBox.setSelectedIndex(2);
		} 
		else if (eye.equals("green"))
		{
			eyeColourComboBox.setSelectedIndex(3);
		} 
		else 
		{
			eyeColourComboBox.setSelectedIndex(0);
		}

		String spell = (String)(witches[index].getSpell());
		if (spell.equals("healing spell"))
		{
			spellsComboBox.setSelectedIndex(1);
		} 
		else if (spell.equals("shield spell"))
		{
			spellsComboBox.setSelectedIndex(2);
		} 
		else if (spell.equals("cloaking spell")) 
		{
			spellsComboBox.setSelectedIndex(3);
		} 
		else
		{
			spellsComboBox.setSelectedIndex(0);
		}
	}
	
	// method to present user's saved attributes in combo boxes for vampire-werewolf characters
	static void displayAttributesVW(int index) 
	{
		String gender = (String)(vampireWerewolves[index].getGender()); // get gender of vampire character, cast to string, store in variable
		if (gender.equals("Male")) // if character is male
		{
			genderComboBox.setSelectedIndex(1); // default select male in combo box
		} 
		else if (gender.equals("Female")) // if character is female
		{
			genderComboBox.setSelectedIndex(2); // default select female in combo box
		}
		else 
		{
			genderComboBox.setSelectedIndex(0);
		}

		String eye = (String)(vampireWerewolves[index].getEyeColour());
		if (eye.equals("brown")) 
		{
			eyeColourComboBox.setSelectedIndex(1);
		}
		else if (eye.equals("blue")) 
		{
			eyeColourComboBox.setSelectedIndex(2);
		} 
		else if (eye.equals("green")) 
		{
			eyeColourComboBox.setSelectedIndex(3);
		} 
		else 
		{
			eyeColourComboBox.setSelectedIndex(0);
		}

		String power = (String)(vampireWerewolves[index].getPower());
		if (power.equals("immortality"))
		{
			powersComboBox.setSelectedIndex(1);
		} 
		else if (power.equals("speed")) 
		{
			powersComboBox.setSelectedIndex(2);
		} 
		else if (power.equals("telepathy")) 
		{
			powersComboBox.setSelectedIndex(3);
		}
		else 
		{
			powersComboBox.setSelectedIndex(0);
		}
		
		String fur = (String)(vampireWerewolves[index].getFurColour());
		if (fur.equals("black")) 
		{
			furColourComboBox.setSelectedIndex(1);
		} 
		else if (fur.equals("brown")) 
		{
			furColourComboBox.setSelectedIndex(2);
		} 
		else if (fur.equals("grey")) 
		{
			furColourComboBox.setSelectedIndex(3);
		}
		else 
		{
			furColourComboBox.setSelectedIndex(0);
		}
	}

	// method to delete character from object array
	static void deleteCharacter(String name) 
	{
		int indexToDelete = findCharacter(name); // find the index inside of the vampire array of the character to delete
		deleteFromComboList(name); // call method to delete character from character list array
		if (charType.equals("Vampire")) // if user wants to delete a vampire character
		{			
			// make copy of array (skipping over the index deleted)
			Vampire[] vampiresTemp = new Vampire[vampires.length];
			int j = 0; // indexing the tempArray
			// loop through vampires array
			for (int i = 0;i < vampires.length; i++)
			{
				// if current vampire is not the one we want to delete
				if (i != indexToDelete)
				{
					vampiresTemp[j] = vampires[i]; // copy current vampire into temp array
					j++; // increment temp array counter
				}
			}
			
			//copy back the array
			vampires = vampiresTemp.clone();
			indexVamp--; // decrement vampire object counter by one
		}
		
		if (charType == "Witch") // if user wants to edit a witch character
		{
			// make copy of array (skipping over the index deleted)
			Witch[] witchesTemp = new Witch[witches.length];
			int j = 0; // indexing the tempArray
			// loop through witch array
			for (int i = 0;  i < witches.length; i++) 
			{
				// if current witch is not the one we want to delete
				if (i != indexToDelete) 
				{
					witchesTemp[j] = witches[i]; // copy current witch into temp array
					j++; // increment temp array counter
				}
			}
			
			//copy back the array
			witches = witchesTemp.clone();
			indexWitch--; // decrement witch count by one
		}

		if (charType == "Vampire-Werewolf hybrid") // if user wants to edit vampire-werewolf character
		{
			// make copy of array
			VampireWerewolf[] vampireWerewolvesTemp = new VampireWerewolf[vampireWerewolves.length];
			int j = 0; // indexing the tempArray
			// loop through original array
			for (int i=0;  i < vampireWerewolves.length; i++) 
			{
				// if current object is not the one we want to delete
				if (i != indexToDelete) 
				{
					vampireWerewolvesTemp[j] = vampireWerewolves[i]; // copy object into temp array
					j++; // increment temp array counter 
				}
			}
			//copy back the array
			vampireWerewolves = vampireWerewolvesTemp.clone();
			indexVampWere--; 
		} 
	} 
	
	// method to delete character from character combo list
	static void deleteFromComboList(String name)
	{
		List<String> list = new ArrayList<String>(Arrays.asList(charListComboList)); // create ArrayList of items in charListComboList
		list.remove(name); // function to remove item from list
		charListComboList = list.toArray(new String[0]); // returns updated list into charListComboList array
		clc--; // decrement list counter
	}
	
	// method to sort characters in vampire array
	public static void insertionSort(Vampire[] characters, Vampire newCharacter, int index)
	{
		String currentChar = newCharacter.getName(); // store the character that needs to be inserted into the sorted list
		int j = index - 1; // variable to compare current character with the one behind it
	
		// check if there is a character on the left to compare to AND if the characters need to be swapped		
		while ((j >= 0) && (characters[j].getName().compareToIgnoreCase(currentChar) > 0)) 
		{
			characters[j+1] = characters[j]; // copy character on the left one cell forward
			j--; // move pointer one cell backwards to compare to new character
		}
		characters[j+1] = newCharacter; // swap
	} 
	
	// overload method to sort characters in witch array	
	public static void insertionSort(Witch[] characters, Witch newCharacter, int index)
	{
		String currentChar = newCharacter.getName(); // store the character's name that needs to be inserted into the sorted list
		int j = index - 1; // variable to compare current character with the one behind it
	
		// check if there is a character on the left to compare to AND if the characters need to be swapped		
		while ((j >= 0) && (characters[j].getName().compareToIgnoreCase(currentChar) > 0)) 
		{
			characters[j+1] = characters[j]; // copy character on the left one cell forward
			j--; // move pointer one cell backwards to compare to new character
		}
		characters[j+1] = newCharacter; // swap
	} 
	
	// overload method to sort characters in vampire-werewolf array
	public static void insertionSort(VampireWerewolf[] characters, VampireWerewolf newCharacter, int index)
	{
		String currentChar = newCharacter.getName(); // store the character's name that needs to be inserted into the sorted list
		int j = index - 1; // variable to compare current character with the one behind it
	
		// check if there is a character on the left to compare to AND if the characters need to be swapped		
		while ((j >= 0) && (characters[j].getName().compareToIgnoreCase(currentChar) > 0)) 
		{
			characters[j+1] = characters[j]; // copy character on the left one cell forward
			j--; // move pointer one cell backwards to compare to new character
		}
		characters[j+1] = newCharacter; // swap
	} 
		
	// overload method to sort characters in combo box, clc is the current number of characters in the array 
	public static void insertionSort(String[] characters, String newCharacter, int clc)
	{
		String currentChar = newCharacter; // store the character that needs to be inserted into the sorted list
		int j = clc - 1; // variable to compare current character with the one behind it

		// check if there is a character on the left to compare to AND if the characters need to be swapped		
		while ((j >= 0) && (characters[j].compareToIgnoreCase(currentChar) > 0)) 
		{
			characters[j+1] = characters[j]; // copy character on the left one cell forward
			j--; // move pointer one cell backwards to compare to new character
		}
		characters[j+1] = currentChar; // swap	
	}
	
	public static int binarySearch(String[] characters, String target)
	{
		// initialize upper bound and lower bound in array, keeps track of the sub-array we are currently looking at 
		int left = 0;
		int right = characters.length - 1;
		
		// if entire array is not full, change upper bound to be the last element in array
		for (int i = 0; i < characters.length; i++) 
		{
			// if current cell is null
			if (characters[i] == null)
			{
				right = i -1; // set right to be the previous element
				break; // exit loop
			}	
		}
		
		while (left <= right)
		{
			// calculates index of the middle item in the sub-array
			int middle = (left + right) / 2;
			
			// if target belongs in the left sub-array
			if (target.compareToIgnoreCase(characters[middle]) < 0)
			{
				right = middle - 1; // adjust upper bound to ignore right half of sub-array
			}
			// if target belongs in the right sub-array
			else if (target.compareToIgnoreCase(characters[middle]) > 0)
			{
				left = middle + 1; // adjust lower bound to ignore left half of sub-array
			}
			// if target belongs exactly in the middle position
			else
			{
				return middle;
			}
		}
		return -1; // if we reach this line, then the target is not present (name does not exist in array)
	}

	// method to read from file 
	public static void readFromFile()
	{
		try
		{
			File file = new File("CharactersTextFile.txt"); // create file for reading
			Scanner myReader = new Scanner(file); // create Scanner object to connect to file for reading
			String[] characterProps; // array of character properties
			while(myReader.hasNextLine()) // check if there is another line in the input in the file
			{
				String data = myReader.nextLine(); // read next line
				characterProps = data.split(","); // split string every time there is a comma, store data in array
				// check character type
				switch(characterProps[1]) 
				{
					case "Vampire":
						// instantiate new vampire with their saved attributes
						vampire = new Vampire(characterProps[0], characterProps[2], characterProps[3], characterProps[4]);
						addCharacter(1, vampire); // call method to add character to object array
						break;
						
					case "Witch":
						// instantiate new witch with their saved attributes
						witch = new Witch(characterProps[0], characterProps[2], characterProps[3], characterProps[4]);
						addCharacter(2, witch); // call method to add character to object array
						break;
						
					case "Vampire-Werewolf hybrid":
						// instantiate new vampire-werewolf with their saved attributes
						vampireWerewolf = new VampireWerewolf(characterProps[0], characterProps[2], characterProps[3], characterProps[4], characterProps[5]);
						addCharacter(3, vampireWerewolf); // call method to add character to object array
						break;
						
					default:
						break;
				}
				
				System.out.println(data);
			}
			if (charCount > 0) // if user has created at least one character
			{
				displayOptions(6); // display all four buttons (add, edit, delete, view)
			} 
			myReader.close(); // close the file
		}
		catch (FileNotFoundException e)
		{
			System.out.println("error when reading");
		}
	}
	
	// method to write to file
	public static void writeToFile()
	{
		try
		{
			FileWriter writer = new FileWriter("CharactersTextFile.txt"); // create FileWriter object to connect to text file
			String name;
			int indexInCharArray;
			// loop through names in character list combo box
			for (int i = 0; i < clc; i++) 
			{
				name = charListComboList[i]; 
				indexInCharArray = findCharacter(name); // get index of current object to write to file
				
				// check character type
				switch (charType) 
				{
				// EACH CHARACTER GETS ONE LINE IN TEXT FILE (e.g. Kevin,Vampire,Male,brown,speed)
				case "Vampire":
					// get properties of character from vampires object array and write data to file
					// separate character attributes with commas
					writer.write(String.format("%s,%s,%s,%s,%s\n", name, charType, vampires[indexInCharArray].getGender(), vampires[indexInCharArray].getEyeColour(), vampires[indexInCharArray].getPower()));
					break;
				
				case  "Witch":
					// get properties of character from witches object array and write data to file
					writer.write(String.format("%s,%s,%s,%s,%s\n", name, charType, witches[indexInCharArray].getGender(), witches[indexInCharArray].getEyeColour(), witches[indexInCharArray].getSpell()));
					break;
					
				case "Vampire-Werewolf hybrid":
					// get properties of character from vampiresWerewolves object array and write data to file
					writer.write(String.format("%s,%s,%s,%s,%s,%s\n", name, charType, vampireWerewolves[indexInCharArray].getGender(), vampireWerewolves[indexInCharArray].getEyeColour(), vampireWerewolves[indexInCharArray].getPower(), vampireWerewolves[indexInCharArray].getFurColour()));
					break;
				
				default:
					break;
				}

			}
			
			writer.close(); // close writer object
			System.out.println("successfully wrote to file");
		}
		catch (IOException e)
		{
			System.out.println("error when writing");
		}
	}
} // GUI class
